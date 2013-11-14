package eagleye.component;

import java.util.Map;
import java.util.Set;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public interface MonitorResultCollector {

    /**
     * @param type
     * @param name
     * @param id
     * @param header
     * @param row
     */
    void setRow(String type, String name, String id, 
            Set<String> header, Map<String, Object> row);
    
}
