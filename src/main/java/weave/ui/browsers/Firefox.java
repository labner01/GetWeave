package weave.ui.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public final class Firefox implements Browser {

   public final WebDriver prepareLocalDriver() {
      System.setProperty("webdriver.gecko.driver", getDriverPath());
      FirefoxOptions ffOpts = new FirefoxOptions();
      FirefoxProfile ffProfile = new FirefoxProfile();
      ffProfile.setPreference("browser.autofocus", true);
      ffProfile.setPreference("browser.download.folderList", 2);
      ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv");
      ffOpts.setCapability(FirefoxDriver.PROFILE, ffProfile);
      ffOpts.setCapability("marionette", true);
      return new FirefoxDriver(ffOpts);
   }
}
