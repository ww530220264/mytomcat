package com.cat;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.UnavailableException;

public interface Wrapepr {

    public static final String ADD_MAPPING_EVENT = "addMapping";
    public static final String REMOVE_MAPPING_EVENT = "removeMapping";
    long getAvailable();
    void setAvailable(long available);
    int getLoadOnStartup();
    void setLoadOnStartup(int loadOnStartup);
    String getRunAs();
    void setRunAs(String runAs);
    String getServletClass();
    void setServletClass(String servletClass);
    String[] getServletMethods();
    boolean isUnAvailable();
    Servlet getServlet();
    void setServlet(Servlet servlet);
    void addInitParameter(String name,String value);
    void addMapping(String mapping);
    void addSecurityReference(String name,String link);
    Servlet allocate();
    void deallocate(Servlet servlet);
    String findInitParameter(String name);
    String[] findInitParameters();
    String[] findMappings();
    String findSecurityReference(String name);
    String[] findSecurityReferences();
    void incrementErrorCount();
    void load();
    void removeInitParameter(String name);
    void removeMapping(String mapping);
    void removeSecurityReference(String name);
    void unavailable(UnavailableException e);
    void unload();
    MultipartConfigElement getMultipartConfigElement();
    void setMultipartConfigElement(MultipartConfigElement element);
    boolean isAsyncSupported();
    void setAsyncSupported(boolean asyncSupported);
    boolean isEnabled();
    void setEnabled(boolean enabled);
    boolean isOverridable();
    void setOverridable(boolean overridable);
}
