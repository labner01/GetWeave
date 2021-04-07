package weave.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public final class Safari implements Browser {

   public final WebDriver prepareLocalDriver() {
      System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
      SafariOptions options = new SafariOptions();
      options.setCapability("autoAcceptAlerts", true);
      return new SafariDriver(options);
   }
}
