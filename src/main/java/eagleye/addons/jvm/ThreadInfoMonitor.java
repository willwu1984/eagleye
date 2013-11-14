/**
 *
 */
package eagleye.addons.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import eagleye.AbstractPeriodMonitorable;
import eagleye.component.PeriodMonitorInfo;
import eagleye.component.jvm.ThreadMonitorInfo;
import eagleye.component.jvm.ThreadsMonitorInfo;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public final class ThreadInfoMonitor extends AbstractPeriodMonitorable {

    static final ThreadInfoMonitor INSTANCE = new ThreadInfoMonitor();

    public static boolean STACK_TRACES_ENABLED = true;

    ThreadsMonitorInfo tInfo;

    private String id = "PeriodMonitorInfo-" + UUID.randomUUID().toString().replace("-", "");

    public static ThreadInfoMonitor getInstance() {
        return INSTANCE;
    }

    public ThreadInfoMonitor() {
        tInfo = new ThreadsMonitorInfo();
        tInfo.setId(id);
        tInfo.setHeader("name", "id", "priority", "daemon", "state", "cpuTimeMillis",
                "userTimeMillis", "deadlocked", "globalThreadId", "stackTrace");
    }

    /* (non-Javadoc)
     * @see eagleye.PeriodMonitorable#doMonitorEagleye()
     */
    public PeriodMonitorInfo doMonitorEagleye() {

        tInfo.set(buildThreadInformationsList());
        tInfo.updateRow();
        tInfo.update();

        return tInfo;
    }


    /* (non-Javadoc)
     * @see eagleye.PeriodMonitorable#getIntervalEagleye()
     */
    public int getIntervalEagleye() {
        return 0;
    }

    static List<ThreadMonitorInfo> buildThreadInformationsList() {
        final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        final List<Thread> threads;
        final Map<Thread, StackTraceElement[]> stackTraces;
        if (STACK_TRACES_ENABLED) {
            stackTraces = Thread.getAllStackTraces();
            threads = new ArrayList<Thread>(stackTraces.keySet());
        } else {
            threads = getThreadsFromThreadGroups();
            final Thread currentThread = Thread.currentThread();
            stackTraces = Collections.singletonMap(currentThread, currentThread.getStackTrace());
        }

        final boolean cpuTimeEnabled = threadBean.isThreadCpuTimeSupported()
                && threadBean.isThreadCpuTimeEnabled();
        final long[] deadlockedThreads = getDeadlockedThreads(threadBean);
        final List<ThreadMonitorInfo> threadInfosList = new ArrayList<ThreadMonitorInfo>(
                threads.size());
        // hostAddress
        final String hostAddress = getHostAddress();
        for (final Thread thread : threads) {
            final StackTraceElement[] stackTraceElements = stackTraces.get(thread);
            final List<StackTraceElement> stackTraceElementList = stackTraceElements == null ? null
                    : new ArrayList<StackTraceElement>(Arrays.asList(stackTraceElements));
            final long cpuTimeMillis;
            final long userTimeMillis;
            if (cpuTimeEnabled) {
                cpuTimeMillis = threadBean.getThreadCpuTime(thread.getId()) / 1000000;
                userTimeMillis = threadBean.getThreadUserTime(thread.getId()) / 1000000;
            } else {
                cpuTimeMillis = -1;
                userTimeMillis = -1;
            }
            final boolean deadlocked = deadlockedThreads != null
                    && Arrays.binarySearch(deadlockedThreads, thread.getId()) >= 0;
            // stackTraceElementList
            threadInfosList.add(new ThreadMonitorInfo(thread, stackTraceElementList,
                    cpuTimeMillis, userTimeMillis, deadlocked, hostAddress));
        }
        return threadInfosList;
    }

    static List<Thread> getThreadsFromThreadGroups() {
        ThreadGroup group = Thread.currentThread().getThreadGroup(); // NOPMD
        while (group.getParent() != null) {
            group = group.getParent();
        }
        final Thread[] threadsArray = new Thread[group.activeCount()];
        group.enumerate(threadsArray, true);
        return Arrays.asList(threadsArray);
    }

    private static long[] getDeadlockedThreads(ThreadMXBean threadBean) {
        final long[] deadlockedThreads;

/*        if (SYNCHRONIZER_ENABLED && threadBean.isSynchronizerUsageSupported()) {
 *          //only works at JDK 6
            deadlockedThreads = threadBean.findDeadlockedThreads();
        } else {*/
            deadlockedThreads = threadBean.findMonitorDeadlockedThreads();
/*        }*/
        if (deadlockedThreads != null) {
            Arrays.sort(deadlockedThreads);
        }
        return deadlockedThreads;
    }

    static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (final UnknownHostException ex) {
            return "unknown";
        }
    }

}
