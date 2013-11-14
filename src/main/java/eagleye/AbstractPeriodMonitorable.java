/**
 * 
 */
package eagleye;

import eagleye.component.PeriodMonitorInfo;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public abstract class AbstractPeriodMonitorable implements PeriodMonitorable {

    private boolean active;
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }

    //should differ to user method
    public abstract PeriodMonitorInfo doMonitorEagleye();
    
    public abstract int getIntervalEagleye();

}
