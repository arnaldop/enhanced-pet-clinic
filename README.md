enhanced-pet-clinic
===================

Spring Pet Clinic example using Spring-Boot, Thymeleaf, AOP, MVC, Embedded Tomcat, Security, and more.

## Technology stack

 - Spring Boot
 - Thymeleaf
 - Dandelion-Datatables 0.10.0
 - Hibernate Validator
 - WebJars
 - Tomcat 8

## Spring Features

 - Spring JavaConfig
 - Spring Security
 - Spring MVC
 - Spring JPA
 - Spring AOP

## To run this application

Using __Embedded Apache Tomcat__:

	mvn spring-boot:run

You can then access the sample here: [http://localhost:8080/](http://localhost:8080/)

Logins are found in [data.sql](src/main/resources/data.sql). Passwords are the same as the user name.

## Spring Boot Remote Shell

To access this application via SSH, use the username sshuser with password sshpassword on port 2000.

Example: ssh -p 2000 sshuser@localhost

Type help for a list of commands. See [http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-remote-shell](Monitoring and management using a remote shell).

### Use of Profiles

For the sake of simplicity, there are 3 profiles:
  * dev
	* in-memory database (auto-generated HSQLDB with sample data), HTTP (8080) interface only, actuator features are open
  * test
	* external database (auto-generated MySQL with sample data), HTTP (8080) and HTTPS (8443) interfaces, actuator is open
  * live
	* external database (assumes existing MySQL with data), HTTPS (8443) interface only, actuator is secured

Use different databases depending on profile.
[Database Initialization](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-database-initialization)

## HTTP POST vs. PUT
I removed all PUT requests from the application. Please see http://zacharyvoase.com/2009/07/03/http-post-put-diff/ for more information.

## TODO / To Fix

### Spring Boot Auditing

Add implementation for a lock-out policy based on authentication failures.

### Packaging executable jar application

This works, with the caveat that the embedded Tomcat cannot find the keystore in the embedded JAR file, which means the file must be found in the file system and must be properly referenced from application.properties.

### "Whitelabel" Error Page

Ensure error handling is happening properly.
