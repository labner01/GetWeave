package weave.pages;

import org.openqa.selenium.By;
import weave.common.BrowserUtil;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Dashboard {
   public static final String PATH = "/dashboard";
   private By name = By.cssSelector("button span");
   private By linkLogOut = By.xpath("//ul[@role='menu']//li[@role='menuitem']");
   private String linkFormat = "//span[contains(text(),'%s')]";
   private By linkDataSources = By.xpath(String.format(linkFormat, "Data Sources"));
   private By linkAccount = By.xpath(String.format(linkFormat, "Account"));
   private By linkMessages = By.xpath(String.format(linkFormat, "Messages"));
   private By linkCustomer = By.xpath(String.format(linkFormat, "Customers"));
   private By linkPhone = By.xpath(String.format(linkFormat, "Phone"));
   private By linkPayments = By.xpath(String.format(linkFormat, "Payments"));
   private By linkMarketing = By.xpath(String.format(linkFormat, "Marketing"));
   private By linkFax = By.xpath(String.format(linkFormat, "Fax"));
   private By linkWeaveHelp = By.xpath(String.format(linkFormat, "Weave Help"));
   private By linkUsers = By.xpath(String.format(linkFormat, "Users"));
   private By linkCvsUpload = By.xpath(String.format(linkFormat, "CSV Upload"));

   private BrowserUtil browserUtil;

   public Dashboard(BrowserUtil browserUtil) {
      this.browserUtil = browserUtil;
   }

   public void validateFields(String username) {
      browserUtil.waitUntilUrlIncludes(PATH);
      String text = browserUtil.getElementText(name);
      assertEquals(text, username);

      // We could perform this validation with a for loop as well but
      // some engineers think that it makes it difficult to debug.
      assertTrue(browserUtil.isElementPresent(linkDataSources));
      assertTrue(browserUtil.isElementPresent(linkAccount));
      assertTrue(browserUtil.isElementPresent(linkMessages));
      assertTrue(browserUtil.isElementPresent(linkCustomer));
      assertTrue(browserUtil.isElementPresent(linkPhone));
      assertTrue(browserUtil.isElementPresent(linkPayments));
      assertTrue(browserUtil.isElementPresent(linkMarketing));
      assertTrue(browserUtil.isElementPresent(linkFax));
      assertTrue(browserUtil.isElementPresent(linkWeaveHelp));
   }

   public Users clickUsers() {
      browserUtil.waitUntilUrlIncludes(PATH);
      browserUtil.findElement(linkAccount).click();
      browserUtil.findElement(linkUsers).click();
      return new Users(browserUtil);
   }
}
