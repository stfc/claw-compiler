/*
 * This file is released under terms of BSD license
 * See LICENSE file for more information
 */
package claw.python;

/**
 * Base class for exceptions raised within Jython code but caught in
 * Claw (Java)
 *
 * @author A. R. Porter, STFC
 */
public class PythonException extends Exception {
    
    public PythonException(String message) {
	super(message);
    }
}

