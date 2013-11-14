package eagleye;

import eagleye.javassist.ByteArrayClassPath;
import eagleye.javassist.ClassPool;
import eagleye.javassist.CtClass;
import eagleye.javassist.LoaderClassPath;
import eagleye.utils.ClassUtils;

/**
 * To support class byte code render by Javassit.
 * 
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public abstract class JavassitClassBytecodeRender implements
        ClassBytecodeRender {
    
    private int interval;
    
    boolean active;
    
    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }


    public boolean active() {
        return active;
    }


    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public final byte[] render(String name, String classname, byte[] bytecode,
            ClassLoader cl) {
        try {
            classname = ClassUtils.getClassName(classname);
            ClassPool cp = new ClassPool();
            cp.appendClassPath(new ByteArrayClassPath(classname, bytecode));
            cp.appendSystemPath();
            cp.appendClassPath(new LoaderClassPath(cl));
            CtClass cc = cp.get(classname);
            cc.defrost();
            process(name, cp, cl, cc);
            return cc.toBytecode();
        } catch (Throwable e) {
            e.printStackTrace();
            return bytecode;
        }
    }

    /**
     * Modifies the class to be loaded.
     * 
     * @param cp
     *            the container of <code>CtClass</code> objects.
     * @param cl
     *            the class loader loading the given class.
     * @param ctClass
     *            the class representation.
     */
    public abstract void process(String name, ClassPool cp, ClassLoader cl, 
            CtClass ctClass) throws Exception;

}
