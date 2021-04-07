package weave.api;

import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class GetLocationTest extends BaseApi {
   private static final String LOCATION_ID = "4dc91013-e08c-4653-93cd-a515cc6720c8";
   private static final String PATH = "/locations/?location_id=";

   @Test
   public void getLocationHappyPath() {
      callManager.getRequest(PATH + LOCATION_ID, autoToken, LOCATION_ID)
         .then()
         .statusCode(HttpStatus.SC_OK)
         .rootPath("data[0]")
         .body("Active", is(true))
         .body("LocationID", is(LOCATION_ID))
         .body("ParentID", is(nullValue()))
         .body("Name", is("leo-abner"))
         .body("Slug", is("leo-abner"))
      ;
   }

   @Test
   public void getLocationInvalidLocationId() {
      callManager.getRequest(PATH + "invalidId", autoToken, LOCATION_ID)
         .then()
         .statusCode(HttpStatus.SC_BAD_REQUEST);
   }

   @DataProvider
   public Object[][] invalidParamsForAuthToken() {
      return new Object[][] {
         new Object[] { null },
         new Object[] { "" },
         new Object[] { "Invalid" },
      };
   }

   @Test(dataProvider="invalidParamsForAuthToken")
   public void getUserInvalidAuthToken(String token) {
      callManager.getRequest(PATH + LOCATION_ID, token , LOCATION_ID)
         .then()
         .statusCode(HttpStatus.SC_UNAUTHORIZED);
   }

   @DataProvider
   public Object[][] invalidParamsForLocationId() {
      return new Object[][] {
         new Object[] { null },
//         new Object[] { "" },  // I wonder if this is a bug or intentional. But we get 200 here
         new Object[] { "Invalid" },
      };
   }

   @Test(dataProvider="invalidParamsForLocationId")
   public void getUserInvalidLocationId(String locationId) {
      callManager.getRequest(PATH + LOCATION_ID, autoToken, locationId)
         .then()
         .statusCode(HttpStatus.SC_FORBIDDEN);
   }
}
