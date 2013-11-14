eagleye
=======

Eagleye is a simple tool that can monitor Java EE application servers in QA and production environments. 
It is implemented by injecting monitor bytes code to application.Currently it can collect the following infomation:
1. memory info
2. GC info
3. c3p0 info

Installation
------------

    $ git clone https://github.com/willwu1984/eagleye.git
    $ mvn clean install
    $ mv target/eagleye-0.1.jar ${tomcat_install_path}/lib
    $ cp src/resources/*.properties ${tomcat_install_path}/lib

**Configuration**

1. add the following lines in the file WEB-INF/web.xml
    <filter>
      <filter-name>monitor</filter-name>
      <filter-class>eagleye.output.MonitorFilter</filter-class>
    </filter>
    <filter-mapping>
      <filter-name>monitor</filter-name>
      <url-pattern>*.action</url-pattern>
    </filter-mapping>

2. add the following line into your application context in the file WEB-INF/server.xml.Test in follow is your application name.
    <Context docBase="test" path="/test">
      <Loader loaderClass="eagleye.extension.tomcat.WebappClassLoader"/>
    </Context>


How can you get the monitor infomations??
-----------------------
A monitor URI(/monitor/monitorInfo.action) is created if you installed. You can view the infomation by this url such as
[http://localhost:8080/test/monitor/monitorInfo.action](http://localhost:8080/test/monitor/monitorInfo.action)
