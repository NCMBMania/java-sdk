package ncmb;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;
import java.util.Date;
import java.text.ParseException;


class NCMBObject {
  private String className;
  private JSONObject fields;
  private NCMB ncmb;
  
  public NCMBObject(NCMB ncmb_obj, String name) {
    className = name;
    ncmb = ncmb_obj;
    fields = new JSONObject();
  }
  
  // 文字列
  public void put(String field, String value) throws NCMBException {
    try {
      fields.put(field, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  // 日付型
  public void put(String field, Date value) throws NCMBException {
    try {
      fields.put(field, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  // boolean型
  public void put(String field, Boolean value) throws NCMBException {
    try {
      fields.put(field, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  // int型
  public void put(String field, int value) throws NCMBException {
    try {
      fields.put(field, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  // Long型
  public void put(String field, long value) throws NCMBException {
    try {
      fields.put(field, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }

  // 配列型
  public void put(String field, JSONArray value) throws NCMBException {
    try {
      fields.put(field, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }

  // オブジェクト型
  public void put(String field, JSONObject value) throws NCMBException {
    try {
      fields.put(field, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  public String getString(String field) throws NCMBException {
    try {
      return fields.getString(field);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  public void save() throws NCMBException{
    JSONObject response = NCMBRequest.post(path(), ncmb.applicationKey(), ncmb.clientKey(), fields);
    try {
      put("objectId", response.getString("objectId"));
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
      df.setTimeZone(new SimpleTimeZone(0, "GMT"));
      put("createDate", df.parse(response.getString("createDate")));
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    } catch (ParseException e) {
      throw new NCMBException("日付がパースできません");
    }
  }
  
  public String path() {
    return "/" + ncmb.version() + "/classes/" + className;
  }
}