package weave.ui.pages;

import org.openqa.selenium.By;
import weave.ui.common.BrowserUtil;
import weave.ui.common.Constants;

public class Login {
   public static final String PATH = "/admin/login";
   private By inputEmail = By.name("username");
   private By inputPassword = By.name("password");
   private By buttonLogin = By.tagName("button");
   private BrowserUtil browserUtil;

   public Login(BrowserUtil browserUtil) {
      this.browserUtil = browserUtil;
   }

   public Dashboard login(String username, String password) {
      browserUtil.navigate(Constants.BASE_URI + PATH);
      browserUtil.waitUntilUrlIncludes(PATH);
      browserUtil.sendKeysToElement(inputEmail, username);
      browserUtil.sendKeysToElement(inputPassword, password);
      browserUtil.findElement(buttonLogin).click();
      browserUtil.waitUntilUrlIncludes(Dashboard.PATH);
      return new Dashboard(browserUtil);
   }
}
