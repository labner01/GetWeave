package weave.api;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import weave.common.CallManager;
import weave.common.EnvManager;
import java.util.HashMap;
import java.util.Map;

public class BaseApi {
   protected static final String SCHEMA_PATH = "";
   protected CallManager callManager = new CallManager();
   protected EnvManager envManager = new EnvManager();
   protected String autoToken;

   @BeforeClass
   public void setup() {
      Map<String, String> body = new HashMap<String, String>() {{
         put("username", envManager.getUserEmail());
         put("password", envManager.getUserPassword());
      }};
      autoToken = callManager.getAuthToken(body).then().statusCode(HttpStatus.SC_OK).extract().path("data");
   }
}
