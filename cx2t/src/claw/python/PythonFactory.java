/*
 * This file is released under terms of BSD license
 * See LICENSE file for more information
 */
package claw.python;

/* These modules are provided by Jython */
import org.python.core.PyObject;

/**
 * Placeholder class where we will use Jython to create a
 * PythonInterpreter object.
 *
 * @author A. R. Porter, STFC
 */
public class PythonFactory implements PythonFactoryInterface {

    private PyObject transformClass;

    
    public void createTransformClass() {
    }
    
    public String createTransform(String name) {
	return "";
    }
}
