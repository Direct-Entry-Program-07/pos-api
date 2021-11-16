# POS API
The REST API for the Point of Sale System

## Getting Started
* Fork the repository to your playground. **This is important.**
* Clone the project 
`git clone https://github.com/Direct-Entry-Program-7/pos-api.git`
* Open the project with **IntelliJ IDEA** (Make sure to open via `pom.xml`)
* Open the Database Tool Window and setup a new MySQL datasource
* Open the [db-script.sql]() file and execute the whole script via the database console
* Open the [src/main/webapp/META-INF/context.xml]() and change the `username` and `password`
* Double press the `Ctrl` key or open the integrated terminal and then execute `mvn clean package`
* Press `F9` and select `Edit Configurations`
  * Click on `Add new run configuration` and set up a Local Tomcat Server
  * On the `Server` tab, under the `Open Browser` uncheck the `After Launch` check box
  * On the `Deployment` tab, add the artifact and set the context path to `pos`
* Run the Tomcat Server and wait until the deployment is successful (Check the log)
* Open an HTTP GET request and try [http://localhost:8080/pos/customers]() and it should display a JSON array, if not please troubleshoot the error before proceeding

## Change Log
### [v1.0.0](https://github.com/Direct-Entry-Program-7/pos-api/releases/tag/v1.0.0)
* Compound Architecture (MVC2, Layered, Client-Server, SOA)
* DAO Design Pattern (Java EE)
* DTO vs. Entity (Java EE)
* Facade Design Pattern (Structural)
* Factory Design Pattern (Creational)
* Strategy Design Pattern (Behavioral)
* Marker Design Pattern (Structural)
* Null Object Design Pattern (Behavioral) [Optional]
* Dependency Injection Design Pattern (Without IoC) (Creational)
* KISS, WET, DRY Design Principles
* SOLID Principles
* Java Generics and Generic Programming Paradigm
* Entity DTO Mapper
* ThreadLocal

### [v0.0.1](https://github.com/Direct-Entry-Program-7/pos-api/releases/tag/v0.0.1)
* Servlet 5 (Tomcat 10)
* JSONB (Yasson)
* DTO Design Pattern
* Junit 5
* Tomcat DBCP

## License
Copyright © 2021 DEP7. All Rights Reserved.
License under the [MIT](LICENSE) license.