package weave.ui.pages;

import org.openqa.selenium.By;
import weave.ui.common.BrowserUtil;
import weave.ui.common.Utils;
import static org.testng.Assert.*;

public class Users {
   public static final String PATH = "/employees/employee-list";
   private String linkFormat = "//span[contains(text(),'%s')]";
   private By buttonAddUser = By.xpath("//button[contains(text(),'Add User')]");
   private By linkTeam = By.xpath(String.format(linkFormat, "Team"));
   private By linkInvitations = By.xpath(String.format(linkFormat, "Invitations"));
   private By invitatedPerson = By.className("rt-tr-group");
   private By iconDelete = By.className("css-1hzyjnh-SvgIcon");
   private By buttonDelete = By.xpath("//button[contains(text(),'Delete')]");
   private BrowserUtil browserUtil;

   public Users(BrowserUtil browserUtil) {
      this.browserUtil = browserUtil;
   }

   public InviteUser clickAddUser() {
      browserUtil.findElement(buttonAddUser).click();
      return new InviteUser(browserUtil);
   }

   public Users validateInvitedUsers(String email, String firstname, String lastname) {
      browserUtil.waitUntilUrlIncludes(PATH);
      browserUtil.findElement(linkInvitations).click();
      String userInfo = browserUtil.findElement(invitatedPerson).getText();
      assertTrue(userInfo.contains(email));
      assertTrue(userInfo.toLowerCase().contains(firstname.toLowerCase()));
      assertTrue(userInfo.toLowerCase().contains(lastname.toLowerCase()));
      return this;
   }

   public Dashboard deleteUser() {
      browserUtil.findElement(iconDelete).click();
      browserUtil.findElement(buttonDelete).click();
      new Utils().sleep(300); // Page needs to refresh after deleting the user.
      assertFalse(browserUtil.elementExists(iconDelete));
      return new Dashboard(browserUtil);
   }
}
