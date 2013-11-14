/**
 *
 */
package eagleye.extension.tomcat;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.catalina.LifecycleException;

import eagleye.ClassLoaderRenderManager;
import eagleye.utils.ClassUtils;
import eagleye.utils.IOUtils;


/**
 * Override tomcat webapp class loader method to intercept <br>
 * all classes loading action<br>
 *
 * <pre>
 * Example usage in tomcat server.xml
 *
 * &lt;Host&gt;
 *    &lt;Context docBase=&quot;E:/examples&quot; path=&quot;/examples&quot; reloadable=&quot;true&quot;&gt;
 *      &lt;Loader loaderClass=&quot;eagleye.extension.tomcat.WebappClassLoader/&gt;
 *    &lt;/Context&gt;
 * &lt;/Host&gt;
 * </per>
 */
public class WebappClassLoader extends org.apache.catalina.loader.WebappClassLoader {

    private final AtomicBoolean pluginOn = new AtomicBoolean(false);

    public WebappClassLoader() {
        super(WebappClassLoader.class.getClassLoader());
    }

    public WebappClassLoader(ClassLoader parent) {
        super(parent);
    }

    public void registerAddons() {
        try {
            Class c = Class.forName("eagleye.addons.AddonRegister", false, this);
            c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() throws LifecycleException {
        super.start();
    }

    @Override
    public synchronized Class loadClass(final String name, boolean resolve)
            throws ClassNotFoundException {

        if (name.equals("java.net.ServerSocket")) {
            String resourceName = ClassUtils.getInternalName(name) + ".class";
            InputStream is = getResourceAsStream(resourceName);
            try {
                byte[] bytes = IOUtils.toByteArray(is);
                bytes = ClassLoaderRenderManager.loadClass(getContextName(),
                        name, bytes, this);
                return defineClass(name, bytes, 0, bytes.length);
            } catch (Exception e) {
                //throw new RuntimeException(e.getMessage(), e);
            }
        }

        if (name.startsWith("java.") || name.startsWith("javax.") ||
                name.startsWith("com.sun.") || name.startsWith("sun.") ||
                name.startsWith("sunw.")) {
            return super.loadClass(name, resolve);
        }

        if (findLoadedClass0(name) != null || findLoadedClass(name) != null) {
            return super.loadClass(name, resolve);
        }

        if (pluginOn.compareAndSet(false, true)) {
            registerAddons();
        }


        String resourceName = ClassUtils.getInternalName(name) + ".class";
        InputStream is = getResourceAsStream(resourceName);
        try {
            byte[] bytes = IOUtils.toByteArray(is);
            bytes = ClassLoaderRenderManager.loadClass(getContextName(),
                    name, bytes, this);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            //throw new RuntimeException(e.getMessage(), e);
        	//System.err.println("Error:"+e.getMessage());
        }

        return super.loadClass(name, resolve);
    }
}
