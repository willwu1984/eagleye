/**
 * 
 */
package eagleye.addons;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import eagleye.ClassLoaderRenderManager;
import eagleye.MonitorWatcherRegister;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public class AddonRegister {


    public AddonRegister() {
        
        Map<String, Boolean> addonClasses = ClassLoaderRenderManager.simpleAddonClassesMap;
        if (addonClasses != null) {
            Iterator<Entry<String, Boolean>> iter = addonClasses.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, Boolean> next = iter.next();
                
                String name = next.getKey();
                try {
                    Class c = Class.forName(name);
                    Object instance = c.newInstance();
                    MonitorWatcherRegister.register(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
}
