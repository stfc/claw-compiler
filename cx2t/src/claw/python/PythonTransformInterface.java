package claw.python;


import claw.tatsu.xcodeml.xnode.common.XcodeProgram;
import claw.python.PythonException;

/*
 * The interface that any python transformation script must implement
 *
 */
public interface PythonTransformInterface {
    public XcodeProgram trans(String script_name, XcodeProgram kernel_ast) throws PythonException;
}

