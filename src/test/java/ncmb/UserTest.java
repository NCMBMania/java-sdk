package ncmb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class UserTest {
  static NCMB ncmb;
  
  @BeforeAll
  public static void setup() {
    String applicationKey = "70dfced7542e494861ec39ba7442115dfa9806312a444831ed9a7faac5087934";
    String clientKey = "4d0dea61349c1ae47106a06c80f11dfffe705e606709fa9563ac5cf80cf2edff";
    ncmb = new NCMB(applicationKey, clientKey);
  }
  
  @Test
  public void testRegister() {
    try {
      NCMBUser user = ncmb.NCMBUser();
      user.put("userName", "test_user");
      user.put("password", "password");
      if (user.signUp()) {
        System.out.println(user.getString("objectId"));
      } else {
        System.err.println("Login failed.");
      }
    } catch (NCMBException e) {
      System.err.println(e.getMessage());
    }
  }
}
