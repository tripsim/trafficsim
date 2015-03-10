A Web-based Traffic Simulation Platform
=======================================
    SimModel     -- GIS-T model for traffic simulation
    SimData      -- Data modual
    SimEngine    -- Traffic simulation model and customized plugin
    SimFramework -- Web frontend for user interaction

Building
--------
1. Install git, mvn, mongoDB java 7/8, tomcat/jetty/(any java servlet container).
2. Install program

	`cd your-path/tripsim`
	`mvn clean install -DskipTests`

3. Start mongo db, configure db.properties (if you want)
4. Drop the war file from simframework to your java servlet server, and start the server

Set up Eclipse Environment
--------------------------
1. Config Maven for eclipse
2. m2e plugin
2. Config Tomcat for eclipse
3. thyemeleaf plugin (optional)
