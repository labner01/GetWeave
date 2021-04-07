package weave.api;

import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Collection;
import static org.hamcrest.Matchers.*;

public class UsersTest extends BaseApi {
   private static final String USER_PATH = "/users/";
   private static final String USER_ID = "b23c8d64-458c-4e4f-b838-c1cce3ffbae7";
   private static final String LOCATION_ID = "4dc91013-e08c-4653-93cd-a515cc6720c8";

   @Test
   public void getUserHappyPath() {
      System.out.println("\tUsersTest - ThreadId: " + Thread.currentThread().getId());
      callManager.getRequest(USER_PATH + USER_ID, autoToken, LOCATION_ID)
         .then()
         .statusCode(HttpStatus.SC_OK)
         .rootPath("data")
         .body("UserID", is(USER_ID))
         .body("Username", is(envManager.getUserEmail()))
         .body("FirstName", is("Leo"))
         .body("LastName", is("Abner"))
         .body("Type", isA(String.class))
         .body("Status", isA(String.class))
         .body("MobileNumber", isA(String.class))
         .body("MobileNumberVerifiedAt", is(nullValue()))
         .body("Roles", isA(Collection.class))
         .body("Roles.size()", greaterThan(0))
         .rootPath("data.Roles[0]")
         .body("ID", isA(Integer.class))
         .body("Name", isA(String.class))
         .body("Type", isA(String.class))
         .body("Permissions", isA(Collection.class))
         .body("Permissions.size()", greaterThanOrEqualTo(1))
      ;
   }

   @DataProvider
   public Object[][] invalidParams() {
      return new Object[][] {
         new Object[] { null },
         new Object[] { "" },
         new Object[] { "Invalid" },
      };
   }

   @Test(dataProvider="invalidParams")
   public void getUserInvalidAuthToken(String token) {
      System.out.println("\tUsersTest - ThreadId: " + Thread.currentThread().getId());
      callManager.getRequest(USER_PATH + USER_ID, token , LOCATION_ID)
         .then()
         .statusCode(HttpStatus.SC_UNAUTHORIZED);
   }

   @Test(dataProvider="invalidParams")
   public void getUserInvalidLocationId(String locationId) {
      System.out.println("\tUsersTest - ThreadId: " + Thread.currentThread().getId());
      callManager.getRequest(USER_PATH + USER_ID, autoToken, locationId)
         .then()
         .statusCode(HttpStatus.SC_FORBIDDEN);
   }

   @Test
   public void getUserUserId() {
      System.out.println("\tUsersTest - ThreadId: " + Thread.currentThread().getId());
      callManager.getRequest(USER_PATH + "invalidUserId", autoToken, LOCATION_ID)
         .then()
         .statusCode(HttpStatus.SC_BAD_REQUEST);
   }
}
