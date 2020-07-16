package ncmb;

import org.json.JSONObject;
import java.sql.Timestamp;
import java.net.URLEncoder;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

public class NCMBRequest {
  public static JSONObject get(String path, String applicationKey, String clientKey, JSONObject queries) throws NCMBException{
    String method = "GET";
    Timestamp time = new Timestamp(System.currentTimeMillis());
    Signature s = new Signature();
    String signature = s.sign(method, path, applicationKey, time, queries, clientKey);
    try {
      String urlString = "https://" + Signature.FQDN + path;
      
      Iterator<String> iterator = queries.keys();
      Map<String,String> params = new TreeMap<>();
      while (iterator.hasNext()) {
        String key = iterator.next();
        Object obj = queries.get(key);
        if (obj instanceof Integer) {
          params.put(key, URLEncoder.encode(obj.toString(), "UTF-8"));
        } else {
          params.put(key, URLEncoder.encode(queries.getJSONObject(key).toString(), "UTF-8"));
        }
      }
      ArrayList<String> aryParams = new ArrayList<>();
      for (Map.Entry<String, String> entry : params.entrySet()) {
        aryParams.add(entry.getKey() + "=" + entry.getValue());
      }
      if (aryParams.size() > 0) {
        urlString = urlString + "?" + String.join("&", aryParams);
      }
      HttpRequest r = new HttpRequest();
      String result = r.get(urlString, applicationKey, time, signature);
      return new JSONObject(result);
    } catch (Exception e) {
      throw new NCMBException("GET エラー");
    }
  }


  public static JSONObject post(String path, String applicationKey, String clientKey, JSONObject data) throws NCMBException{
    String method = "POST";
    Timestamp time = new Timestamp(System.currentTimeMillis());
    Signature s = new Signature();
    String signature = s.sign(method, path, applicationKey, time, new JSONObject(), clientKey);
    String urlString = "https://" + Signature.FQDN + path;
    try {
      HttpRequest r = new HttpRequest();
      String result = r.post(urlString, applicationKey, time, signature, data);
      System.out.println(result);
      return new JSONObject(result);
    } catch (Exception e) {
      throw new NCMBException("POST エラー");
    }
  }
  
  public static JSONObject post(String path, String applicationKey, String clientKey, String fileName, byte[] data) throws NCMBException{
    String method = "POST";
    Timestamp time = new Timestamp(System.currentTimeMillis());
    Signature s = new Signature();
    String signature = s.sign(method, path, applicationKey, time, new JSONObject(), clientKey);
    String urlString = "https://" + Signature.FQDN + path;
    try {
      HttpRequest r = new HttpRequest();
      String result = r.post(urlString, applicationKey, time, signature, fileName, data);
      return new JSONObject(result);
    } catch (Exception e) {
      throw new NCMBException(e.getMessage());
    }
  }

  public static Boolean delete(String path, String applicationKey, String clientKey, String objectId) throws NCMBException{
    String method = "DELETE";
    Timestamp time = new Timestamp(System.currentTimeMillis());
    Signature s = new Signature();
    String signature = s.sign(method, path, applicationKey, time, new JSONObject(), clientKey);
    String urlString = "https://" + Signature.FQDN + path;
    try {
      HttpRequest r = new HttpRequest();
      String result = r.delete(urlString, applicationKey, time, signature);
      return result.equals("");
    } catch (Exception e) {
      throw new NCMBException(e.getMessage());
    }
  }
}