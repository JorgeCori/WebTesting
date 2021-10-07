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


## Getting started with testing - Theory
Alright, you should know there are different fraworks for testing, such as JUnit and TestNG. However, TestNG is extremely powerful given the anottations you can use that allow you to execute scripts before or after certain events. 
Here are some: 

### TestNG Anottations
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

### XML usage

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
### Using the Page Object Model (POM)
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


## Getting started with Testing - Practice
1. Abstract WebDriver

Let's setup a utilities class to help us setup the driver and quitting the browser after each test
This file can be found as *AbstractWebDriver.java* in the repository. 

This will be used as a super class, so that we can inherit the driver object.  

Notice there are different lines commented on this file when building the driver. The current method is using a local copy of the ChromeDriver, however, I recommend using the WebDriverManager. 


2. Setup your main class
This class will be used to contain the tests per se. Any number of classes can be used to contain the tests and, using it along with the testng.xml, you can express how you want those tests to execute. 
So, create a new testNG class and make the AbstractWebDriver the super class in order to add functionality of driver building automatically whenever you run a test. 

3. Get going with selectors and stuff
When you're testing on Web, you have to interact with three types of elements: HTML, CSS and JavaScript. 
Usually, you can get a class or id specified for important elements in CSS, however you might need to search certain tags (HTML) or execute JavaScript in order to interact with an element (or perform certain hacks). 

Using the Dev Tools in your browser, you can actually run commands to identify both elements by CSS Selectors and XPath 
It's a good practice to execute your selectors here to corroborate that they do identify the element and, once it's confirmed that itworks, adding it to your testing script. 

So, how to execute this commands? 
For **CSS**: 
```
$$("CSS-Selector")
```
Where CSS Selector can be the id, class, tag, etc. Remember that when looking for ids you use ".", as in ".id-name"; for classes you use "#", as in "#class-name".

For **XPath**:
```
$x("//tag[attribute]")

$X("//tag[@id = 'id-name']")
```
The double slash indicates to search for all elements under the relative path, however, the absolute path can also be specified. 
An @class can also be used to look for the element with the class name specified in XPath, however, it must be noted that this won¿t work if the element has multiple classes. 

### Locating elements
So, you have your selector. You've confirmed this identifies the desired element using the Dev Tools. Now, we search for it using the *findElement* method. 
When you store an element, the type for it is a **WebElement**, so remember to declare it that way. 

```
WebElement nameOfElementByID = driver.findElement(By.id("id-name"));

WebElement nameOfElementByName = driver.findElement(By.name("css-name"));

WebElement nameOfElementByTag = driver.findElement(By.tagName("tag"));

```
Now, when your're using id or names, there's not much trouble since it's non duplicable. However, there may be many elements with an associated tag.
Retrieving an element using the tag method only gets the **first element** with that tag. 
If you want to **retrieve all elements**, you have to store it in a List:
```
List<WebElement> tagElements = driver.findElements(By.tagName("tag"));
```
When you have certain links that are associated to visible text, you can actually retrieve it by checking for the text

```
driver.findElement(By.linkText("www.gooogle.com")); //this method retrieves the element with the exact match

driver.findElement(By.partialLinkText("google")); //this method retrieves any element that contains the argument as a substring 
```

Many times you'll find elements that have no id, name or class (An example of this are tables).
To deal with this, we use CSS Selectors and XPath Selector

```
driver.findElement(By.cssSelector("cssSelector"));

driver.findElement(By.xpath("xpathSelector"));
```
You can get these selectors by inspecting the element, and then right click, copy, and choose xpath selector or css selector. 
Use this sparingly, since many times this cannot be reliable when it's under many divs and stuff. If the website ever changes, you're gonna havve a bad time. 

Usually, the hierarchy is:

*id -> name -> tag name -> CSS/XPath Selector * 

### Caching elements 
Name says it all. You store a parent WebElement and then search inside that element. This has a lot of impact in performance, given that you don't perform searches in the whole page, but only in one element. 

```
WebElement cachedElement = driver.findElement(By.cssSelector("#dataTables-example"));

cachedElement.findeElement(By.cssSelector("#dataColumn-example"));

```

### Interacting with Radio Buttons and Select Boxes and Text areas
Radio Buttons are part of single set. Usually, each option doesn´t have an id, so you usually want to use the name

```
List<WebElement> options = driver.findElements(By.name("name"));
//we loop around the list to get each individual option (or use functional programming)

for(WebElement option:options){
	option.getAttribute("value").equals("...");
}
```

Now onto **Select Boxes**. There are Select Boxes that allow multi selecting, and others that don't, so be careful. 
To interact with these elements, there's a special object we can use
```
Select selectBoxElement = new Select(driver.findElement(By.id("an-example-id")));
```
Now you have several methods at your disposal to deal with these new elements:

```
selectBoxElement.getAllSelectedOptions();

selectBoxElement.selectByValue();// Search with the 'value' attribute

selectBoxElement.selectByVisibleText();// The actual text displayed in the option

selectBoxElement.selectByIndex();// Index starts at 0

selectBoxElement.deselectAll(); 

selectBoxElement.deselectByVisibleText();
```
Finally, **Text Areas** are pretty straight forward

If you want to get the text in a text area, it's usually stored in the attribute "value", so
```
textElement.getAttribute("value");
```
Now, the two standard this you'll be doing with these elements is inputting text and clicking them, so
To **enter text**:
```
textElement.sendKeys("Your string here");
```

To **click**:
```
clickableElement.click();
```

Now, there are some elements that seem clickable but are actually not. Sometimes the parent or child element is the one clickable 
Executing some JavaScript may be an option to get there without having to find the selector. 

```
JavascriptExecutor jse = (JavaScriptExecutor) driver;
WebElement parentElementFromChild = (WebElement) jse.ExecuteScript("return arguments[0].parentNode;", childElement);
```

