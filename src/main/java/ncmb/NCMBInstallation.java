package ncmb;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Iterator;

class NCMBInstallation extends NCMBObject {
  public NCMBInstallation(NCMB ncmb_obj) {
    super(ncmb_obj, "Installation");
  }
    
  public String path() throws NCMBException {
    try {
      String basePath = "/" + ncmb.version() + "/installations";
      if (fields.has("objectId")) {
        basePath = basePath + "/" + fields.getString("objectId");
      }
      return basePath;
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
}
