# WebTesting

This repo serves as notes for Web Testing using Selenium. The notes cover from the installation, to different resoruces and examples to be used in Web Testing. 

## Installation 
For running test cases for Web with Selenium three things are needed, a JDK, an IDE and Dependencies for the TestNG framework.  

1. JDK: Just head over to https://www.oracle.com/java/technologies/downloads/ and download your Java version and install normally using the Wizard. 

2. IDE: There're different options. I've used both Eclipse and IntelliJ. IntelliJ already has a setu up plugin for TestNG, so it's ready to go. 
If you prefer using Eclipse, here goes: 

In the Toolbar, click on Help and then click on Install New Software

A pop-up window should show up and in the "Work with:" field, paste the site for release: https://testng.org/testng-eclipse-update-site

Clicking on next, and following the process in Eclipse should get you with your plug-in installed. 

3. Dependencies: These thingies are what allow you not to have to install the jars for each framework you use. 
In order to use these, you have to setup a Maven project, no archetype needed. Once you've setup your project, head over to the pom.xml and paste the following dependencies: 

'''
<dependencies>

    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>3.13.0</version>
      <scope>test</scope>
    </dependency>

    <!-- https://github.com/bonigarcia/webdrivermanager -->
    <dependency>
      <groupId>io.github.bonigarcia</groupId>
      <artifactId>webdrivermanager</artifactId>
      <version>2.2.4</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.0.13</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>org.testng</groupId>
      <artifactId>testng</artifactId>
      <version>6.14.3</version>
      <scope>test</scope>
    </dependency>

  </dependencies>
	
''' 
