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
    String applicationKey = "b347bafb25296ae896e06684068574f4332e2526626f78b475e799ca5882901e";
    String clientKey = "4895215f6469f325e6278afdb8d0178ddb88659964fd2b0e73ed4db4417bd462";
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
      System.err.println(e.getMessage());
    }
  }
  
  @Test
  public void testFetch() {
    try {
      NCMBObject Hello = ncmb.NCMBObject("Hello");
      Hello.setObjectId("hZq3u8EeqULE4CBN");
      if (Hello.fetch()) {
        System.err.println(Hello.getString("message"));
      }
      return;
    } catch (NCMBException e) {
      System.err.println(e.getMessage());
    }
  }
  
  @Test
  public void testSave() {
    try {
      NCMBObject hello = ncmb.NCMBObject("Hello");
      hello.put("message", "Hello World");
      JSONArray array = new JSONArray();
      array.put("apple");
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
      System.out.println(hello.getString("objectId"));
    } catch (JSONException e) {
      System.err.println(e.getMessage());
    } catch (NCMBException e) {
      System.err.println(e.getMessage());
    }
  }
}
