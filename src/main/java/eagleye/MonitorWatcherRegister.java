/**
 * 
 */
package eagleye;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public class MonitorWatcherRegister {

    private static final Set<PeriodMonitorable> periodMonitors = 
        new CopyOnWriteArraySet<PeriodMonitorable>();
    
    public static void register(PeriodMonitorable monitor) {
        if (monitor == null) {
            return;
        }
        periodMonitors.add(monitor);
    }
    
    public static Set<PeriodMonitorable> periodMonitors() {
        return new HashSet<PeriodMonitorable>(periodMonitors);
    }
    
    public static void register(Object o) {
        if (o instanceof PeriodMonitorable) {
            register((PeriodMonitorable) o);
        }
    }
}
