package ncmb;
import org.json.JSONException;

public class Test {
   public static void main(String[] args){
     String applicationKey = "b347bafb25296ae896e06684068574f4332e2526626f78b475e799ca5882901e";
     String clientKey = "4895215f6469f325e6278afdb8d0178ddb88659964fd2b0e73ed4db4417bd462";
     NCMB ncmb = new NCMB(applicationKey, clientKey);
     try {
       NCMBObject hello = ncmb.NCMBObject("Hello");
       hello.put("message", "Hello World");
       hello.save();
       System.out.println(hello.getString("objectId"));
     } catch (NCMBException e) {
       System.err.println(e.getMessage());
     }
   }
}
