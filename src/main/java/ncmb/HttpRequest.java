package ncmb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.sql.Timestamp;
import org.json.JSONObject;
import java.io.IOException;

public class HttpRequest {
  public static String exec(HttpURLConnection con, String applicationKey, Timestamp time, String signature, JSONObject data, int statusCode) throws NCMBException {
    StringBuffer result = new StringBuffer();
    try {
      con.setRequestProperty(Signature.NCMB_APPLICATION_KEY_NAME, applicationKey);
      con.setRequestProperty(Signature.NCMB_APPLICATION_TIMESTAMP_NAME, Signature.iso8601(time));
      con.setRequestProperty("X-NCMB-Signature", signature);
      con.setRequestProperty("Content-Type", "application/json");
      if (data != null) {
        con.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        out.write(data.toString());
        out.close();
      }
      con.connect();
      final int status = con.getResponseCode();
      if (status == statusCode) {
        final InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        if(null == encoding){
          encoding = "UTF-8";
        }
        final InputStreamReader inReader = new InputStreamReader(in, encoding);
        final BufferedReader bufReader = new BufferedReader(inReader);
        String line = null;
        // 1行ずつテキストを読み込む
        while((line = bufReader.readLine()) != null) {
          result.append(line);
        }
        bufReader.close();
        inReader.close();
        in.close();
      }else{
        throw new NCMBException("Auth error. HTTP Status code is " + String.valueOf(status));
      }
    } catch (Exception e) {
      throw new NCMBException(e.getMessage());
    } finally {
      if (con != null) {
        con.disconnect();
      }
    }
    return result.toString();
  }

  public static String get(String urlString, String applicationKey, Timestamp time, String signature) {
    String result = null;
    try {
      URL url = new URL(urlString);
      String method = "GET";
      HttpURLConnection con = null;
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod(method);
      result = exec(con, applicationKey, time, signature, null, HttpURLConnection.HTTP_OK);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
  
  public static String post(String urlString, String applicationKey, Timestamp time, String signature, JSONObject data) throws IOException, NCMBException {
    String result = null;
    try {
      HttpURLConnection con = null;
      String method = "POST";
      URL url = new URL(urlString);
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod(method);
      result = exec(con, applicationKey, time, signature, data, HttpURLConnection.HTTP_CREATED);
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
    return result;
  }

  public static String delete(String urlString, String applicationKey, Timestamp time, String signature) throws IOException, NCMBException {
    String result = null;
    try {
      HttpURLConnection con = null;
      String method = "DELETE";
      URL url = new URL(urlString);
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod(method);
      result = exec(con, applicationKey, time, signature, new JSONObject(), HttpURLConnection.HTTP_OK);
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
    return result;
  }
}