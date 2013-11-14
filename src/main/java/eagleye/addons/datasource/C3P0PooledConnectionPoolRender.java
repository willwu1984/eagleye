/**
 * 
 */
package eagleye.addons.datasource;

import eagleye.PeriodMonitorableJavassitRender;
import eagleye.javassist.ClassPool;
import eagleye.javassist.CtClass;
import eagleye.javassist.CtMethod;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public class C3P0PooledConnectionPoolRender extends
     PeriodMonitorableJavassitRender {

    @Override
    public String createDoMonitorMethodBody(String idFieldName) {
        String columns = "new String[]{\"minSize\",\"maxSize\",\"idleSize\",\"activeSize\"";
        columns += ",\"connectedSize\"}";
        
        //method body
        String doMonitorEagleyeBody = 
            "public PeriodMonitorInfo doMonitorEagleye() {" +
            "  __info.setId(" + idFieldName + ");" +
            "  __info.setMinSize(rp.getMinPoolSize());" +
            "  __info.setMaxSize(rp.getMaxPoolSize());" +
            "  __info.setIdlSize(getNumIdleConnections());" +
            "  __info.setBusySize(getNumBusyConnections());" +
            "  __info.setConnectedSize(getNumConnections());" +
            "  __info.setLastUpdate(new Date());" +
            "  __info.setLastUpdateResult(true);" +
            "  __info.setHeader(" + columns + ");" +
            "  __info.setType(MonitorType.DATASOURCE.toString());" +
            "  __info.updateRow();" +
            "  __info.update();" +
            "  __info.setBegin(new Date(getStartTime()));" +
            "  return __info;" +
            "}";
        
        return doMonitorEagleyeBody;
    }

    @Override
    public String createPeriodMonitorInfoFieldBody(String idFieldName) {
        return "private PooledDataSourceMonitorInfo __info = new PooledDataSourceMonitorInfo(" + idFieldName + ");";
    }

    @Override
    public void processFinal(String name, ClassPool cp, ClassLoader cl,
            CtClass ctClass) throws Exception {
        //get checkoutResource(J) method
        CtMethod method;
        method = ctClass.getMethod("checkoutPooledConnection", "()Ljavax/sql/PooledConnection;");
        //call push method 
        method.insertAfter("pushEagleye();");
        
        method = ctClass.getMethod("checkinPooledConnection", "(Ljavax/sql/PooledConnection;)V");
        //call push method 
        method.insertAfter("pushEagleye();");
    }

}
