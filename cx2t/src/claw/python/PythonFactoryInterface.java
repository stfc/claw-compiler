/*
 * This file is released under terms of BSD license
 * See LICENSE file for more information
 */
package claw.python;

/* The Java definition of the transform interface implemented in Python */
//import claw.python.PythonTransformInterface;

/**
 * Interface to class wrapping construction of Python interpreter
 *
 * @author A. R. Porter, STFC
 */
public interface PythonFactoryInterface {

    /* An interface cannot include a constructor */
    public void createTransformClass();
    //public PythonTransformInterface createTransform(String name);
    public String createTransform(String name);
}
