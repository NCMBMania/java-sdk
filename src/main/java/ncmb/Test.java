package ncmb;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class Test {
   public static void main(String[] args){
     String applicationKey = "b347bafb25296ae896e06684068574f4332e2526626f78b475e799ca5882901e";
     String clientKey = "4895215f6469f325e6278afdb8d0178ddb88659964fd2b0e73ed4db4417bd462";
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