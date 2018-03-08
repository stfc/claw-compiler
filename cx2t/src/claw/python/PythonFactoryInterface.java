package claw.python;

/* The Java definition of the transform interface implemented in Python */
import claw.python.PythonTransformInterface;

public interface PythonFactoryInterface {

    /* An interface cannot include a constructor */
    public void createTransformClass();
    public PythonTransformInterface createTransform(String name);
}
