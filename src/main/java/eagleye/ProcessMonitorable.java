/**
 * 
 */
package eagleye;

/**
 * Monitor component for process type monitoring
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public interface ProcessMonitorable extends Managable {

    String method = "registerWatcherEagleye";
    
    /**
     * process monitor will generate monitor message by each process finish.<br>
     * All registered {@link MonitorWatcher} will call back auto.
     * 
     * @param wather {@link MonitorWatcher} instance.
     */
    void registerWatcherEagleye(MonitorWatcher wather);
}
