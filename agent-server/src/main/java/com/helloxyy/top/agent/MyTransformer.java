package com.helloxyy.top.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;

/**
 * Created by lihao on 17/12/13.
 *
 * @author lihao
 * @date 2019-02-24
 */
public class MyTransformer implements ClassFileTransformer {

    private static Logger logger = LoggerFactory.getLogger(MyTransformer.class);

    //需要监控的方法
    private static final String WITHDRAW_MONEY_METHOD = "run";

    /**
     * The internal form class name of the class to transform
     */
    private String      targetClassName;
    /**
     * The class loader of the class we want to transform
     */
    private ClassLoader targetClassLoader;

    public MyTransformer(String targetClassName, ClassLoader targetClassLoader) {
        this.targetClassName = targetClassName;
        this.targetClassLoader = targetClassLoader;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws
                                                                                       IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;
        //replace . with /
        String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/");
        if (!className.equals(finalTargetClassName)) {
            return byteCode;
        }

        if (className.equals(finalTargetClassName) && loader.equals(targetClassLoader)) {
            logger.info("[Agent] Transforming class" + className);
            //todo 两种方式都可尝试。
            byteCode = buildByAsm(byteCode);
//            byteCode = buildByJavassist(byteCode);
        }
        return byteCode;
    }

    private byte[] buildByJavassist(byte[] byteCode) {
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass cc = cp.get(targetClassName);
            CtMethod m = cc.getDeclaredMethod(WITHDRAW_MONEY_METHOD);

            // 开始时间
            m.addLocalVariable("startTime", CtClass.longType);
            m.insertBefore("startTime = System.currentTimeMillis();");

            StringBuilder endBlock = new StringBuilder();

            // 结束时间
            m.addLocalVariable("endTime", CtClass.longType);
            endBlock.append("endTime = System.currentTimeMillis();");

            // 时间差
            m.addLocalVariable("opTime", CtClass.longType);
            endBlock.append("opTime = endTime-startTime;");

            // 打印方法耗时
            endBlock.append("logger.info(\"completed in:\" + opTime + \" millis!\");");

            m.insertAfter(endBlock.toString());

            byteCode = cc.toBytecode();
            cc.detach();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return byteCode;
    }

    private byte[] buildByAsm(byte[] byteCode) {
        try {
            ClassReader cr = new ClassReader(byteCode);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new CostClassVisitor(cw);
            cr.accept(cv, EXPAND_FRAMES);
            byteCode = cw.toByteArray();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return byteCode;
    }
}
