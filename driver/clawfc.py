
OMNI_JAR_FILES = ["/home/kbc59144/MyInstalls/share/xcalablemp/om-exc-tools.jar",
                  "/home/kbc59144/MyInstalls/share/xcalablemp/om-common.jar",
                  "/home/kbc59144/MyInstalls/share/xcalablemp/om-f-back.jar",
                  "/home/kbc59144/MyInstalls/share/xcalablemp/om-c-back.jar"]
CLAW_JAR_FILES = ["/home/kbc59144/MyInstalls/share/claw/om-cx2x-claw.jar",
                  "/home/kbc59144/MyInstalls/share/claw/commons-cli.jar",
                  "/home/kbc59144/MyInstalls/share/claw/om-cx2x-xcodeml.jar",
                  "/home/kbc59144/MyInstalls/share/claw/antlr4.jar",
                  "/home/kbc59144/MyInstalls/share/claw/antlr4-runtime.jar"]
JYTHON_JAR = "/home/kbc59144/MyInstalls/jython2.7.0/jython.jar"

JAR_FILES = OMNI_JAR_FILES + CLAW_JAR_FILES + [JYTHON_JAR]
CLASS_PATH = ":".join(JAR_FILES)

CLAW_CONFIG_FILE = "/home/kbc59144/MyInstalls/etc"
NUM_OUTPUT_COLUMNS = 80

def claw_driver(argv):
    ''' Top level python driver for Claw compiler '''
    from subprocess import call
    from os import path
    fortran_file = argv[0]
    script_file = argv[1]
    print "Processing file {0} using recipe {1}".format(fortran_file,
                                                        script_file)
    # Use the OMNI frontend to generate the XcodeML representation of
    # the Fortran file
    (dir_path, xml_file) = path.split(fortran_file)
    if xml_file.endswith(".F90"):
        xml_file = xml_file.replace(".F90", ".xml", 1)
    elif xml_file.endswith(".f90"):
        xml_file = xml_file.replace(".f90", ".xml", 1)
    else:
        raise Exception("Fortran file must have .f90 or .F90 suffix but "
                        "got {0}".format(xml_file))
    xml_file = path.join(dir_path, xml_file)
    call(["F_Front", fortran_file, "-o", str(xml_file)])
    print "Produced XCodeML file: {0}".format(xml_file)

    # Then transform this XcodeML representation using CLAW and de-compile
    # it back to Fortran
    call(["/usr/bin/java", "-Xmx200m", "-Xms200m", "-cp", CLASS_PATH,
          "claw.ClawX2T", "--config-path={0}".format(CLAW_CONFIG_FILE),
          "--schema=/home/kbc59144/MyInstalls/etc/claw_config.xsd",
          "-w", str(NUM_OUTPUT_COLUMNS), "-l",
          "-M/home/kbc59144/Projects/code_fragments",
          "-M/home/kbc59144/MyInstalls/fincludes",
          "-o", "claw_5f_example_f90_out.xml",
          "-f", fortran_file,
          "-script", script_file,
          xml_file])


if __name__ == "__main__":
    import sys
    claw_driver(sys.argv[1:])
