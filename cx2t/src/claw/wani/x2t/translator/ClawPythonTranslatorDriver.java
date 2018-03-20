/*
 * This file is released under terms of BSD license
 * See LICENSE file for more information
 */
package claw.wani.x2t.translator;

import claw.python.PythonFactoryInterface;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;

/**
 * ClawPythonTranslatorDriver is the class that wraps the use of a Python
 * script in order to perform the translation.
 *
 * @author A. R. Porter
 */
public class ClawPythonTranslatorDriver extends ClawTranslatorDriver {

  private String _transformScript = null;
  private PythonFactoryInterface _factory = null;

  /**
   * ClawPythonTranslatorDriver constructor.
   *
   * @param transScript       The python recipe input file path.
   * @param xcodemlInputFile  The XcodeML input file path.
   * @param xcodemlOutputFile The XcodeML output file path.
   */
  public ClawPythonTranslatorDriver(String transScript, String xcodemlInputFile,
                                    String xcodemlOutputFile)
      throws Exception
  {
    _transformScript = transScript;
    _xcodemlInputFile = xcodemlInputFile;
    _xcodemlOutputFile = xcodemlOutputFile;

    System.out.println("Creating factory object");

    // Dynamically load our PythonFactory from file - avoids compile-time
    // dependence on Jython. If Claw was built with Jython support then
    // the PythonFactory class is built into the same Jar file that
    // contains the definition of the factory interface.
    File pythonTransJar = new File(PythonFactoryInterface.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    try {
      ClassLoader loader = URLClassLoader.newInstance(new URL[]
	  { pythonTransJar.toURI().toURL() });
      PythonFactoryInterface _factory =
	  (PythonFactoryInterface) loader.loadClass("claw.python.PythonFactory").newInstance();
    } catch(MalformedURLException merr) {
      throw new Exception("Failed to create loader for "+
			  pythonTransJar.getName());
    } catch(ClassNotFoundException cerr) {
      throw new Exception("Jar "+pythonTransJar.getName()+
			  " does not contain the claw.python.PythonFactory "+
			  "class - did you compile Claw with Jython support?");
    }
  }

  /**
   * Analysis the XcodeML/F directives and categorized them in corresponding
   * transformation with the help of the translator.
   */
  public void analyze()
  {
  }

  /**
   * Apply the transformation script
   */
  public void transform()
  {
  }

   /**
   * Override this method from base class so that it does nothing
   *
   */
  public void flush()
  {
  }

}
