package ncmb;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.ContentType;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.sql.Timestamp;
import org.json.JSONObject;

public class HttpRequest {
  public static String get(String urlString, String applicationKey, Timestamp time, String signature) {
    String result = null;
    try {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpGet httpGet = new HttpGet(urlString);
      httpGet.setHeader("Content-type", "application/json; charset=UTF-8");
      httpGet.setHeader(Signature.NCMB_APPLICATION_KEY_NAME, applicationKey);
      httpGet.setHeader(Signature.NCMB_APPLICATION_TIMESTAMP_NAME, Signature.iso8601(time));
      httpGet.setHeader("X-NCMB-Signature", signature);
      CloseableHttpResponse response = client.execute(httpGet);
      client.close();
      result = EntityUtils.toString(response.getEntity());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
  
  public static String post(String urlString, String applicationKey, Timestamp time, String signature, JSONObject data) throws IOException, NCMBException {
    String result = null;
    try {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(urlString);
      httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
      httpPost.setHeader(Signature.NCMB_APPLICATION_KEY_NAME, applicationKey);
      httpPost.setHeader(Signature.NCMB_APPLICATION_TIMESTAMP_NAME, Signature.iso8601(time));
      httpPost.setHeader("X-NCMB-Signature", signature);
      StringEntity entity = new StringEntity(data.toString(), "UTF-8");
      httpPost.setEntity(entity);
      CloseableHttpResponse response = client.execute(httpPost);
      client.close();
      result = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
    return result;
  }

  public static String post(String urlString, String applicationKey, Timestamp time, String signature, String fileName, byte[] data) throws IOException, NCMBException {
    String result = null;
    try {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(urlString);
      httpPost.setHeader(Signature.NCMB_APPLICATION_KEY_NAME, applicationKey);
      httpPost.setHeader(Signature.NCMB_APPLICATION_TIMESTAMP_NAME, Signature.iso8601(time));
      httpPost.setHeader("X-NCMB-Signature", signature);
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.addBinaryBody("file", data, ContentType.DEFAULT_BINARY, fileName);
      HttpEntity entity = builder.build();
      httpPost.setEntity(entity);
      CloseableHttpResponse response = client.execute(httpPost);
      result = EntityUtils.toString(response.getEntity());
      client.close();
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
      CloseableHttpClient client = HttpClients.createDefault();
      HttpDelete httpDelete = new HttpDelete(urlString);
      httpDelete.setHeader("Content-type", "application/json; charset=UTF-8");
      httpDelete.setHeader(Signature.NCMB_APPLICATION_KEY_NAME, applicationKey);
      httpDelete.setHeader(Signature.NCMB_APPLICATION_TIMESTAMP_NAME, Signature.iso8601(time));
      httpDelete.setHeader("X-NCMB-Signature", signature);
      CloseableHttpResponse response = client.execute(httpDelete);
      client.close();
      result = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
    return result;
  }
}