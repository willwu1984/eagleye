/**
 * 
 */
package eagleye.component;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public enum MonitorType {
    
    DATASOURCE("DataSource");

    private final String value;

    MonitorType(String value) { this.value = value; }
    
    public String value() { return value; }
    
    @Override
    public String toString() {
        return value;
    }
}
