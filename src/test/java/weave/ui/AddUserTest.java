package weave.ui;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import weave.ui.common.Constants;
import weave.ui.common.EnvManager;
import weave.ui.common.Utils;
import weave.ui.pages.Dashboard;
import weave.ui.pages.Login;

public class AddUserTest extends BaseSuite {
   private Utils utils = new Utils();
   private EnvManager envManager = new EnvManager();
   private Dashboard dashboard;

   @BeforeClass
   public void setup() {
      dashboard = new Login(browserUtil).login(envManager.getUserEmail(), envManager.getUserPassword());
   }

   @Test
   public void addNewUserHappyPath() {
      String timeStamp = utils.getCurrentTimeStamp();
      String testUserEmail = "t_" + timeStamp + "@example.com";
      String testUserFirstName = "fn_" + timeStamp;
      String testUserLastName = "ln_" + timeStamp;
      browserUtil.navigate(Constants.DASHBOARD_URL);
      dashboard
         .clickUsers()
         .clickAddUser()
         .addNewUser(testUserEmail, testUserFirstName, testUserLastName, false)
         .validateInvitedUsers(testUserEmail, testUserFirstName, testUserLastName)
         .deleteUser()
      ;
   }

   @Test
   public void addExistingUserHappyPath() {
      String testUserEmail = "t1@example.com";
      String testUserFirstName = "t1First";
      String testUserLastName = "t1Last";
      dashboard
         .clickUsers()
         .clickAddUser()
         .addNewUser(testUserEmail, testUserFirstName, testUserLastName, true)
         .validateInvitedUsers(testUserEmail, testUserFirstName, testUserLastName)
         .deleteUser()
      ;
   }
}
