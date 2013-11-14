/**
 * 
 */
package eagleye.component;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public interface MonitorResultVisitor {

    void collect(MonitorResultCollector collector);
}
