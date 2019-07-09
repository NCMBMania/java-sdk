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
      where.put(name, value);
    } catch (JSONException e) {
      throw new NCMBException("JSONが不正です");
    }
  }
  
  public void whereContainedIn(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$in", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereContainedIn");
    }
  }
  public void whereContainedInArray(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$inArray", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereContainedInArray");
    }
  }
  public void whereContainsAll(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$all", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereContainsAll");
    }
  }
  public void whereDoesNotExists(String name) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$exists", false);
      where.put(name, false);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereDoesNotExists");
    }
  }
  public void whereExists(String name) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$exists", true);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereExists");
    }
  }
  public void whereGreaterThan(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$gt", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereGreaterThan");
    }
  }
  public void whereGreaterThanOrEqualTo(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$gte", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereGreaterThanOrEqualTo");
    }
  }
  public void whereLessThan(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$lt", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereLessThan");
    }
  }
  public void whereLessThanOrEqualTo(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$lte", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereLessThanOrEqualTo");
    }
  }
  public void whereNotContainedIn(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$nin", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereNotContainedIn");
    }
  }
  public void whereNotContainedInArray(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$ninArray", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereNotContainedInArray");
    }
  }
  public void whereNotEqualTo(String name, Object value) throws NCMBException {
    try {
      JSONObject condition = where.has(name) ? where.getJSONObject(name) : new JSONObject();
      condition.put("$ne", value);
      where.put(name, condition);
    } catch (JSONException e) {
      System.out.println(e);
      throw new NCMBException("JSONが不正です : whereNotEqualTo");
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
      throw new NCMBException("JSONが不正です : find");
    }
  }
  
  public String path() {
    return "/" + ncmb.version() + "/classes/" + className;
  }
}
