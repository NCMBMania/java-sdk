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
import java.io.IOException;

class NCMBFile {
  public String className;
  public String fileName;
  public byte[] fileData;
  public JSONObject fields;
  public NCMB ncmb;
  public Date createDate;

  public NCMBFile(NCMB ncmb_obj, String name, byte[] data) {
    className = "files";
    ncmb = ncmb_obj;
    fileName = name;
    fileData = data;
    fields = new JSONObject();
  }
  
  public Boolean save() throws NCMBException {
    try {
      JSONObject response = NCMBRequest.post(path(), ncmb.applicationKey(), ncmb.clientKey(), fileName, fileData);
      fileName = response.getString("fileName");
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
      df.setTimeZone(new SimpleTimeZone(0, "GMT"));
      createDate = df.parse(response.getString("createDate"));
      return true;
    } catch (ParseException e) {
      throw new NCMBException("日付が不正です");
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    } catch (NCMBException e) {
      throw new NCMBException(e.getMessage());
    }
  }

  public Boolean destroy() throws NCMBException{
    return true;
  }
  
  public String path() {
    String basePath = "/" + ncmb.version() + "/" + className;
    basePath = basePath + "/" + fileName;
    return basePath;
  }
}