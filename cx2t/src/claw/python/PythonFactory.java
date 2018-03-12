/*
 * This file is released under terms of BSD license
 * See LICENSE file for more information
 */
package claw.python;

/* The Java definition of the interface implemented in Python */
import claw.python.PythonTransformInterface;

/* These modules are provided by Jython */
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

/**
 * Class wrapping construction of Python interpreter
 *
 * @author A. R. Porter, STFC
 */
public class PythonFactory implements PythonFactoryInterface {

    private PyObject transformClass;

    /**
     * Create a new PythonInterpreter object, then use it to
     * execute some python code. In this case, we want to
     * import the python module that we will coerce.
     *
     * Once the module is imported than we obtain a reference to
     * it and assign the reference to a Java variable
     */

    public void createTransformClass() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from ClawTransform import ClawTransform");
        transformClass = interpreter.get("ClawTransform");
    }

    /**
     * The create method is responsible for performing the actual
     * coercion of the referenced python module into Java bytecode
     */

    public PythonTransformInterface createTransform (String name) {

        PyObject transformObject = transformClass.__call__(new PyString(name));
        return (PythonTransformInterface)transformObject.__tojava__(PythonTransformInterface.class);
    }

}
