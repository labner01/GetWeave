package weave.api;

import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import weave.ui.common.CallManager;
import weave.ui.common.EnvManager;
import java.util.HashMap;
import java.util.Map;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

public class AuthenticationTest {
   private EnvManager envManager = new EnvManager();
   private CallManager callManager = new CallManager();

   @Test
   public void happyPath() {
      System.out.println("\tAuthenticationTest - ThreadId: " + Thread.currentThread().getId());
      assertNotNull(getToken());
   }

   @Test
   public void getTokenMultipleTimes_TokensAreDifferent() {
      System.out.println("\tAuthenticationTest - ThreadId: " + Thread.currentThread().getId());
      assertNotEquals(getToken(), getToken());
   }

   private String getToken() {
      return callManager.getAuthToken(getBody(envManager.getUserEmail(), envManager.getUserPassword()))
         .then()
         .statusCode(HttpStatus.SC_OK)
         .extract().path("data");
   }

   @Test(dataProvider = "authParameters")
   public void invalidCases(String username, String password) {
      System.out.println("\tAuthenticationTest - ThreadId: " + Thread.currentThread().getId());
      callManager.getAuthToken(getBody(username, password))
         .then()
         .statusCode(HttpStatus.SC_UNAUTHORIZED);
   }

   private Map<String, String> getBody(String username, String password) {
      return new HashMap<String, String>() {{
         put("username", username);
         put("password", password);
      }};
   }

   @DataProvider
   private Object[][] authParameters() {
      return new Object[][] {
         {null, null},
         {"", ""},
         {"'", "'"},
         {"\"", "\""},
         {".", "."},
         {" ", " "},
         {"invalid", "invalid"},
         {" " + envManager.getUserEmail(), " "  + envManager.getUserPassword()},
         {envManager.getUserEmail() + " ", envManager.getUserPassword() + " "},
         {null, envManager.getUserPassword()},
         {"", envManager.getUserPassword()},
         {" ", envManager.getUserPassword()},
         {"invalid", envManager.getUserPassword()},
         {envManager.getUserEmail(), null},
         {envManager.getUserEmail(), ""},
         {envManager.getUserEmail(), " "},
         {envManager.getUserEmail(), "invalid"},

         // SQL injection test cases
         {envManager.getUserEmail(), envManager.getUserPassword() + "' or 1=1;"},
         {envManager.getUserEmail(), envManager.getUserPassword() + "\" or 1=1;"},

         {envManager.getUserEmail() + "' or 1=1;", envManager.getUserPassword()},
         {envManager.getUserEmail() + "\" or 1=1;", envManager.getUserPassword()},

         {envManager.getUserEmail() + "' or 1=1;", envManager.getUserPassword() + "' or 1=1;"},
         {envManager.getUserEmail() + "\" or 1=1;", envManager.getUserPassword() + "\" or 1=1;"},
      };
   }
}
