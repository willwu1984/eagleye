package eagleye.addons.jvm;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.UUID;

import eagleye.AbstractPeriodMonitorable;
import eagleye.component.PeriodMonitorInfo;
import eagleye.component.jvm.GarbageCollectorInfo;

public class GarbageCollectorMonitor extends AbstractPeriodMonitorable {

	static GarbageCollectorInfo gcInfo;
	private String id = "PeriodMonitorInfo-" + UUID.randomUUID().toString().replace("-", "");
	
	public GarbageCollectorMonitor(){
		gcInfo=new GarbageCollectorInfo();
		gcInfo.setId(id);
		gcInfo.setHeader("GC_Major_Time", "GC_Major_Frequency", "GC_Minor_Time", "GC_Minor_Frequency");
	}
	
	@Override
	public PeriodMonitorInfo doMonitorEagleye() {
		gcInfo.set(buildGCInformationsList());
		return gcInfo;
	}

	static ArrayList<String> buildGCInformationsList(){
		long MajorGCCount=0,MajorGCTime=0,MinorGCCount=0,MinorGCTime=0;
		for (final GarbageCollectorMXBean garbageCollector
		        : ManagementFactory.getGarbageCollectorMXBeans()) {
			String name=garbageCollector.getName();
			if (GarbageCollectorInfo.isOldGenCollector(name)) {
				MajorGCCount += garbageCollector.getCollectionCount();
				MajorGCTime += garbageCollector.getCollectionTime();
			} else {
				MinorGCCount += garbageCollector.getCollectionCount();
				MinorGCTime += garbageCollector.getCollectionTime();
			}
		}
		ArrayList<String> arrayList = new ArrayList<String>();
		long fullgcTime=MajorGCTime-gcInfo.getLastMajorGCTime();
		long fullgcCount=MajorGCCount-gcInfo.getLastMajorGCCount();
		long minorgcTime=MinorGCTime-gcInfo.getLastMinorGCTime();
		long minorgcCount=MinorGCCount-gcInfo.getLastMinorGCCount();
		arrayList.add(Long.toString(fullgcTime));
		arrayList.add(Long.toString(fullgcCount));
		arrayList.add(Long.toString(minorgcTime));
		arrayList.add(Long.toString(minorgcCount));
		return arrayList;
	}
	
	@Override
	public int getIntervalEagleye() {
		// TODO Auto-generated method stub
		return 0;
	}

}
