package weave.ui.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class FirefoxHeadless implements Browser {

   public final WebDriver prepareLocalDriver() {
      System.setProperty("webdriver.gecko.driver", getDriverPath());
      FirefoxOptions options = new FirefoxOptions();
      options.addArguments("-headless");
      return new FirefoxDriver(options);
   }
}
