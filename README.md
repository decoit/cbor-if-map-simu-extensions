# DECOIT CBOR-IF-MAP Bridge - SIMU Extensions
This library provides mappings for the IF-MAP extensions of the open-source components that were developed during the [SIMU research project](http://www.simu-project.de).

It uses the extension interface of our [DECOIT CBOR-IF-MAP Bridge - TNC Base Elements](https://github.com/decoit/cbor-if-map-tnc-base) library to extend it with additional identifiers and metadata.

## Preparation
The following requirements must be met to compile and use this library:

* Java 8 or higher
* Maven 3
* [XML-CBOR-Dictionary](https://github.com/decoit/cbor-xml-dictionary) installed in local Maven repository
* [DECOIT CBOR-IF-MAP Bridge - TNC Base Elements](https://github.com/decoit/cbor-if-map-tnc-base) installed in local Maven repository

To compile this project the Oracle JDK is preferred but it may work as well on other JDK implementations. Any Java 8 compatible JRE (Oracle, OpenJDK, Apple) should be able to run the application.

## Installation
Follow these steps to compile the project and install the JAR to your local Maven repository:

* Open a command prompt and change directory to the root of this project
* Execute `mvn install`

## Usage
To use this library in your application simply add the dependency to your pom.xml file:

```xml
<dependency>
    <groupId>de.decoit.simu</groupId>
    <artifactId>cbor-ifmap-simu</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## License
The source code and all other contents of this repository are copyright by DECOIT GmbH and licensed under the terms of the [Apache License Version 2.0](http://www.apache.org/licenses/). A copy of the license may be found inside the LICENSE file.