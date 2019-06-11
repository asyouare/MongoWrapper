>### **结构说明：**
---
- #### **服务器对象：**
```text
MongoAdapter    ：数据库适配器，单例模式。
MongoHost	：数据库服务器，包含在MongoAdapter中，单例模式。
Config		：数据库配置选项，包含在MongoHost中。

// 用单例模式创建服务器对象。
```
- #### **操作器对象：**
```text
Find		：查找操作器，MongoAdapter中直接获取。
Insert		：插入操作器，MongoAdapter中直接获取。
Update		：更新操作器，MongoAdapter中直接获取。
Delete		：删除操作器，MongoAdapter中直接获取。
Count       	：统计数量操作器，MongoAdapter中直接获取。
Aggregate	：聚合函数操作器，MongoAdapter中直接获取 
```
- #### **查询器对象：**
```text
IQuery		：查询器接口。
QueryMatcher	：条件匹配器。
QueryAggregate	：聚合函数生成器
QueryObject	：IQuery生成器
```
- #### **使用Maven安装：**
```xml
<!--http://maven.asyou.cc:8081/repository/maven-public/-->  
<dependency>  
    <groupId>org.asyou</groupId>  
    <artifactId>asyou-mongowrapper</artifactId>  
    <version>1.0</version>  
</dependency>  
```

- #### **如何配置：**

你可以使用最简单的方式进行配置：
```text
Config dataConfig = new Config("adapter_data","hostname",27017,"database_name");
```
也可以使用更详细的配置参数：
```text
MongoClientOptions options = ....
Config logConfig = new Config("adapter_log","hostname",27017,"user_name","password","database_name",options);
```
装入构造器对象，多个数据库服务器可以配置多个Config，然后使用ConfigManager装入：  
```text
ConfigManager.addConfig(
    dataConfig,
    logConfig
);
```

装入后，会自动生成MongoHost对象，这个MongoHost是单例模式，也是MongoAdapter对象创建的必须条件。Config中的adapterId必须是唯一的，构造MongoAdapter时给出的ID与Config中的adapterId是一致的。

- #### **如何连接数据库：**
数据库的连接操作已经集成在操作器内部，你可以忽略数据库连接的任何操作，而只关心操作本身：
```text
MongoAdapter.create("adapter_data").find()
MongoAdapter.create("adapter_data").insert()
MongoAdapter.create("adapter_data").update()
MongoAdapter.create("adapter_data").delete()
MongoAdapter.create("adapter_data").count()
MongoAdapter.create("adapter_data").aggregate()
```

- #### **如何使用查询器：**

我们提供了两个快速查询器，QueryMatcher用来做条件匹配，QueryAggregate用来生成聚合函数。
```text
QueryMatcher queryMatcher = QueryMatcher.create("{a:1,b:2,c:'string'}").or().not();
System.out.println(queryMatcher.toBson().toString());

->{$not:[{$or:[{a:1},{b:2},{c:"string"}]}]}
```
下面一次性获得统计amount字段的求和结果，和price字段的平均值结果。
```text
QueryAggregate queryAggregate = QueryAggregate.create();
queryAggregate.match(queryMatcher).group().sum("amount").avg("price");

Map<String,Number> values = mongoAdapter.aggregate().match(queryAggregate).values();
```
values中的key默认是使用的函数名称，上面使用了sum和avg，也可以自定义返回名称。

- #### **如何自定义查询条件：**
对于极其复杂的查询条件，快速查询器无法满足，可以使用QueryObject用来装入你的自定义查询条件。
```text
QueryObject query = QueryObject.create(or(and(eq("a",1),eq("b",2),eq("c","string"))));
```
聚合操作也同样支持自定义条件：
```text
Document match = new Documnet("$match",new Document());
Document group = new Document("$group", new Document("_id", "orderId", new Document("amount", new Document("sum","price"))));
List<Bson> queryList = new ArrayList<>();
queryList.add(match);
queryList.add(group);
List<Document> resultList = queryAggregate.aggregate(queryList);
```
将自定义条件match和group传入，aggregate会返回List<Document>执行结果。
