package weave.ui.common;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CallManager {

   public Response getAuthToken(Object body) {
      return given()
         .contentType(ContentType.JSON)
         .accept(ContentType.JSON)
         .body(body)
         .when()
         .post(Constants.API_LOGIN)
         .then()
         .log().all()   // We want to see what the response is to make debugging easier.
         .extract().response();
   }

   public Response getRequest(String path, String authToken, String locationId) {
      return given()
         .contentType(ContentType.JSON)
         .accept(ContentType.JSON)
         .header("Authorization", "Bearer " + authToken)
         .header("location-id", "" + locationId)
         .when()
         .log().all()   // We want to see how we are making the call
         .get(Constants.API_BASE_URI + path)
         .then()
         .log().all()   // We want to see what the response is to make debugging easier.
         .extract().response();
   }

   public Response postRequest(Object body, String path, String authToken, String locationId) {
      return given()
         .contentType(ContentType.JSON)
         .accept(ContentType.JSON)
         .header("Authorization", "Bearer " + authToken)
         .header("location-id", "" + locationId)
         .body(body)
         .when()
         .log().all()   // We want to see how we are making the call
         .post(Constants.API_BASE_URI + path)
         .then()
         .log().all()   // We want to see what the response is to make debugging easier.
         .extract().response();
   }
}
