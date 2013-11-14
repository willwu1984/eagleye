package eagleye.addons.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import eagleye.AbstractPeriodMonitorable;
import eagleye.component.PeriodMonitorInfo;
import eagleye.component.jvm.MemMonitorInfo;

public class MemInfoMonitor extends AbstractPeriodMonitorable {
	MemMonitorInfo mInfo;
	private String id = "PeriodMonitorInfo-" + UUID.randomUUID().toString().replace("-", "");
	public MemInfoMonitor() {
		mInfo=new MemMonitorInfo();
		mInfo.setId(id);
		//mInfo.setHeader("HeapUsage","nonHeapUsage","totalMem");
	}
	@Override
	public PeriodMonitorInfo doMonitorEagleye() {
		mInfo.set(buildMemInformationsList(mInfo));
		//mInfo.updateRow();
		return mInfo;
	}

	@Override
	public int getIntervalEagleye() {
		return 0;
	}
	static ArrayList<String> buildMemInformationsList(MemMonitorInfo mInfo){
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage memUsage = memoryMXBean.getHeapMemoryUsage();
		MemoryUsage noHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> arrayList = new ArrayList<String>();
		nameList.add("HeapUsed");
		long headUsed= (memUsage.getUsed()/1024);
		arrayList.add(Long.toString(headUsed));
		long nonHeapUsed=noHeapMemoryUsage.getUsed()/1024;
		nameList.add("nonHeapUsed");
		arrayList.add(Long.toString(nonHeapUsed));
		long totalHeapUsed=(memUsage.getMax()+noHeapMemoryUsage.getMax())/1024;
		nameList.add("TotalUsed");
		arrayList.add(Long.toString(totalHeapUsed));

		List<MemoryPoolMXBean> mpmList=ManagementFactory.getMemoryPoolMXBeans();
	    for(MemoryPoolMXBean mpm:mpmList){
	    	nameList.add(mpm.getName().replace(" ", "_"));
	    	arrayList.add(Long.toString(mpm.getUsage().getUsed()));
	    }
	    mInfo.setHeader((String[]) nameList.toArray(new String[1]));
		return arrayList;
	}
}
