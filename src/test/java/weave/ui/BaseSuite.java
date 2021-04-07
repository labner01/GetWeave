package weave.ui;

import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import weave.ui.common.BrowserUtil;
import java.io.File;
import java.io.IOException;
import static java.lang.String.format;

public abstract class BaseSuite {
   protected BrowserUtil browserUtil;

   @BeforeClass
   public void beforeClass() {
      if (browserUtil == null) {
         browserUtil = new BrowserUtil();
      } else if (browserUtil.getWebDriver() == null) {
         browserUtil.setNewWebDriver();
      }
      System.out.println("\tBeforeClass - ThreadId: " + Thread.currentThread().getId());
   }

   @AfterClass
   public void tearDownSuite() {
      System.out.println("\ttearDownSuite - ThreadId: " + Thread.currentThread().getId());
      quitDriver();
   }

   private void quitDriver() {
      if (browserUtil != null) {
         browserUtil.quitDriver();
      }
   }

   @AfterMethod
   public void recordFailure(ITestResult result) {
      if (ITestResult.FAILURE == result.getStatus()) {
         File screenShot = ((TakesScreenshot) browserUtil.getWebDriver()).getScreenshotAs(OutputType.FILE);
         String fileName = format("resources/screenshots/%s.%s.png",
            StringUtils.substringAfterLast(result.getTestClass().getName(), "."),
            result.getName()
         );
         try {
            Files.move(screenShot, new File(fileName));
            System.out.println("\t*** " + fileName + " has been created. ***");
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
