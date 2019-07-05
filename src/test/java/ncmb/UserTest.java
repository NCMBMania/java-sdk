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
    String applicationKey = "b347bafb25296ae896e06684068574f4332e2526626f78b475e799ca5882901e";
    String clientKey = "4895215f6469f325e6278afdb8d0178ddb88659964fd2b0e73ed4db4417bd462";
    ncmb = new NCMB(applicationKey, clientKey);
  }
  
  @Test
  public void testRegister() {
    try {
      NCMBUser user = ncmb.NCMBUser();
      user.put("username", "test_user");
      user.put("password", "password");
      if (user.signUp()) {
        assertNotNull(user.getString("objectId"));
      }
    } catch (NCMBException e) {
      System.err.println(e.getMessage());
    }
  }
}
