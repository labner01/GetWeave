package weave.ui.browsers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public final class Chrome implements Browser {

   public final WebDriver prepareLocalDriver() {
      System.setProperty("webdriver.chrome.driver", getDriverPath());
      ChromeOptions options = new ChromeOptions();
      Map<String, Object> prefs = new HashMap<>();
      options.addArguments("--disable-plugins", "--disable-extensions", "--disable-popup-blocking");
      options.setCapability("applicationCacheEnabled", false);
      options.setCapability("credentials_enable_service", false);
      options.setPageLoadStrategy(PageLoadStrategy.NONE);
      options.setExperimentalOption("prefs", prefs);
      return new ChromeDriver(options);
   }
}
