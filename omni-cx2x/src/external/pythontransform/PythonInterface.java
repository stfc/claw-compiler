package claw.external.pythontransform;


import claw.tatsu.xcodeml.xnode.common.XcodeProgram;

/*
 * The interface that any python transformation script must implement
 *
 */
public interface PythonInterface {
    public XcodeProgram trans(String script_name, XcodeProgram kernel_ast);
}

