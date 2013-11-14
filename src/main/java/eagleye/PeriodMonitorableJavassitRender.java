/**
 * 
 */
package eagleye;

import eagleye.javassist.ClassPool;
import eagleye.javassist.CtClass;
import eagleye.javassist.CtConstructor;
import eagleye.javassist.CtField;
import eagleye.javassist.CtMethod;
import eagleye.javassist.CtNewMethod;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public abstract class PeriodMonitorableJavassitRender extends
        JavassitClassBytecodeRender {

    /* (non-Javadoc)
     * @see eagleye.JavassitClassBytecodeRender#process(java.lang.String, eagleye.javassist.ClassPool, java.lang.ClassLoader, eagleye.javassist.CtClass)
     */
    @Override
    public final void process(String name, ClassPool cp, ClassLoader cl,
            CtClass ctClass) throws Exception {
        cp.importPackage("eagleye");
        cp.importPackage("eagleye.component");
        cp.importPackage("eagleye.component.datasource");
        cp.importPackage("java.util");
        
        //this is period monitor
        String clsName = PeriodMonitorable.class.getName();
        ctClass.addInterface(cp.get(clsName));
        
        //create active field
        String activeFieldName = "__active";
        CtField field = CtField.make(
                "private boolean " + activeFieldName + ";", ctClass);
        ctClass.addField(field);  
        
        //create set and get method
        String setActiveSrc = "public void setActive(boolean active) {$0." + 
            activeFieldName + "=$1;}";
        CtMethod method = CtNewMethod.make(setActiveSrc, ctClass);
        ctClass.addMethod(method);
        String getActiveSrc = "public boolean isActive() {return $0." + 
        activeFieldName + ";}";
        method = CtNewMethod.make(getActiveSrc, ctClass);
        ctClass.addMethod(method);
        
        //create id field
        String idFieldName = "__id";
        field = CtField.make(
                "private String __id = \"PeriodMonitorInfo-\"+UUID.randomUUID().toString().replace(\"-\", \"\");", ctClass);
        ctClass.addField(field);
        
        //create monitor info instance
        field = CtField.make(
                createPeriodMonitorInfoFieldBody(idFieldName), ctClass);
        ctClass.addField(field);
        
        //create method for PeriodMonitorable
        //method body
        String doMonitorEagleyeBody = createDoMonitorMethodBody(idFieldName);
        method = CtNewMethod.make(doMonitorEagleyeBody, ctClass);
        ctClass.addMethod(method);
        
        //add getInterval method
        String getIntervalBody = "public int getIntervalEagleye() { return " + 
            getInterval() + ";}";
        method = CtNewMethod.make(getIntervalBody, ctClass);
        ctClass.addMethod(method);
        
        //register back to Monitor Watcher
        CtConstructor[] constructors = ctClass.getConstructors();
        if (constructors != null) {
            for (CtConstructor ctConstructor : constructors) {
                ctConstructor.insertAfter(
                        "MonitorWatcherRegister.register($0);", true);
            }
        }
        
        //add push monitor method
        String pushMethodBody = "public void pushEagleye() {" +
        		"PeriodMonitorInfo info = doMonitorEagleye();" +
        		"MonitorWatcher.watch(info);}";
        method = CtNewMethod.make(pushMethodBody, ctClass);
        ctClass.addMethod(method);

        processFinal(name, cp, cl, ctClass);
    }
    
    public abstract String createDoMonitorMethodBody(String idFieldName);
    public abstract String createPeriodMonitorInfoFieldBody(String idFieldName);
    public abstract void processFinal(String name, ClassPool cp, ClassLoader cl,
            CtClass ctClass) throws Exception;
}
