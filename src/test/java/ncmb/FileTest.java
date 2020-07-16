package ncmb;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class FileTest {
  static NCMB ncmb;
  
  @BeforeAll
  public static void setup() {
    String applicationKey = "70dfced7542e494861ec39ba7442115dfa9806312a444831ed9a7faac5087934";
    String clientKey = "4d0dea61349c1ae47106a06c80f11dfffe705e606709fa9563ac5cf80cf2edff";
    ncmb = new NCMB(applicationKey, clientKey);
  }
  
  @Test
  public void testCreate() {
    try {
      byte[] data = Files.readAllBytes(Paths.get("./test.png"));
      NCMBFile file = ncmb.NCMBFile("test333.png", data);
      if (file.save()) {
        System.out.println("保存完了");
        System.out.println(file.fileName);
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (NCMBException e) {
      System.err.println(e.getMessage());
    }
  }
}
