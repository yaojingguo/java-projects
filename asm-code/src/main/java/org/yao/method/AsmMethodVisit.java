package org.yao.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AsmMethodVisit extends MethodVisitor{
  public AsmMethodVisit(MethodVisitor mv) {
    super(Opcodes.ASM4, mv);
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name,
                              String desc) {
    super.visitMethodInsn(opcode, owner, name, desc);
  }

  @Override
  public void visitCode(){
    super.visitCode();
  }

  @Override
  public void visitInsn(int opcode){
    // Add instructions before return
    if(opcode == Opcodes.RETURN){
      mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
      mv.visitLdcInsn("this is a modify method!");
      mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
    }
    super.visitInsn(opcode);
  }
}