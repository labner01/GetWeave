package weave;

import org.testng.annotations.Test;
import weave.common.EnvManager;
import weave.pages.Login;

public class LoginTest extends BaseSuite {

   @Test
   public void loginTest() {
      EnvManager envManager = new EnvManager();
      new Login(browserUtil)
         .login(envManager.getUserEmail(), envManager.getUserPassword())
         .validateFields("Leo Abner");
   }
}
