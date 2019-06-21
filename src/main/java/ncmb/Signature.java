package ncmb;

import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;
import org.json.JSONObject;
import java.util.Iterator;
import java.sql.Timestamp;
import org.json.JSONException;

public class Signature {
  private static final String SIGNATURE_METHOD_NAME = "SignatureMethod";
  private static final String SIGNATURE_METHOD_VALUE = "HmacSHA256";
  private static final String SIGNATURE_VERSION_NAME = "SignatureVersion";
  private static final String SIGNATURE_VERSION_VALUE = "2";
  public static final String NCMB_APPLICATION_KEY_NAME = "X-NCMB-Application-Key";
  public static final String NCMB_APPLICATION_TIMESTAMP_NAME = "X-NCMB-Timestamp";
  public static final String FQDN = "mbaas.api.nifcloud.com";

  public static String sign(String method, String path, String applicationKey, Timestamp time, JSONObject queries, String clientKey) throws NCMBException  {
    String result = null;
    try {
      String str = "";
      String[] ary = new String[4];
      ary[0] = method;
      ary[1] = FQDN;
      ary[2] = path;
      ary[3] = queryString(applicationKey, time, queries);
      
      SecretKeySpec signingKey = new SecretKeySpec(clientKey.getBytes(StandardCharsets.UTF_8), SIGNATURE_METHOD_VALUE);
      Mac mac = Mac.getInstance(SIGNATURE_METHOD_VALUE);
      mac.init(signingKey);
      byte[] rawHmac = mac.doFinal(String.join("\n", ary).getBytes(StandardCharsets.UTF_8));
      result = Base64.getEncoder().encodeToString(rawHmac);
    } catch (NoSuchAlgorithmException e) {
      throw new NCMBException("アルゴリズムがありません");
    } catch (InvalidKeyException e) {
      throw new NCMBException("キーが不正です");
    }
    return result;
  }
  
  private static String queryString(String applicationKey, Timestamp time, JSONObject queries) throws NCMBException{
    String result = null;
    try {
      Map<String,String> map = new TreeMap<>();
      map.put(SIGNATURE_METHOD_NAME, SIGNATURE_METHOD_VALUE);
      map.put(SIGNATURE_VERSION_NAME, SIGNATURE_VERSION_VALUE);
      map.put(NCMB_APPLICATION_KEY_NAME, applicationKey);
      map.put(NCMB_APPLICATION_TIMESTAMP_NAME, iso8601(time));
      Iterator<String> iterator = queries.keys();
      while (iterator.hasNext()) {
        String key = iterator.next();
        map.put(key, URLEncoder.encode(queries.getJSONObject(key).toString(), "UTF-8"));
      }
      ArrayList<String> ary = new ArrayList<>();
      for (Map.Entry<String, String> entry : map.entrySet()) {
        ary.add(entry.getKey() + "=" + entry.getValue());
      }
      result = String.join("&", ary);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    } catch (UnsupportedEncodingException e) {
      throw new NCMBException("サポートされていないエンコードです");
    }
    return result;
  }
  
  public static String iso8601(Timestamp time) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
    df.setTimeZone(new SimpleTimeZone(0, "GMT"));
    return df.format(time);
  }
}