package ncmb;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Iterator;

class NCMBPush extends NCMBObject {
  public NCMBPush(NCMB ncmb_obj) {
    super(ncmb_obj, "Push");
  }
  
  public String path() throws NCMBException {
    try {
      String basePath = "/" + ncmb.version() + "/push";
      if (fields.has("objectId")) {
        basePath = basePath + "/" + fields.getString("objectId");
      }
      return basePath;
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
}
