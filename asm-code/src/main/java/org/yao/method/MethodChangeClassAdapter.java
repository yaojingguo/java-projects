package org.yao.method;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodChangeClassAdapter extends ClassVisitor implements Opcodes {
  public MethodChangeClassAdapter(ClassVisitor cv) {
    super(Opcodes.ASM4, cv);
  }

  @Override
  public void visit(
      int version,
      int access,
      String name,
      String signature,
      String superName,
      String[] interfaces) {
    if (cv != null) {
      cv.visit(version, access, name, signature, superName, interfaces);
    }
  }

  @Override
  public MethodVisitor visitMethod(
      int access, String name, String desc, String signature, String[] exceptions) {
    // Change method execute to execute1
    if (cv != null && "execute".equals(name)) {
      return cv.visitMethod(access, name + "1", desc, signature, exceptions);
    }

    // Change the behaviour of changeMethodContent method
    if ("changeMethodContent".equals(name)) {
      // Get the original method
      MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
      MethodVisitor newMethod = null;
      newMethod = new AsmMethodVisit(mv);
      return newMethod;
    }
    if (cv != null) {
      return cv.visitMethod(access, name, desc, signature, exceptions);
    }
    return null;
  }
}
