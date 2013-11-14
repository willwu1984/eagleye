/**
 * 
 */
package eagleye;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import eagleye.component.PeriodMonitorInfo;
import eagleye.component.ProcessMonitorInfo;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public class MonitorWatcher {

    private static final Map<String, PeriodMonitorInfo> periodInfos =
        new ConcurrentHashMap<String, PeriodMonitorInfo>();
    private static final Map<String, ProcessMonitorInfo> processInfos =
        new ConcurrentHashMap<String, ProcessMonitorInfo>();
    
    public static void watch(PeriodMonitorInfo info) {
        if (info != null && info.getId() != null) {
            periodInfos.put(info.getId(), info);
        }
    }
    
    public static void watch(ProcessMonitorInfo info) {
        if (info != null && info.getId() != null) {
            processInfos.put(info.getId(), info);
        } 
    }
    
    public static Map<String, PeriodMonitorInfo> periodInfos() {
        return new HashMap<String, PeriodMonitorInfo>(periodInfos);
    }
    
    public static Map<String, ProcessMonitorInfo> processInfos() {
        return new HashMap<String, ProcessMonitorInfo>(processInfos);
    }
}
