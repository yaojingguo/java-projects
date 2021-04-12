package org.yao;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public enum ToStringAssigner implements Assigner {
  INSTANCE;

  @Override
  public StackManipulation assign(
      TypeDescription.Generic source, TypeDescription.Generic target, Assigner.Typing typing) {
    if (!source.isPrimitive() && target.represents(String.class)) {
      MethodDescription toStringMethod =
          new TypeDescription.ForLoadedType(Object.class)
              .getDeclaredMethods()
              .filter(named("toString"))
              .getOnly();
      return MethodInvocation.invoke(toStringMethod).virtual(source.asErasure());
    } else {
      return StackManipulation.Illegal.INSTANCE;
    }
  }
}
