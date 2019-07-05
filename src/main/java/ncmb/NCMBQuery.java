package ncmb;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

class NCMBQuery {
  private String className;
  private JSONObject where;
  private Integer limit;
  private Integer offset;
  private String order;
  private Boolean descending;
  private NCMB ncmb;
  
  public NCMBQuery(NCMB ncmb_obj, String name) {
    className = name;
    ncmb = ncmb_obj;
    where = new JSONObject();
    limit = 20;
    offset = 0;
    order = "createDate";
    descending = false;
  }
  
  public void whereEqualTo(String name, Object value) throws NCMBException {
    try {
      where.put (name, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  public void limit(int l) {
    limit = l;
  }
  
  public ArrayList<NCMBObject> find() throws NCMBException {
    try {
      JSONObject queries = new JSONObject();
      queries.put("where", where);
      queries.put("limit", limit);
      JSONObject response = NCMBRequest.get(path(), ncmb.applicationKey(), ncmb.clientKey(), queries);
      JSONArray ary = response.getJSONArray("results");
      ArrayList<NCMBObject> results = new ArrayList<>();
      for (int i=0; i < ary.length(); i++) {
        JSONObject item = ary.getJSONObject(i);
        NCMBObject obj = ncmb.NCMBObject(className);
        obj.set(item);
        results.add(obj);
      };
      return results;
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  public String path() {
    return "/" + ncmb.version() + "/classes/" + className;
  }
}
