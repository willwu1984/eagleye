/**
 *
 */
package eagleye;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * @author xiemalin
 * @since 1.0.0.0
 */
public class ClassLoaderRenderManager {

    private static final String CONF = "/render.properties";
    private static final String STATUS_CONF = "/addon_status.properties";
    private static final String SIMPLE_ADDON = "*";
    public static final String STATUS_ON = "on";
    public static final String STATUS_OFF = "off";

    private static Map<String, Set<ClassBytecodeRender>> rendersMap =
        new HashMap<String, Set<ClassBytecodeRender>>();

    public static final Map<String, Boolean> simpleAddonClassesMap =
        new HashMap<String, Boolean>();
    public static final Map<String, Managable> simpleAddonsMap =
        new HashMap<String, Managable>();

    private static final int DEFAULT_COLLECT_INTERVAL = 1;

    static {
        InputStream is;
        is = ClassLoaderRenderManager.class.getResourceAsStream(STATUS_CONF);
        if (is == null) {
            throw new RuntimeException("Configuration file not found.'" +
                    CONF + "'");
        }
        Properties p = new Properties();
        Map<String, Boolean> statusMap = new HashMap<String, Boolean>();
        try {
            p.load(is);

            Iterator<Entry<Object, Object>> iter = p.entrySet().iterator();
            String classNames;
            String status;
            while (iter.hasNext()) {
                Entry<Object, Object> next = iter.next();

                classNames = next.getKey().toString();
                status = next.getValue().toString();
                boolean active = true;
                if (STATUS_OFF.equals(status)) {
                    active = false;
                }
                statusMap.put(classNames, active);

            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        is = ClassLoaderRenderManager.class.getResourceAsStream(CONF);
        if (is == null) {
            throw new RuntimeException("Configuration file not found.'" +
                    CONF + "'");
        }
        p = new Properties();
        try {
            p.load(is);
            Iterator<Entry<Object, Object>> iter = p.entrySet().iterator();
            String classNames;
            String renderClass;
            while (iter.hasNext()) {
                Entry<Object, Object> next = iter.next();

                classNames = next.getKey().toString();
                renderClass = next.getValue().toString();
                if (classNames.startsWith(SIMPLE_ADDON) ){
                    boolean active = true;
                    if (statusMap.containsKey(renderClass)) {
                        active = statusMap.get(renderClass);
                    }
                    simpleAddonClassesMap.put(renderClass, active);
                    continue;
                }

                registerRender(classNames, renderClass);
            }

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void registerRender(String classNames, String renderClass) {
        renderClass = renderClass.trim();
        try {
            int interval = DEFAULT_COLLECT_INTERVAL;
            if (renderClass.indexOf(",") != -1) {
                String[] vals = renderClass.split(",");
                if (vals == null || vals.length != 2) {
                    throw new RuntimeException("Invalid render class name '" +
                            renderClass + "'");
                }
                renderClass = vals[0];
                interval = Integer.valueOf(vals[1]);
            }

            ClassBytecodeRender render;
            Class c = Class.forName(renderClass);
            if (!ClassBytecodeRender.class.isAssignableFrom(c)) {
                System.out.println("Register '" + classNames + "' with render class" +
                        "' " + renderClass +
                        "' ignored due to not a ClassBytecodeRender class.");
                return;
            }

            render = (ClassBytecodeRender) c.newInstance();
            render.setInterval(interval);

            String[] keys = classNames.split(",");
            for (String key : keys) {
                registerRender(key, render);
            }

        } catch (Exception e) {
            System.err.println("Register '" + classNames + "' with render class" +
            		"' " + renderClass + "' failed.");
            e.printStackTrace();
        }
    }

    /**
     * @param key
     * @param render
     */
    public static void registerRender(String key, ClassBytecodeRender render) {
        Set<ClassBytecodeRender> renderSet = rendersMap.get(key);
        if (renderSet == null) {
            renderSet = new HashSet<ClassBytecodeRender>();
            rendersMap.put(key, renderSet);
        }
        renderSet.add(render);
    }

    public static byte[] loadClass(String name, String className,
            byte[] bytes, ClassLoader cl) {

        Set<ClassBytecodeRender> renderSet = rendersMap.get(className);
        if (renderSet == null || renderSet.isEmpty()) {
            return bytes;
        }

        for (ClassBytecodeRender render : renderSet) {
            render.setActive(true);
            bytes = render.render(name, className, bytes, cl);
        }

        return bytes;
    }
}
