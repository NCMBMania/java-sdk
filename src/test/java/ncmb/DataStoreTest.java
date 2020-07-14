package ncmb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class DataStoreTest {
  static NCMB ncmb;
  
  @BeforeAll
  public static void setup() {
    String applicationKey = "70dfced7542e494861ec39ba7442115dfa9806312a444831ed9a7faac5087934";
    String clientKey = "4d0dea61349c1ae47106a06c80f11dfffe705e606709fa9563ac5cf80cf2edff";
    ncmb = new NCMB(applicationKey, clientKey);
  }
  
  @Test
  public void testFind() {
    try {
      NCMBQuery Query = ncmb.NCMBQuery("Hello");
      Query.whereEqualTo("int", 1000);
      Query.limit(5);
      ArrayList<NCMBObject> ary = Query.find();
      assertEquals(ary.get(0).getInt("int"), 1000);
      assertEquals(ary.size(), 5);
      Query.limit(6);
      ary = Query.find();
      assertEquals(ary.size(), 6);
      return;
    } catch (NCMBException e) {
      System.err.println("testFind failed.");
      System.err.println(e.getMessage());
    }
  }
  
  @Test
  public void testSave() {
    try {
      NCMBObject hello = ncmb.NCMBObject("Hello");
      hello.put("message", "Hello World");
      JSONArray array = new JSONArray();
      array.put("日本語");
      array.put("orange");
      array.put("banana");
      hello.put("array", array);
      hello.put("boolean", false);
      hello.put("int", 1000);
      hello.put("long", 9223372036854775807L);
      JSONObject json = new JSONObject();
      json.put("obj", "Hello");
      json.put("name", "json");
      hello.put("json", json);
      hello.save();
      assertEquals(hello.getInt("int"), 1000);
      System.out.println(hello.getString("objectId"));
    } catch (JSONException e) {
      System.err.println(e.getMessage());
    } catch (NCMBException e) {
      System.err.println("Save failed.");
      System.err.println(e.getMessage());
    }
  }

  @Test
  public void testFetch() {
    try {
      NCMBObject Hello = ncmb.NCMBObject("Hello");
      Hello.setObjectId("RCdYT8svkoUea3pA");
      if (Hello.fetch()) {
        System.out.println(Hello.getString("message"));
      }
      return;
    } catch (NCMBException e) {
      System.err.println(e.getMessage());
    }
  }

  @Test
  public void testDelete() {
    try {
      NCMBObject hello = ncmb.NCMBObject("Hello");
      hello.put("message", "Hello World");
      hello.put("int", 1000);
      hello.save();
      assertEquals(hello.getInt("int"), 1000);
      System.out.println(hello.getString("objectId"));
      assertEquals(hello.destroy(), true);
    } catch (NCMBException e) {
      System.err.println("Delete failed.");
      System.err.println(e.getMessage());
    }
  }

}
