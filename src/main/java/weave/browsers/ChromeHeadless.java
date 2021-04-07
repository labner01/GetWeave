package weave.browsers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class ChromeHeadless implements Browser {

   public final WebDriver prepareLocalDriver() {
      System.setProperty("webdriver.chrome.driver", getDriverPath());
      ChromeOptions options = new ChromeOptions();
      options.addArguments("headless");
      options.setHeadless(true);
      options.addArguments("window-size=1920x1080");
      options.setPageLoadStrategy(PageLoadStrategy.NONE);
      return new ChromeDriver(options);
   }
}
