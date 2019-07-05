package ncmb;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;
import java.util.Date;
import java.text.ParseException;
import java.util.Iterator;

class NCMBObject {
  public String className;
  public JSONObject fields;
  public NCMB ncmb;
  
  public NCMBObject(NCMB ncmb_obj, String name) {
    className = name;
    ncmb = ncmb_obj;
    fields = new JSONObject();
  }
  
  public void setObjectId(String value) throws NCMBException {
    try {
      fields.put("objectId", value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  public Boolean fetch() throws NCMBException {
    try {
      JSONObject response = NCMBRequest.get(path(), ncmb.applicationKey(), ncmb.clientKey(), new JSONObject());
      Iterator<String> keys = response.keys();

      while(keys.hasNext()) {
        String key = keys.next();
        fields.put(key, response.get(key));
      }
      return true;
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
    
  // boolean型
  public void put(String field, Object value) throws NCMBException {
    try {
      fields.put(field, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  public void set(JSONObject data) throws NCMBException {
    try {
      Iterator<String> keys = data.keys();
      while(keys.hasNext()) {
        String key = keys.next();
        fields.put(key, data.get(key));
      }
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
  
  public int getInt(String field) throws NCMBException {
    try {
      return fields.getInt(field);
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
  
  public String path() throws NCMBException {
    try {
      String basePath = "/" + ncmb.version() + "/classes/" + className;
      if (fields.has("objectId")) {
        basePath = basePath + "/" + fields.getString("objectId");
      }
      return basePath;
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
}