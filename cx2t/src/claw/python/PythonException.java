package claw.python;

/*
 * Base class for exceptions raised within Jython code but caught in
 * Claw (Java)
 */
public class PythonException extends Exception {
    
    public PythonException(String message) {
	super(message);
    }
}

