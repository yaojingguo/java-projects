package org.yao.method;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AsmExample extends ClassLoader implements Opcodes {

  public static void main(String args[])
      throws IOException, IllegalArgumentException, SecurityException, IllegalAccessException,
          InvocationTargetException, InstantiationException {
    ClassReader cr = new ClassReader(Foo.class.getName());
    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
    ClassVisitor cv = new MethodChangeClassAdapter(cw);
    cr.accept(cv, Opcodes.ASM4);

    // Add a new method
    MethodVisitor mw =
        cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "add", "([Ljava/lang/String;)V", null, null);
    mw.visitCode();
    mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    mw.visitLdcInsn("this is add method print!");
    mw.visitMethodInsn(
        INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    mw.visitInsn(RETURN);
    // this code uses a maximum of two stack elements and two local
    // variables
    mw.visitMaxs(0, 0);
    mw.visitEnd();

    byte[] code = cw.toByteArray();
    AsmExample loader = new AsmExample();
    Class<?> fooClass = loader.defineClass(Foo.class.getName(), code, 0, code.length);

    System.out.println("method:");
    for (Method method : fooClass.getMethods()) {
      System.out.println("\t" + method);
    }
    System.out.println();

    // Invoke the first method whose name is execute1
    System.out.println("method 0");
    System.out.println("method name: " + fooClass.getMethods()[0].getName());
    System.out.println("parameter count: " + fooClass.getMethods()[0].getParameterCount());
    fooClass.getMethods()[0].invoke(fooClass.newInstance());
    System.out.println();

    // Invoke the third method whose behaviour has been changed
    System.out.println("method 1");
    System.out.println("method name: " + fooClass.getMethods()[1].getName());
    System.out.println("parameter count: " + fooClass.getMethods()[1].getParameterCount());
    fooClass.getMethods()[1].invoke(fooClass.newInstance());
    System.out.println();

    System.out.println("method 2");
    // Invoke the third method whose behaviour has been changed
    System.out.println("method name: " + fooClass.getMethods()[2].getName());
    System.out.println("parameter count: " + fooClass.getMethods()[2].getParameterCount());
    fooClass.getMethods()[2].invoke(null, "one");

    // gets the bytecode of the Example class, and loads it dynamically
//    FileOutputStream fos = new FileOutputStream("Foo.class");
//    fos.write(code);
//    fos.close();
  }
}
