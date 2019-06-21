package ncmb;

class NCMB {
  private String APPLICATION_KEY;
  private String CLIENT_KEY;
  private String VERSION = "2013-09-01";
  
  public NCMB(String appKey, String cliKey) {
    APPLICATION_KEY = appKey;
    CLIENT_KEY = cliKey;
  }
  
  public NCMBObject NCMBObject(String name) {
    return new NCMBObject(this, name);
  }
  
  public String version() {
    return VERSION;
  }
  
  public String applicationKey() {
    return APPLICATION_KEY;
  }
  
  public String clientKey() {
    return CLIENT_KEY;
  }
}

