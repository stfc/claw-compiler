''' jython module for testing interoperation with Java '''

# Import the Java interface that this module implements
from claw.external.pythontransform import PythonInterface

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
        '''
        print "Here we would import {0} and apply it to {1}".\
            format(script_name, str(kernel_ast))
        # Mess with the state of the supplied (Java) object
        kernel_ast.root_node = 7
        # Call a method on the supplied Java object
        kernel_ast.PrintNode()

        return kernel_ast
