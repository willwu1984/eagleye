/**
 * 
 */
package eagleye;

/**
 * To support management for component.<br>
 * All monitor plug-ins should implement this interface.
 * 
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public interface Managable {

    boolean isActive();
    
    void setActive(boolean active);
}
