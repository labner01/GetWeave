package weave.ui.common;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.lang3.StringUtils;

public class EnvManager {
   private static final String DEFAULT_BROWSER = "chrome";
   private static final long DEFAULT_WAIT_TIME = 10;
   private String browserType;
   private long defaultWaitTime;
   private String whereToRunTests;
   private String userEmail;
   private String userPassword;
   private Dotenv dotenv;

   public EnvManager() {
      // We will not push .env file to the repo therefore, jenkins etc will not load
      // env variables from the .env file.
      try {
         dotenv = Dotenv.load();
      } catch (Exception ex) {
         dotenv = null;
      }
   }

   public String getBrowserType() {
      if (StringUtils.isBlank(browserType)) {
         browserType = getEnvString("BROWSER_TYPE");
      }
      if (StringUtils.isBlank(browserType)) {
         browserType = DEFAULT_BROWSER;
      }
      return browserType;
   }

   public long getDefaultWaitTime() {
      if (defaultWaitTime == 0) {
         String value = getEnvString("DEFAULT_WAIT_TIME");
         defaultWaitTime =  StringUtils.isBlank(value) ? DEFAULT_WAIT_TIME : Long.parseLong(value);
      }
      return defaultWaitTime;
   }

   public String getWhereToRunTests() {
      if (StringUtils.isBlank(whereToRunTests)) {
         whereToRunTests = getEnvString("WHERE_TO_RUN_TESTS");
      }
      return whereToRunTests;
   }

   public String getUserEmail() {
      if (StringUtils.isBlank(userEmail)) {
         userEmail = getEnvString("USER_EMAIL");
      }
      return userEmail;
   }

   public String getUserPassword() {
      if (StringUtils.isBlank(userPassword)) {
         userPassword = getEnvString("USER_PASSWORD");
      }
      return userPassword;
   }

   // We first try to get values from the property and then we try .env file
   private String getEnvString(String envName) {
      String envValue = System.getProperty(envName);
      if (StringUtils.isBlank(envValue) && dotenv != null) {
         envValue = dotenv.get(envName);
      }

      if (StringUtils.isBlank(envValue)) {
         System.out.println(String.format("%s is NULL or EMPTY.", envName));
      }
      return envValue;
   }
}
