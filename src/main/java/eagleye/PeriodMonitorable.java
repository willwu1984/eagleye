/**
 * 
 */
package eagleye;

import eagleye.component.PeriodMonitorInfo;

/**
 * Monitor component for period monitoring
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public interface PeriodMonitorable extends Managable {
    
    String method = "doMonitorEagleye";
    String methodInterval = "getIntervalEagleye";

    //should differ to user method
    PeriodMonitorInfo doMonitorEagleye();
    
    int getIntervalEagleye();
    
}
