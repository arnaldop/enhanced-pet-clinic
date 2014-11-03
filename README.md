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

You can then access the sample here: [http://localhost:8080/](http://localhost:8080/)

## Spring Boot Remote Shell

To access this application via SSH, use the username sshuser with password sshpassword on port 2000.

Example: ssh -p 2000 sshuser@localhost

Type help for a list of commands. See [http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-connecting-to-the-remote-shell](Spring Boot Reference Manual Section 38.1).

### Use of Profiles

There are currently 2 sets of profiles:
  * Environment
  ** test - allows for access via HTTP and HTTPS
  ** live - only allows access via HTTPS and puts management pages behind under security
  * Database
  ** intdb - uses hsqldb as an internal database
  ** extdb - uses MySQL as an external database
Use different databases depending on profile.
http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/ Section 58.5


## TODO / To Fix

### Spring Boot Auditing

Add implementation for a lock-out policy based on authentication failures.

### Packaging executable jar application

http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/ Section 53.1

### Logout (Spring Security)

When session management is enabled with a lockout policy for maximum sessions reached, logging out prevents the user from re-logging in. So far I have been unable to figure out how to implement the removal of the session upon logout.

### Actuator

Make index page for Actuator services, available to an ADMIN or superuser.

### "Whitelabel" Error Page

Ensure error handling is happening properly.
