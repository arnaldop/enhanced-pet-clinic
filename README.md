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

## Tomcat 8

POM <tomcat.version>8.0.8</tomcat.version>

NOTE: Check latest stable version.

## To run this application

Using __Embedded Apache Tomcat__:

    mvn spring-boot:run

You can then access the sample here: [http://localhost:8090/](http://localhost:8090/)

## Spring Boot Remote Shell

To access this application via SSH, use the username sshuser with password sshpassword on port 2000.

Example: ssh -p 2000 sshuser@localhost

Type help for a list of commands. See [http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-connecting-to-the-remote-shell](Spring Boot Reference Manual Section 38.1).

## TODO / To Fix

### Spring Boot Auditing

Add implementation for a lock-out policy based on authentication failures.

### Packaging executable jar application

http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/ Section 53.1

### Use of Profiles

Use different databases depending on profile.
http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/ Section 58.5

### SSL

Make sure http redirects to https.

Make sure static resources are available from http.

http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/ Section 59.6

### SQL Initialization

Set up SQL initialization for both in-memory and external databases.

### Logout (Spring Security)

Currently logout does not invalidate the session, making the user unable to re-log in. 

### Actuator

Make index page for Actuator services, available to an ADMIN or superuser.

### Dandelion Bundles

Currently forms submitted with invalid date get reloaded, but Dandelion is not reloading the bundles, loading the page with no style.

### "Whitelabel" Error Page

Ensure error handling is happening properly.
