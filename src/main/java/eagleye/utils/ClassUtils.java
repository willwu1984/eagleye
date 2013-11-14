/*
 * 
 */
package eagleye.utils;


/**
 * Miscellaneous class utility methods. Mainly for internal use within the
 * framework; consider Jakarta's Commons Lang for a more comprehensive suite
 * of class utilities.
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public class ClassUtils {
    
    /** The ".class" file suffix */
    public static final String CLASS_FILE_SUFFIX = ".class";
    
    /** The package separator character '.' */
    public static final char PACKAGE_SEPARATOR = '.';

    /**
     * Replacement for <code>Class.forName()</code> that also returns Class instances
     * for primitives (like "int") and array class names (like "String[]").
     * <p>Always uses the default class loader: that is, preferably the thread context
     * class loader, or the ClassLoader that loaded the ClassUtils class as fallback.
     * @param name the name of the Class
     * @return Class instance for the supplied name
     * @throws RuntimeException wrapped as {@link RuntimeException} if the class was not found
     * @see Class#forName(String, boolean, ClassLoader)
     * @see #getDefaultClassLoader()
     */
    public static Class<?> forName(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    /**
     * <p>
     * Loads the class with the specified <a href="#name">binary name</a>.
     * This method searches for classes in the same manner as the {@link
     * #loadClass(String, boolean)} method.  It is invoked by the Java virtual
     * machine to resolve class references.  Invoking this method is equivalent
     * to invoking {@link #loadClass(String, boolean) <tt>loadClass(name,
     * false)</tt>}.  </p>
     * 
     * @param clazzName
     * @param cl
     * @return
     */
    public static Class<?> loadClass(String clazzName, ClassLoader cl) {
        try {
            return cl.loadClass(clazzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
  
    /**
     * <p>
     * Loads the class with the specified <a href="#name">binary name</a>.
     * This method searches for classes in the same manner as the {@link
     * #loadClass(String, boolean)} method.  It is invoked by the Java virtual
     * machine to resolve class references.  Invoking this method is equivalent
     * to invoking {@link #loadClass(String, boolean) <tt>loadClass(name,
     * false)</tt>}.  </p>
     * 
     * @param clazzName
     * @param cl
     * @return
     */
    public static Class<?> loadClassSilently(String clazzName, ClassLoader cl) {
        try {
            return cl.loadClass(clazzName);
        } catch (Throwable e) {
        }
        return null;
    }    
    
   
    /**
     * Returns the internal name of the class corresponding to this object or
     * array type. The internal name where '.' are replaced by '/'. This method
     * should only be used for an object or array type.
     * 
     * @param name
     * @return
     */
    public static String getInternalName(String name) {
        if (name == null) {
            return null;
        }
        return name.replace(PACKAGE_SEPARATOR, '/');
    }
    
    /**
     * Returns the internal name of the class corresponding to this object or
     * array type. The internal name where '/' are replaced by '.'. This method
     * should only be used for an object or array type.
     * 
     * @param name
     * @return
     */
    public static String getClassName(String name) {
        if (name == null) {
            return null;
        }
        return name.replace('/', PACKAGE_SEPARATOR);      
    }
}
