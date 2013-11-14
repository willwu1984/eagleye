/**
 * 
 */
package eagleye;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public abstract class AbstractProcessMonitorable implements ProcessMonitorable {

    private boolean active;
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public abstract void registerWatcherEagleye(MonitorWatcher wather);

}
