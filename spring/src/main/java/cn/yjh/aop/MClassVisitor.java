package cn.yjh.aop;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-05 17:01
 */
public class MClassVisitor extends ClassVisitor {

    public MClassVisitor(int api, ClassVisitor classVisitor) {
        super(Opcodes.ASM8,classVisitor);

    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        //cv.visitMethod();
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
