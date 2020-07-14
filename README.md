# NCMB Java SDK

Androidではなく、JavaからmBaaSを操作できるようにします。

## 共通処理

初期化は次のように行います。

```java
String applicationKey = "b34...01e";
String clientKey = "489...462";
NCMB ncmb = new NCMB(applicationKey, clientKey);
```

## データストア

### 保存

```java
try {
  NCMBObject hello = ncmb.NCMBObject("Hello");
  hello.put("message", "Hello World");
  hello.save();
  System.out.println(hello.getString("objectId"));
} catch (NCMBException e) {
  System.err.println(e.getMessage());
}
```

### 削除

```java
hello.destroy();
```

### 1件取得

```java
try {
  NCMBObject Hello = ncmb.NCMBObject("Hello");
  Hello.setObjectId("hZq3u8EeqULE4CBN");
  if (Hello.fetch()) {
    System.err.println(Hello.getString("message"));
  }
} catch (NCMBException e) {
  System.err.println(e.getMessage());
}
```

### データストアの検索と抽出件数の設定

```java
try {
  NCMBQuery Query = ncmb.NCMBQuery("Hello");
  Query.whereEqualTo("int", 1000);
  Query.limit(5);
  ArrayList<NCMBObject> ary = Query.find();
  assertEquals(ary.get(0).getInt("int"), 1000);
  assertEquals(ary.size(), 5);
} catch (NCMBException e) {
  System.err.println(e.getMessage());
}
```

複数の検索条件設定

```java
try {
  NCMBQuery Hello = ncmb.NCMBQuery("Hello");
  Hello.whereGreaterThanOrEqualTo("int", 400);
  Hello.whereLessThan("int", 800);
  ArrayList<NCMBObject> ary = Hello.find();
  ary.forEach((o) -> {
    try {
      System.out.println(o.getInt("int"));
    } catch (NCMBException e) {
      System.out.println(e);
    }
  });
} catch (NCMBException e) {
  System.err.println(e.getMessage());
}
```

## 会員管理

### 会員登録

```java
try {
  NCMBUser user = ncmb.NCMBUser();
  user.put("userName", "test_user");
  user.put("password", "password");
  if (user.signUp()) {
    System.out.println(user.getString("objectId"));
  } else {
    System.err.println("Login failed.");
  }
} catch (NCMBException e) {
  System.err.println(e.getMessage());
}
```

## デバイストークン

### 登録

```java
NCMBInstallation installation = ncmb.NCMBInstallation();
installation.put("sdkVersion", "1.0.0");
installation.put("deviceToken", "aaaaaaaaaaaaaaaaaaaa");
installation.put("deviceType", "ios");
installation.put("appVersion", "1.0.0");
installation.put("timeZone", "Asia/Tokyo");
installation.put("applicationName", "Java SDK");
installation.save();

// 削除
installation.destroy();
```

## プッシュ通知

### 登録

```java
NCMBPush push = ncmb.NCMBPush();
push.put("immediateDeliveryFlag", true);
push.put("message", "Hello, World!");
JSONArray target = new JSONArray();
target.put("ios");
target.put("android");
push.put("target", target);
push.save();
// 削除
push.destroy();
```

## LICENSE

MIT.
