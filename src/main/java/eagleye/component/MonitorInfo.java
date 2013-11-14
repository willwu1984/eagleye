/**
 * 
 */
package eagleye.component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public abstract class MonitorInfo implements MonitorResultVisitor, Serializable {

    /**
     * serial Version UID
     */
    private static final long serialVersionUID = 953074692125325252L;
    private String id;
    private String memo;
    private String type;
    private Map<String, String> extraInfo;
    
    //formated data
    private List<String> columns;
    /**
     * @return the columns
     */
    public List<String> getColumns() {
        return columns;
    }

    public MonitorInfo() {
        
    }
    
    public void setHeader(String... heads) {
        if (heads == null) {
            return;
        }
        columns = Arrays.asList(heads);
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public String getExtra(String key) {
        if (extraInfo == null) {
            return null;
        }
        return extraInfo.get(key);
    }
    
    public void addExtar(String key, String val) {
        if (extraInfo == null) {
            extraInfo = new HashMap<String, String>();
        }
        extraInfo.put(key, val);
    }
    
    public Map<String, String> getExtras() {
        if (extraInfo == null) {
            return Collections.emptyMap();
        }
        return new HashMap<String, String>(extraInfo);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MonitorInfo)) {
            return false;
        }
        MonitorInfo other = (MonitorInfo) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
    
}
