package ncmb;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class Test {
   public static void main(String[] args){
     String applicationKey = "70dfced7542e494861ec39ba7442115dfa9806312a444831ed9a7faac5087934";
     String clientKey = "4d0dea61349c1ae47106a06c80f11dfffe705e606709fa9563ac5cf80cf2edff";
     NCMB ncmb = new NCMB(applicationKey, clientKey);
     try {
       NCMBQuery Hello = ncmb.NCMBQuery("Hello");
       Hello.whereGreaterThanOrEqualTo("int", 400);
       Hello.whereLessThan("int", 800);
       ArrayList<NCMBObject> ary = Hello.find();
       ary.forEach((o) -> {
         try {
           System.out.println(o.getInt("int"));
         } catch (NCMBException e) {
           System.out.println(e);
         }
       });
     } catch (NCMBException e) {
       System.err.println(e.getMessage());
     }
   }
   
   public static void save(NCMB ncmb) {
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