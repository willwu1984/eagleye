/**
 * 
 */
package eagleye.addons.test;

import eagleye.JavassitClassBytecodeRender;
import eagleye.javassist.ClassPool;
import eagleye.javassist.CtClass;
import eagleye.javassist.CtMethod;

/**
 * @author xiemalin
 *
 */
public class TestRender extends JavassitClassBytecodeRender {

    @Override
    public void process(String name, ClassPool cp, ClassLoader cl,
            CtClass ctClass) throws Exception {
        
        
        CtMethod method = ctClass.getMethod("doGet", 
                "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V");
        
        
        if (method != null) {
            method.insertAfter("System.out.println(\"Yes= " + getInterval() + "\");");
        }
        
    }

    public void test(long a) {
        
    }

}
