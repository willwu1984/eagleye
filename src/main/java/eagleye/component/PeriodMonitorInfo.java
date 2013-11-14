/**
 * 
 */
package eagleye.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Period type monitor info.
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public class PeriodMonitorInfo extends MonitorInfo {

    /**
     * serial Version UID
     */
    private static final long serialVersionUID = 1L;
    
    //begin time
    private Date begin;
    //last update time
    private Date lastUpdate;
    //update interval
    private int interval;
    //update times
    private int updateTimes = 0;
    //if true represent success at last update result
    private boolean lastUpdateResult;
    
    private List<Object> data;
    
    public PeriodMonitorInfo() {
    }
    
    public PeriodMonitorInfo(String id) {
        setId(id);
    }
    
    public int update() {
        updateTimes++;
        return updateTimes;
    }
    
    /**
     * @return the data
     */
    public List<Object> getData() {
        if (data == null) {
            return Collections.emptyList();
        }
        return new ArrayList<Object>(data);
    }


    //format data
    public void updateRow(Object... cells) {
        if (cells == null) {
            return;
        }
        data = (Arrays.asList(cells));
    }
    
    @Override
    public String toString() {
        String ret = "";
        if (getColumns() != null) {
            ret = getColumns().toString() + "\r\n";
        }
        if (data != null) {
            ret += data.toString();
        }
        
        return ret;
    }
    
    /**
     * @return the begin
     */
    public Date getBegin() {
        return begin;
    }
    /**
     * @param begin the begin to set
     */
    public void setBegin(Date begin) {
        this.begin = begin;
    }
    /**
     * @return the lastUpdate
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }
    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /**
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }
    /**
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }
    /**
     * @return the updateTimes
     */
    public int getUpdateTimes() {
        return updateTimes;
    }
    /**
     * @param updateTimes the updateTimes to set
     */
    public void setUpdateTimes(int updateTimes) {
        this.updateTimes = updateTimes;
    }
    /**
     * @return the lastUpdateResult
     */
    public boolean isLastUpdateResult() {
        return lastUpdateResult;
    }
    /**
     * @param lastUpdateResult the lastUpdateResult to set
     */
    public void setLastUpdateResult(boolean lastUpdateResult) {
        this.lastUpdateResult = lastUpdateResult;
    }

    public void collect(MonitorResultCollector collector) {
        //need to override
    }
    
}
