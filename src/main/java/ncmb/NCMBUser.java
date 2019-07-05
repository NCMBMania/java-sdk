package ncmb;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Iterator;

class NCMBUser extends NCMBObject {
  public NCMBUser(NCMB ncmb_obj) {
    super(ncmb_obj, "User");
  }
  
  public boolean signUp() throws NCMBException {
    try {
      JSONObject response = NCMBRequest.post(path(), ncmb.applicationKey(), ncmb.clientKey(), fields);
      set(response);
      return true;
    } catch (NCMBException e) {
      return false;
    }
  }
  
  public String path() throws NCMBException {
    try {
      String basePath = "/" + ncmb.version() + "/users";
      if (fields.has("objectId")) {
        basePath = basePath + "/" + fields.getString("objectId");
      }
      return basePath;
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
}
