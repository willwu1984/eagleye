package eagleye.component.jvm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import eagleye.component.PeriodMonitorInfo;

public class GarbageCollectorInfo extends PeriodMonitorInfo{
	private static final long serialVersionUID = -7375049327342278418L;
	private ArrayList<String> infosArrayList;

	static final List<String> OldGenCollectorNames = Arrays.asList("MarkSweepCompact","PS MarkSweep","ConcurrentMarkSweep");
	static final List<String> YoungGenCollectorNames = Arrays.asList("Copy","ParNew","PS Scavenge");
	
	private long lastMajorGCTime=0;
	private long lastMinorGCTime=0;
	
	private long lastMajorGCCount=0;
	private long lastMinorGCCount=0;
	
	public void set(ArrayList<String> infos) {
		infosArrayList=infos;
		updateRow(infos);
	}

	public static boolean isOldGenCollector(String name){
		if (OldGenCollectorNames.contains(name)) {
			return true;
		}
		if (YoungGenCollectorNames.contains(name)) {
			return false;
		}
		System.out.println("ERROR:unkown collector: "+name);
		return false;
	}
	
	@Override
    public String toString() {
    	List<Object> data=this.getData();
    	HashMap<String , Long> map = new HashMap<String , Long>();
        if (getColumns() != null && data !=null) {
        	int index=0;
        	for (String colString : getColumns()) {
				map.put(colString, Long.parseLong(infosArrayList.get(index)));
				index++;
			}
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }
	
	public long getLastMajorGCTime() {
		return lastMajorGCTime;
	}

	public void setLastMajorGCTime(long lastMajorGCTime) {
		this.lastMajorGCTime = lastMajorGCTime;
	}

	public long getLastMinorGCTime() {
		return lastMinorGCTime;
	}

	public void setLastMinorGCTime(long lastMinorGCTime) {
		this.lastMinorGCTime = lastMinorGCTime;
	}

	public long getLastMajorGCCount() {
		return lastMajorGCCount;
	}

	public void setLastMajorGCCount(long lastMajorGCCount) {
		this.lastMajorGCCount = lastMajorGCCount;
	}

	public long getLastMinorGCCount() {
		return lastMinorGCCount;
	}

	public void setLastMinorGCCount(long lastMinorGCCount) {
		this.lastMinorGCCount = lastMinorGCCount;
	}

}
