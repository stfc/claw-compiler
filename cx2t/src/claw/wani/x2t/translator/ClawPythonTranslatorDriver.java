/*
 * This file is released under terms of BSD license
 * See LICENSE file for more information
 */
package claw.wani.x2t.translator;

import claw.tatsu.common.Context;
import claw.tatsu.common.Message;
import claw.tatsu.primitive.Pragma;
import claw.tatsu.xcodeml.xnode.common.Xcode;
import claw.tatsu.xcodeml.xnode.common.XcodeProgram;
import claw.tatsu.xcodeml.exception.IllegalTransformationException;
import claw.wani.ClawConstant;
import claw.wani.language.ClawPragma;
import claw.wani.transformation.ClawTransformation;
import claw.wani.x2t.configuration.Configuration;
import claw.wani.x2t.translator.ClawTranslatorDriver;

import claw.python.PythonInterface;
import claw.python.PythonFactory;
import claw.python.PythonException;

/**
 * ClawPythonTranslatorDriver is the class that wraps the use of a Python
 * script in order to perform the translation.
 *
 * @author A. R. Porter
 */
public class ClawPythonTranslatorDriver extends ClawTranslatorDriver {

  private String _transformScript = null;
  // Factory to create our transformation object
  private PythonFactory _factory = null;
  private PythonInterface _transform = null;

  /**
   * ClawPythonTranslatorDriver constructor.
   *
   * @param transScript The python recipe input file path.
   * @param xcodemlInputFile  The XcodeML input file path.
   * @param xcodemlOutputFile The XcodeML output file path.
   */
  public ClawPythonTranslatorDriver(String transScript,
		                    String xcodemlInputFile,
			            String xcodemlOutputFile)
      throws Exception
  {
    _transformScript = transScript;
    _xcodemlInputFile = xcodemlInputFile;
    _xcodemlOutputFile = xcodemlOutputFile;
    
    System.out.println("Creating factory object");
    _factory = new PythonFactory();
    // The string passed to the factory here is just an example of
    // passing an argument in to the constructor - it serves no
    // purpose!
    System.out.println("Creating transform object...");
    _transform = _factory.create("my_transform");
    System.out.println("...constructor finished!");
  }

  /**
   * Analysis the XcodeML/F directives and categorized them in corresponding
   * transformation with the help of the translator.
   */
  public void analyze()
  {
    _translationUnit = XcodeProgram.createFromFile(_xcodemlInputFile);
    if(_translationUnit == null) {
      abort();
    }
    // Analysis done, the transformation can be performed.
    _canTransform = true;
  }

  /**
   * Apply the transformation script
   */
  public void transform()
  {
    try {
      if(!_canTransform) {
        _translationUnit.write(_xcodemlOutputFile, ClawConstant.INDENT_OUTPUT);
        return;
      }
      System.out.println("Calling trans on transform object with script " + _transformScript);
      XcodeProgram _transformedUnit = _transform.trans(_transformScript,
						       _translationUnit);

      // Write transformed IR to file
      _transformedUnit.write(_xcodemlOutputFile, ClawConstant.INDENT_OUTPUT);

    } catch(PythonException ex) {
	System.err.println("Error while attempting to run python script " +
			   _transformScript + ": " + ex);
    } catch(IllegalTransformationException tex){
	System.err.println("Illegal transformation exception: " + tex);
    }
  }

  public void flush()
  {
  }

}
