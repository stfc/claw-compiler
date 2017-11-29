/*
 * This file is released under terms of BSD license
 * See LICENSE file for more information
 */
package claw.wani.x2t.translator;

import claw.shenron.transformation.TransformationGroup;
import claw.tatsu.common.Context;
import claw.tatsu.common.Message;
import claw.tatsu.primitive.Pragma;
import claw.tatsu.xcodeml.exception.IllegalDirectiveException;
import claw.tatsu.xcodeml.exception.IllegalTransformationException;
import claw.tatsu.xcodeml.xnode.common.Xcode;
import claw.tatsu.xcodeml.xnode.common.XcodeProgram;
import claw.tatsu.xcodeml.xnode.common.Xnode;
import claw.wani.ClawConstant;
import claw.wani.language.ClawPragma;
import claw.wani.transformation.ClawTransformation;
import claw.wani.x2t.configuration.Configuration;
import claw.wani.x2t.configuration.GroupConfiguration;
import xcodeml.util.XmOption;
import claw.external.pythontransform.PythonInterface;
import claw.external.pythontransform.PythonFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * ClawExternalTranslatorDriver is the class that wraps the use of a Python
 * script in order to perform the translation.
 *
 * @author clementval
 */
public class ClawExternalTranslatorDriver {

  private String _transformScript = null;
  private String _xcodemlInputFile = null;
  private String _xcodemlOutputFile = null;
  private boolean _canTransform = false;
  private XcodeProgram _translationUnit = null;
  // Factory to create our transformation object
  private PythonFactory _factory = null;
  private PythonInterface _transform = null;

  /**
   * ClawExternalTranslatorDriver constructor.
   *
   * @param transScript The python recipe input file path.
   * @param xcodemlInputFile  The XcodeML input file path.
   * @param xcodemlOutputFile The XcodeML output file path.
   */
  public ClawExternalTranslatorDriver(String transScript,
				      String xcodemlInputFile,
				      String xcodemlOutputFile)
      throws Exception
  {
    _transformScript = transScript;
    _xcodemlInputFile = xcodemlInputFile;
    _xcodemlOutputFile = xcodemlOutputFile;

    _factory = new PythonFactory();
    // The string passed to the factory here is just an example of
    // passing an argument in to the constructor - it serves no
    // purpose!
    _transform = _factory.create("my_transform");

  }

  /**
   * Analysis the XcodeML/F directives and categorized them in corresponding
   * transformation with the help of the translator.
   */
  public void analyze() {
    _translationUnit = XcodeProgram.createFromFile(_xcodemlInputFile);
    if(_translationUnit == null) {
      abort();
    }
    // Analysis done, the transformation can be performed.
    _canTransform = true;
  }

  /**
   * Instantiate correct transformation class from group configuration.
   *
   * @param gc     Group configuration for the
   * @param pragma Pragma associated with the transformation.
   */
  private void generateTransformation(GroupConfiguration gc,
                                      ClawPragma pragma)
  {

  }

  /**
   * Apply the transformation script
   */
  public void transform() {
    try {
      if(!_canTransform) {
        _translationUnit.write(_xcodemlOutputFile, ClawConstant.INDENT_OUTPUT);
        return;
      }
      XcodeProgram _transformedUnit = _transform.trans(_transformScript,
						       _translationUnit);

      // Write transformed IR to file
      _transformedUnit.write(_xcodemlOutputFile, ClawConstant.INDENT_OUTPUT);

    } catch(Exception ex) {
      System.err.println("Transformation exception: " + ex.getMessage());
    }
  }

  /**
   * Print all the errors stored in the XcodeML object and abort the program.
   */
  private void abort() {
    Message.errors(_translationUnit);
    System.exit(1);
  }

  /**
   * Flush all information stored in the translator.
   */
  public void flush()
      throws IllegalTransformationException
  {
      //_translator.getModCache().write(ClawConstant.INDENT_OUTPUT);
  }

  /**
   * Get the current translator associated with this translation.
   *
   * @return Get the current translator.
   */
  public ClawTranslator getTranslator() {
      return null;//_translator;
  }

  /**
   * Get the XcodeProgram object representing the Fortran code translated.
   *
   * @return Current XcodeProgram object.
   */
  public XcodeProgram getTranslationUnit() {
    return _translationUnit;
  }
}
