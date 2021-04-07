package weave.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BrowserUtil {
   private WebDriver webDriver;
   private WebDriverWait wait;
   private EnvManager envManager;

   public BrowserUtil() {
      envManager = new EnvManager();
      setNewWebDriver();
   }

   public BrowserUtil(WebDriver webDriver, long waitTime) {
      this.webDriver = webDriver;
      setNewWaitTime(waitTime);
   }

   public void setNewWebDriver() {
      this.webDriver = createWebDriver();
      setDefaultWaitTime();
      System.out.println("\tWebDriverUtil - ThreadId: " + Thread.currentThread().getId());
   }

   public void setWebDriver(WebDriver webDriver) {
      if (webDriver != null) {
         this.webDriver = webDriver;
         setDefaultWaitTime();
      }
   }

   public WebDriver getWebDriver() {
      return webDriver;
   }

   public void setDefaultWaitTime() {
      this.wait = new WebDriverWait(webDriver, envManager.getDefaultWaitTime());
   }

   public void setNewWaitTime(long waitTime) {
      this.wait = new WebDriverWait(webDriver, waitTime);
   }

   private WebDriver createWebDriver() {
      switch (envManager.getWhereToRunTests().toLowerCase()) {
         case "local":
            return DriverFactory.getDriver(envManager.getBrowserType());
         case "sauce":
            throw new IllegalArgumentException("SauceLabs drivers are not ready.");
         default:
            throw new IllegalArgumentException("Invalid WHERE_TO_RUN_TESTS value: " + envManager.getWhereToRunTests());
      }
   }

   public WebElement findElement(By locator) {
      return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
   }

   public List<WebElement> findAllElements(By locator) {
      return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
   }

   public void sendKeysToElement(By locator, String text) {
      waitUntilVisible(locator).sendKeys(text);
   }

   public WebElement waitUntilVisible(By locator) {
      return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
   }

   public void navigate(String url) {
      webDriver.navigate().to(url);
   }

   public String getElementText(By locator) {
      return findElement(locator).getText();
   }

   public String getAttribute(By locator, String attrName) {
      return findElement(locator).getAttribute(attrName);
   }

   public String getAttributeText(By locator) {
      return getAttribute(locator, "value");
   }

   public boolean elementExists(By locator) {
      List<WebElement> list = webDriver.findElements(locator);
      return list.size() > 0;
   }

   public void waitUntilUrlIncludes(String url) {
      wait.until(ExpectedConditions.urlContains(url));
   }

   public boolean isElementPresent(By locator) {
      try {
         findElement(locator);
         return true;
      } catch (Exception e) {
         return false;
      }
   }

   public void quitDriver() {
      if (webDriver != null) {
         webDriver.quit();
      }
      webDriver = null;
   }
}
