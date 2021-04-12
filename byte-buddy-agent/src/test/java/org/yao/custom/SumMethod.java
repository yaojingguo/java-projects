package org.yao.custom;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.MethodVisitor;

public enum SumMethod implements ByteCodeAppender {
  INSTANCE;

  @Override
  public Size apply(
      MethodVisitor methodVisitor,
      Implementation.Context implementationContext,
      MethodDescription instrumentedMethod) {
    if (!instrumentedMethod.getReturnType().asErasure().represents(int.class)) {
      throw new IllegalArgumentException(instrumentedMethod + " must return int");
    }
    StackManipulation.Size operandStackSize =
        new StackManipulation.Compound(
                IntegerConstant.forValue(10),
                IntegerConstant.forValue(50),
                IntegerSum.INSTANCE,
                MethodReturn.INTEGER)
            .apply(methodVisitor, implementationContext);
    return new Size(operandStackSize.getMaximalSize(), instrumentedMethod.getStackSize());
  }
}
