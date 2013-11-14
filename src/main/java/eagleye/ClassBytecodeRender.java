/**
 * 
 */
package eagleye;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public interface ClassBytecodeRender {

    /**
     * To render class bytecode.
     * 
     * @param name
     * @param className
     * @param bytes
     * @param cl
     * @param collectInterval
     * @return
     */
    byte[] render(String name, String className, byte[] bytes, 
            ClassLoader cl);
    
    void setInterval(int interval);
    
    boolean active();
    
    void setActive(boolean active);
}
