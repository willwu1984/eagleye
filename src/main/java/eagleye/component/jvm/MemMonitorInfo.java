package eagleye.component.jvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import eagleye.component.PeriodMonitorInfo;

public class MemMonitorInfo extends PeriodMonitorInfo {
	private static final long serialVersionUID = -8874726537993577922L;
	private ArrayList<String> infosArrayList;

	public void set(ArrayList<String> infos) {
		infosArrayList=infos;
		updateRow(infos);
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
}
