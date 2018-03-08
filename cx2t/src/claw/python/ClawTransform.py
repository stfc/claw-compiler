''' jython module for testing interoperation with Java '''

# Import the Java interface that this module implements as well
# as the exception class that it raises
from claw.python import PythonInterface, PythonException


def run_script(script_name, xcodeml_ast):
    '''
    Imports the supplied python module and runs the 'claw_trans'
    function defined within it.
    This code is lifted wholesale from PSyclone and I would
    have used it from PSyclone but that requires some
    refactoring.

    :param str script_name: fully-qualified name of Python script
                            to import
    :param xcodeml_ast:
    :raises: lots of things
    '''
    import os
    import sys
    sys_path_appended = False
    try:
        # a script has been provided
        filepath, filename = os.path.split(script_name)
        if filepath:
            # a path to a file has been provided
            # we need to check the file exists
            if not os.path.isfile(script_name):
                raise PythonException("script file '{0}' not found".
                                      format(script_name))
            # it exists so we need to add the path to the python
            # search path
            sys_path_appended = True
            sys.path.append(filepath)
        filename, fileext = os.path.splitext(filename)
        if fileext != '.py':
            raise PythonException(
                "generator: expected the script file '{0}' to have "
                "the '.py' extension".format(filename))
        try:
            transmod = __import__(filename)
        except ImportError:
            raise PythonException(
                "generator: attempted to import '{0}' but script file "
                "'{1}' has not been found".
                format(filename, script_name))
        except SyntaxError:
            raise PythonException(
                "generator: attempted to import '{0}' but script file "
                "'{1}' is not valid python".
                format(filename, script_name))
        if callable(getattr(transmod, 'claw_trans', None)):
            try:
                xcodeml_ast = transmod.claw_trans(xcodeml_ast)
            except Exception:
                import traceback
                exc_type, exc_value, exc_traceback = sys.exc_info()
                lines = traceback.format_exception(exc_type, exc_value,
                                                   exc_traceback)
                e_str = '{\n' +\
                    ''.join('    ' + line for line in lines[2:]) + '}'
                raise PythonException(
                    "Generator: script file '{0}'\nraised the "
                    "following exception during execution "
                    "...\n{1}\nPlease check your script".format(
                        script_name, e_str))
        else:
            raise PythonException(
                "generator: attempted to import '{0}' but script file "
                "'{1}' does not contain a 'claw_trans()' function".
                format(filename, script_name))
    finally:
        if sys_path_appended:
            os.sys.path.pop()

    return xcodeml_ast


class ClawTransform(PythonInterface):

    ''' Wrapper for performing CLAW transformations '''

    def __init__(self, name):
        '''
        :param str name: just an example of passing a variable into the
                         constructor
        '''
        self._name = name

    def trans(self, script_name, kernel_ast):
        '''
        Apply the specified transformation script to the supplied
        kernel AST (produced by CLAW)

        :param str script_name: Python script to apply to AST
        :param kernel_ast: the AST of the code to transform
        :type kernel_ast: claw.tatsu.xcodeml.xnode.common.XcodeProgram
        :return: Transformed AST
        :rtype: claw.tatsu.xcodeml.xnode.common.XcodeProgram
        '''
        transformed_ast = run_script(script_name, kernel_ast)

        return transformed_ast

