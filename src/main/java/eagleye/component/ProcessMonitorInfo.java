/**
 * 
 */
package eagleye.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public class ProcessMonitorInfo extends MonitorInfo {

    /**
     * serial Version UID
     */
    private static final long serialVersionUID = -452115408598829173L;

    //begin time
    private Date begin;
    //finish time
    private Date end;
    
    private boolean success = true;
    private String errorMsg;
    
    private List<List<Object>> data;
    //format data
    public void addRow(Object[] cells) {
        if (cells == null) {
            return;
        }
        if (data == null) {
            data = new ArrayList<List<Object>>();
        }
        
        data.add(Arrays.asList(cells));
    }
    
    
    
    public Date getBegin() {
        return begin;
    }
    public void setBegin(Date begin) {
        this.begin = begin;
    }
    public Date getEnd() {
        return end;
    }
    public void setEnd(Date end) {
        this.end = end;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void collect(MonitorResultCollector collector) {
        //need to override
    }
    
}
