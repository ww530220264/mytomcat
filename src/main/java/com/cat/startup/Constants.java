package com.cat.startup;

/**
 * String constants for the startup package.
 * <br>
 * Note that some values include a leading '/' and that some do not. This is
 * intentional based on how the values are used.
 *
 * @author Craig R. McClanahan
 */
public final class Constants {

    public static final String Package = "org.apache.catalina.startup";

    public static final String ApplicationContextXml = "META-INF/context.xml";
    public static final String ApplicationWebXml = "/WEB-INF/web.xml";
    public static final String TomcatWebXml = "/WEB-INF/tomcat-web.xml";
    public static final String DefaultContextXml = "conf/context.xml";
    public static final String DefaultWebXml = "conf/web.xml";
    public static final String HostContextXml = "context.xml.default";
    public static final String HostWebXml = "web.xml.default";
    public static final String WarTracker = "/META-INF/war-tracker";

    /**
     * A dummy value used to suppress loading the default web.xml file.
     *
     * <p>
     * It is useful when embedding Tomcat, when the default configuration is
     * done programmatically, e.g. by calling
     * <code>Tomcat.initWebappDefaults(context)</code>.
     *
//     * @see Tomcat
     */
    public static final String NoDefaultWebXml = "org/apache/catalina/startup/NO_DEFAULT_XML";

    /**
     * Name of the system property containing
     * the tomcat product installation path
     */
    public static final String CATALINA_HOME_PROP = "catalina.home";

    /**
     * Name of the system property containing
     * the tomcat instance installation path
     */
    public static final String CATALINA_BASE_PROP = "catalina.base";
}
