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

## LICENSE

MIT.
