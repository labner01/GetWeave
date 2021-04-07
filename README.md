
## What you need to run this app
1. Java 8
2. Git
3. Maven
4. IDE (Intellij is recommended)

## Setup
1. You need to download chrome/firefox drivers from
   - https://chromedriver.chromium.org/downloads
   - https://github.com/mozilla/geckodriver/releases/   
2. Please select the correct version when you download the drivers.
3. Rename drivers as
    - chromedriver_mac
    - chromedriver_linux
   - geckodriver_mac
   - geckodriver_linux
4. Copy drivers in this folder
    - src/test/resources/drivers
5. copy and paste .env_sample file as .env
6. Update your username and password in the .env file
7. .env file is for your convenience. If you put parameters here, you don't need to pass them from the command line.
8. .env file is ignored by git since we don't want to push these credentials to the remote repo. These changes will be on your laptop only.
9. When you run these tests in jenkins, you have to pass those parameters from the command line by
   - mvn clean test -DUSER_EMAIL=test@example.com -DUSER_PASSWORD=mypass
10. You should be able to run these test in both Mac and Linux machines and you don't need to specify your OS system since the framework takes care of that part programmatically.

## How to run tests
1. You can run tests using your IDE
2. You can also run tests from the command line
   - mvn clean test -Dtest=weave.api.AuthenticationTest (individual test)
   - mvn clean test -Dtest=weave.api.**  (runs all tests in api package)
   - mvn clean test (runs both UI and API tests)
3. Running tests in `parallel - multi-threaded`
   - If you run tests from the command line, tests will be run multi-threaded.    
4. Default thread number is 5 and you can change it in the pom.xml file
5. You can also override the number of thread from the command line by passing
   -  -DnumberOfThread=2
6. You can run UI tests headlessly. You just need to select the BROWSER_TYPE in the .env file or pass this from the command line.
7. If you do not specify the browser type the default is chrome.
8. Currently, you can run tests on chrome, safari and firefox. But I did not have a chance to run all tests on firefox and Safari (because I have limited time). Sometimes different browsers require special treatment.
9. If a UI test fails, the framework gets the screenshot of the failing page and name the file with the testclass.testname in the resources/screenshots folder.

## System Design
1. In the src/main folder
   - All Browser related files are inside the browsers package.
   - All Page Objects for UI tests are inside the pages package
   - All common classes are inside the common package in the src.
2. In the src/test folder
   - All UI tests are inside the ui package
   - All API tests are inside the api package
3. UI Browser drivers
   - All browser drivers are in the test/resources/drivers
4. Failed UI tests' screenshots are in the resources/screenshots folder. Files in this folder is ignored by git because we don't want to push them to the remote repo.

## Design Decisions
1. I have used the Page Object Model to reduce the maintenance cost. It makes tests more readable as well.
2. Since the assignment included scenarios, I decided to use chaining (see AddUserTest.java). This makes tests more readable and you don't need to create all page objects inside the tests. Chaining will return you the next required page object. 
3. There are some other tests I would like to add but I did not have time to add all. But I would love to talk about them when we meet.
4. There are some other tests we need to perform but it makes more sense to write them as unit tests because as we go up to integration and UI tests, tests run slower and they get more expensive.
5. I have used parameterized tests to reduce the line of code. This means it requires less maintenance and easier to read code.
6. In order to reduce duplication and maintenance costs CallManager class is created. All Http calls are handled in this class. Therefore, junior QEs do not need to worry about these calls. They just need to know what kind of calls they need to make (get, post etc) and they need to pass the path and the body to these methods.
7. BrowserUtil class is created for commonly used UI methods. This class is crutial because this class gives us an advantage to change the waitTime and the driver on the fly. Besides, these changes will NOT affect other threads since ALL classes are thread-safe.
8. Using the .env file or passing environment parameters from the command line, we can run tests in different configuration without making any code changes.
9. Currently, Login test passes on all 3 browsers but AddUserTest passes only on chrome. I need more time to fix the issues. One issue is that both Firefox and Safari open a small window when the browser is opened. This can be fixed by setting dimentions or making it full screen but as I mentioned because of time limitation, I did not have a chance to work on it.
10. EnvManager class is taking care of getting environment variables we pass from the command line or from .env file. using this class, we can access those variables from anywhere anytime.
11. .env file provides convenience for us so that we don't have to run tests from the command line by typing all those variables we want to pass. That being said, if you do not want, you don't have to use this file at all.
12. I used testNG because it has BeforeSuite and AfterSuite annotations we can use.
13. TestNG and surefire also provides us a reporting after running tests. The file name and its location is below
   - target/surefire-reports/index.html
14. I have started SauceLabs implementation but did not have enough time. SauceLabs implementation is not that difficult since I have done it in my current and the previous companies.
15. Both UI and API tests have their own Base class. These classes have some functionalities other test classes may want to use such as getting screenshots after failing UI tests etc.

## Issues
1. I would like to test if I could access others information using my localizationId. I don't have another one to test it.
2. It looks like whenever I create a new token, the system does not invalidate the old one. This might be a problem.
3. Some UI pages don't have good identifiers. This makes it difficult or fragile the locators.
4. After adding or deleting users, we get short notifications on the top right corner. They block the logout section and the user has to wait or close them. There are better ways to implement notifications.
5. Email validation accepts these emails as valid ones. (Add user)
   - %@a.ab, -@a.com, *@a.ab, &@a.ab, +@a.ab, =@a.ab, '@a.ab, "@a.ab, {@a.ab, }@a.ab, #@a.ab, $@a.ab, %@a.ab, ^@a.ab, /@a.ab, ?@a.ab, |@a.ab
   - %@a.commmmmmmmmmmmmmmmmmm
6. Fax History has 2 Date Pickers. I assume these for getting the report between those days. You can set the end date before the start date and there is no warning for the user.
7. Call Records has the same issue.
8. Call Blocking
   - I entered 000000 and 999999 as phone numbers and there were no validations.
9. Ben and I talked about uploading csv files for customers. After the upload, we could not find where they went.
10. During login if credentials are incorrect, we just display the error message for a short period of time and the app is clearing up entered credentials. If the user does not pay attention, it is easy to miss the error message. This will create confusion.
11. I did not add UI tests for fail login cases because these tests can be written with unit tests (js unit tests). Testing fail cases with selenium is a lot more expensive.
12. When you click on "Forgot your password", I can click on the "Reset" button even without entering anything in the email field.
13. It looks like the email validation is very weak when we try to reset the password. I entered a@a amd 1@a and they were accepted. Again these validations can be done in unit level.
14. Login page, when you come to this page, the "login" button is still clickable without entering anything as credentials. The user can click on it and nothing happens and then it becomes disabled.
15. On the login page, it looks like there is not much validation. I entered just a space for both username and password and they were accepted and login button got activated.
16. Login page does not validate entered emails. Again this validation can be done with unit tests. (you can enter just numbers, period, basically anything)
17. "Unable to login" is not a descriptive error message. It can be more specific.
18. 
