/**
 * 
 */
package eagleye.component.datasource;

import eagleye.component.PeriodMonitorInfo;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public class PooledDataSourceMonitorInfo extends PeriodMonitorInfo {

    /**
     * serial Version UID
     */
    private static final long serialVersionUID = -3479202862932246660L;
    
    private int minSize;
    private int maxSize;
    private int idlSize;
    private int busySize;
    private int connectedSize;
    
    public PooledDataSourceMonitorInfo() {
    }
    
    public PooledDataSourceMonitorInfo(String id) {
        setId(id);
    }
    
    public void updateRow() {
        updateRow(minSize, maxSize, idlSize, busySize, connectedSize);
    }
    
    /**
     * @return the minSize
     */
    public int getMinSize() {
        return minSize;
    }
    /**
     * @param minSize the minSize to set
     */
    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }
    /**
     * @return the maxSize
     */
    public int getMaxSize() {
        return maxSize;
    }
    /**
     * @param maxSize the maxSize to set
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
    /**
     * @return the idlSize
     */
    public int getIdlSize() {
        return idlSize;
    }
    /**
     * @param idlSize the idlSize to set
     */
    public void setIdlSize(int idlSize) {
        this.idlSize = idlSize;
    }
    /**
     * @return the busySize
     */
    public int getBusySize() {
        return busySize;
    }
    /**
     * @param busySize the busySize to set
     */
    public void setBusySize(int busySize) {
        this.busySize = busySize;
    }
    /**
     * @return the connectedSize
     */
    public int getConnectedSize() {
        return connectedSize;
    }
    /**
     * @param connectedSize the connectedSize to set
     */
    public void setConnectedSize(int connectedSize) {
        this.connectedSize = connectedSize;
    }
    
    

}
