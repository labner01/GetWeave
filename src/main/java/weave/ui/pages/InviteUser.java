package weave.ui.pages;

import org.openqa.selenium.By;
import weave.ui.common.BrowserUtil;
import static org.testng.Assert.assertEquals;

public class InviteUser {
   public static final String PATH = "/employee-list/invite";
   private By inputEmail = By.cssSelector("#email");
   private By buttonNext = By.xpath("//button[contains(text(),'Next')]");
   private By inputFirstName = By.cssSelector("input[name='FirstName']");
   private By inputLastName = By.cssSelector("input[name='LastName']");
   private By dropDownJobTitles = By.cssSelector("input[name='JobTitles']");
   private By dropDownRoles = By.cssSelector("input[name='Roles']");
   private By buttonSubmit = By.xpath("//button[contains(text(),'Submit')]");
   private By tagMobileAccess = By.cssSelector("#field-d20aee-label");
   private BrowserUtil browserUtil;

   private By doctorPractitioner = By.xpath("//li[@data-value='Doctor/Practitioner']");
   private By officeManager = By.xpath("//li[@data-value='Office Manager']");
   private By paymentAdmin = By.xpath("//li[@data-value='Payment Admin']");
   private By advancedTeamMember = By.xpath("//li[@data-value='Advanced Team Member']");

   private By numberOfSelectedJobTitles = By.xpath("((//div[contains(@class, 'DropdownInput-MultiselectInput')])[1]/p)[2]");
   private By numberOfSelectedRoles = By.xpath("((//div[contains(@class, 'DropdownInput-MultiselectInput')])[2]/p)[2]");

   public InviteUser(BrowserUtil browserUtil) {
      this.browserUtil = browserUtil;
   }

   public Users addNewUser(String email, String firstname, String lastname, boolean existingUser) {
      browserUtil.waitUntilUrlIncludes(PATH);
      browserUtil.sendKeysToElement(inputEmail, email);
      browserUtil.findElement(buttonNext).click();

      if (existingUser) { // Existing User
         assertEquals(firstname, browserUtil.getAttributeText(inputFirstName));
         assertEquals(lastname, browserUtil.getAttributeText(inputLastName));
      } else { // New User
         browserUtil.sendKeysToElement(inputFirstName, firstname);
         browserUtil.sendKeysToElement(inputLastName, lastname);
      }

      // Select the Job Title
      browserUtil.findElement(dropDownJobTitles).click();
      browserUtil.findElement(doctorPractitioner).click();
      browserUtil.findElement(officeManager).click();
      browserUtil.findElement(dropDownJobTitles).click();

      // Select the Roles
      browserUtil.findElement(dropDownRoles).click();
      browserUtil.findElement(paymentAdmin).click();
      browserUtil.findElement(advancedTeamMember).click();
      browserUtil.findElement(dropDownRoles).click();

      // Validate +1
      assertEquals("+1 more", browserUtil.getElementText(numberOfSelectedJobTitles));
      assertEquals("+1 more", browserUtil.getElementText(numberOfSelectedRoles));

      browserUtil.findElement(buttonSubmit).click();
      return new Users(browserUtil);
   }
}
