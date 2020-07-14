package ncmb;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class PushTest {
  static NCMB ncmb;
  
  @BeforeAll
  public static void setup() {
    String applicationKey = "70dfced7542e494861ec39ba7442115dfa9806312a444831ed9a7faac5087934";
    String clientKey = "4d0dea61349c1ae47106a06c80f11dfffe705e606709fa9563ac5cf80cf2edff";
    ncmb = new NCMB(applicationKey, clientKey);
  }
  
  @Test
  public void testCreate() {
    try {
      NCMBPush push = ncmb.NCMBPush();
      push.put("immediateDeliveryFlag", true);
      push.put("message", "Hello, World!");
      JSONArray target = new JSONArray();
      target.put("ios");
      target.put("android");
      push.put("target", target);
      push.save();
      assertNotNull(push.getString("objectId"));
      System.out.println(push.getString("objectId"));
      assertTrue(push.destroy());
    } catch (NCMBException e) {
      System.err.println(e.getMessage());
    }
  }
}
