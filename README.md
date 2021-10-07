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

```
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
```
**NOTE**: The dependency used for WebDriverManager stopped working for some reason when updating to the latest version. 
It's better to work with this dependency because it allows you to run the tests in different browsers without having to download each driver. 
However, all codes shown in here are done calling directly a ChromeDriver so the tests can run. If WebDriverManager is fixes, make sure to use it for better integration. 


## Getting started with testing 
Alright, you should know there are different fraworks for testing, such as JUnit and TestNG. However, TestNG is extremely powerful given the anottations you can use that allow you to execute scripts before or after certain events. 
Here are some: 

###TestNG Anottations
```
@AfterTest //Runs this method after each Test, indistinct from classes
public void MethodThatRunsAfterEachTest(){

}
@BeforeTest //Runs this method before each test, indistinct from classes 
public void MethodThatRunsBeforeEachTest(){

}
@BeforeSuite //Runs this method before executing the whole suite of tests, indistinct from classes
public void MethodThatRunsBeforeWholeSuite(){

}
@AfterSuite //Runs this method after executing the whole suite of tests, indistinct from classes
public void MethodThatRunsAfterWholeSuite(){

}
@BeforeMethod //Runs this method before every method that is present in the same class
public void MethodThatRunsBeforeEveryMethodInThisClass(){

}

@AfterMethod //Runs this method after every method that is present in the same class
public void MethodThatRunsAfterEveryMethodInThisClass(){

}

@DataProvider(name="name-of-data-provider") //Creates a method that loads specific data into a test case. This method returns a multi dimensional array. If you need different data types, use Object [][], if it's a specific data type String[][], int[][], etc. 
public Object[][] dataProvider(){
	return new Object[][] {...}
}

@FindBy()//Used for POM (page object model). Several arguments can be passed, id, name, class, etc. 

@Parameter("parameterName") //This declares that this method uses a certain parameter declarated previously, when creating the method, the first argument is automatically replaced by the parameter
public void ThisMethodTakesTheParamenterInTheFirstArgument(String parameterPasses){
	
}

@Test //Identifies the current method as a test, extra parameters 
//@Test(enabled = false) ignores the test case
//@Test(timeOut = 4000) waits for a specific test case for 4000 milliseconds
//@Test(groups = {"tagName"}) creates a tag for the test in order to group it with some other cases
//@Test(dependsOnMethods = {"methodName"}) makes this test dependant of another, which will force an execution AFTER the dependee
public void TestingSomething(){

}

```

Use these accordingly to your testing needs. For example, a BeforeTest can be used to create the WebDriver Object every time you run a new test, or an AfterTest can clean up remaining data. 
It's important to use these annotations, since suites run the Tests in *alphabetical order*, so take that into consideration. 

If you right click on your Maven Project, you'll be given the option to convert to TestNG. Doing so, will create an xml file corresponding to the Suite. 
Using this file correctly allows you to run certain tests given your parameters in a single click, so it's kind of important

###XML usage

To add **parameters**: 

```
<suite>
	<parameter name = "parameterName" value = "google.com">

```
To add **Listener**:
```
<suite>
	<listeners>
		<listener name-class = "package.ListenerClassName"> <!--The value of name class is the location of the class including the package and class name-->
	</listeners>
```
To add a **profile** (for Maven execution):
<suite>
	<profiles>
		<profile>
			<id> profileID </id>
		</profile>
		<build>
		...
		</build>
	</profiles> 

To make the suites executable frrom maven commands, add 2 things **Maven surefire plug-in** and **resource tags** (if there are Listeners)
```
<build>
	<resources>
		<resource>
			<directory> Path to resource folder </directory>
			<filtering> true </filtering>
		</resource>
	</resources>
	<plugins>
		<dependency>
    			<groupId>org.apache.maven.plugins</groupId>
    			<artifactId>maven-surefire-plugin</artifactId>
    			<version>3.0.0-M5</version>
		</dependency>
	</plugins>
</build>
```

To **exclude test cases** (runs everything in class, except for what is excluded):
```
<class name =testNgClass">
	<methods>
		<exclude name = "method name in  class"/>
	</methods>
</class>
```
To **include test cases** (runs only the specified method in the class):
```
<class name =testNgClass">
	<methods>
		<include name = "method name in  class"/>
	</methods>
</class>
```
To run test cases which are identified **by a tag name** (even if they are form different classes):
```
<test name ="module name">
	<groups>
		<run>
			<include name = "tagName"/>
		</run>
	</groups>
	<classes>
	...
	</classes>
</test>
```
###Using the Page Object Model (POM)
So, this is a way of writing your tests that makes your code really mantainable. You can check out a whole implementation in the examples, however you should understand what it is and the main lines that are used to get you started on POM. 
POM basically considers each page as a whole object (or class, if you want to see it that way). There are certain attributes in that page (web elements) such as buttons, links and forms and there are certain behaviors attached to the page, like pressing the button and sending the form. 
So, you declare each one of this attributes as a variable using the @FindBy anottation and create methods for interacting with the behaviors of the page. 

So if you're testing a site, for each page on a site you create a class and just instance it in the corresponding test you're writing. 

When instantiating this object, you have to do it using the PageFactory, as follows: 
```
PageClassName pageClassName = PageFactory.initElements(driver,PageClassName.class);
```
This allows to initiate the elements in the other pages without using the whole method call for identifying elements (driver.findElement bla bla bla)  

In your Page Class, create a constructor and private driver object
```
private WebDriver driver;

public PageClassName(WebDriver driver){
	this.driver = driver;
	}
```
And you're all setup to start referencing objects with the @FindBy anottations. 