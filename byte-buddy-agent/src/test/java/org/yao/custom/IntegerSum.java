package org.yao.custom;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public enum IntegerSum implements StackManipulation {
  INSTANCE;

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
    methodVisitor.visitInsn(Opcodes.IADD);
    return new Size(-1, 0);
  }
}
