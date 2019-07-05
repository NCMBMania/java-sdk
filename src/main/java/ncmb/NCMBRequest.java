package ncmb;

import org.json.JSONObject;
import java.sql.Timestamp;
import java.net.URLEncoder;

public class NCMBRequest {
  public static JSONObject get(String path, String applicationKey, String clientKey, JSONObject queries) throws NCMBException{
    String method = "GET";
    Timestamp time = new Timestamp(System.currentTimeMillis());
    Signature s = new Signature();
    String signature = s.sign(method, path, applicationKey, time, queries, clientKey);
    try {
      String urlString = "https://" + Signature.FQDN + path;
      if (!queries.isNull("where")) {
        JSONObject where = queries.getJSONObject("where");
        urlString = urlString + "?where=" + URLEncoder.encode(where.toString(), "UTF-8");
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
}