/**
 * 
 */
package eagleye.component.jvm;

import java.io.Serializable;
import java.util.List;


/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public class ThreadMonitorInfo implements Serializable {

    /**
     * serial Version UID
     */
    private static final long serialVersionUID = -4213446340104714870L;

    private String name;
    private long id;
    private int priority;
    private boolean daemon;
    private Thread.State state;
    private long cpuTimeMillis;
    private long userTimeMillis;
    private boolean deadlocked;
    private String globalThreadId;
    private List<StackTraceElement> stackTrace;
    
    
    public ThreadMonitorInfo() {
    }
    
    public ThreadMonitorInfo(Thread thread, List<StackTraceElement> stackTrace,
            long cpuTimeMillis, long userTimeMillis, boolean deadlocked, 
            String hostAddress) {
        super();
        assert thread != null;
        assert stackTrace == null || stackTrace instanceof Serializable;

        this.name = thread.getName();
        this.id = thread.getId();
        this.priority = thread.getPriority();
        this.daemon = thread.isDaemon();
        this.state = thread.getState();
        this.stackTrace = stackTrace;
        this.cpuTimeMillis = cpuTimeMillis;
        this.userTimeMillis = userTimeMillis;
        this.deadlocked = deadlocked;
        this.globalThreadId = buildGlobalThreadId(thread, hostAddress);
    }
    
    private static String buildGlobalThreadId(Thread thread, String hostAddress) {
        return /*PID.getPID() +*/ '_' + hostAddress + '_' + thread.getId();
    }

    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }
    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
    /**
     * @return the daemon
     */
    public boolean isDaemon() {
        return daemon;
    }
    /**
     * @param daemon the daemon to set
     */
    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }
    /**
     * @return the state
     */
    public Thread.State getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(Thread.State state) {
        this.state = state;
    }
    /**
     * @return the cpuTimeMillis
     */
    public long getCpuTimeMillis() {
        return cpuTimeMillis;
    }
    /**
     * @param cpuTimeMillis the cpuTimeMillis to set
     */
    public void setCpuTimeMillis(long cpuTimeMillis) {
        this.cpuTimeMillis = cpuTimeMillis;
    }
    /**
     * @return the userTimeMillis
     */
    public long getUserTimeMillis() {
        return userTimeMillis;
    }
    /**
     * @param userTimeMillis the userTimeMillis to set
     */
    public void setUserTimeMillis(long userTimeMillis) {
        this.userTimeMillis = userTimeMillis;
    }
    /**
     * @return the deadlocked
     */
    public boolean isDeadlocked() {
        return deadlocked;
    }
    /**
     * @param deadlocked the deadlocked to set
     */
    public void setDeadlocked(boolean deadlocked) {
        this.deadlocked = deadlocked;
    }
    /**
     * @return the globalThreadId
     */
    public String getGlobalThreadId() {
        return globalThreadId;
    }
    /**
     * @param globalThreadId the globalThreadId to set
     */
    public void setGlobalThreadId(String globalThreadId) {
        this.globalThreadId = globalThreadId;
    }
    /**
     * @return the stackTrace
     */
    public List<StackTraceElement> getStackTrace() {
        return stackTrace;
    }
    /**
     * @param stackTrace the stackTrace to set
     */
    public void setStackTrace(List<StackTraceElement> stackTrace) {
        this.stackTrace = stackTrace;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ThreadMonitorInfo [name=" + name + ", id=" + id + ", priority="
                + priority + ", daemon=" + daemon + ", state=" + state
                + ", cpuTimeMillis=" + cpuTimeMillis + ", userTimeMillis="
                + userTimeMillis + ", deadlocked=" + deadlocked
                + ", globalThreadId=" + globalThreadId + ", stackTrace="
                + stackTrace + "]";
    }
    
    
}
