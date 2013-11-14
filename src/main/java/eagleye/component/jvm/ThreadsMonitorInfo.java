/**
 * 
 */
package eagleye.component.jvm;

import java.lang.Thread.State;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import eagleye.component.PeriodMonitorInfo;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public class ThreadsMonitorInfo extends PeriodMonitorInfo {

    /**
     * serial Version UID
     */
    private static final long serialVersionUID = -2033426859515452937L;

    
    private Set<ThreadMonitorInfo> infoSet = new HashSet<ThreadMonitorInfo>();
    
    public void add(ThreadMonitorInfo info) {
        infoSet.add(info);
    }
    
    public void set(Collection<ThreadMonitorInfo> infos) {
        infoSet = new HashSet<ThreadMonitorInfo>(infos);
    }
    
    public void clear() {
        infoSet.clear();
    }
    
    @Override
    public String toString() {
    	List<Object> data=this.getData();
    	HashMap<String , Long> threadMap = new HashMap<String , Long>();
    	HashMap<Thread.State, Integer> map = new HashMap<Thread.State, Integer>();
        if (getColumns() != null && data !=null) {
        	threadMap.put("java_count", (long) infoSet.size());
			for (State state : State.values()) {
				map.put(state, 0);
			}
			for (ThreadMonitorInfo threadinfo : infoSet) {
				State state =  threadinfo.getState();
				Integer value = map.get(state);
				map.put(state, value + 1);
			}
			Iterator<State> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				State state = iterator.next();
				long value = (long)map.get(state);
				String stateString = state + "_num";
				threadMap.put(stateString, value);
			}
        }
        Gson gson = new Gson();
        return gson.toJson(threadMap);
    }
    
    public void updateRow() {
        ThreadMonitorInfo[] array = infoSet.toArray(new ThreadMonitorInfo[infoSet.size()]);
        updateRow(array);
        
    }
}
