package org.asyou.mongo.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.asyou.mongo.transform.gson.*;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by steven on 17/7/17.
 */
public final class Convert {

    //region init
    private static Gson gson = null;
    private static JsonParser jsonParser = null;
    private static CodecRegistry codecRegistry = null;
    private static Encoder<Document> encoder = null;
    private static Decoder<Document> decoder = null;

    static {
        check_and_init();
    }

    public static void check_and_init(){
        if (null == gson || null == jsonParser) {
            init_gson_builder();
        }
    }

    private static void init_gson_builder(){
        //Gson init
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class,new ObjectIdTypeAdapter());
        builder.registerTypeAdapter(Date.class,new DateTypeAdapter());
        builder.registerTypeAdapter(LocalDateTime.class,new LocalDateTimeTypeAdapter());
        builder.registerTypeAdapter(BigDecimal.class,new BigDecimalTypeAdapter());
        builder.setDateFormat(Format.TEMPLATE_DATETIME_S3);
        gson = builder.create();

        //BsonDocument coder init
        codecRegistry = createCodecRegistry();
        encoder = createEncoder();
        decoder = createDecoder();

        //JsonParser init
        jsonParser = new JsonParser();

        //BSON EncodingHook init
//        BSON.addEncodingHook(LocalDateTime.class, new LocalDateTimeTransformer());
//        BSON.addDecodingHook(LocalDateTime.class, new LocalDateTimeTransformer());
    }

    public static CodecRegistry createCodecRegistry(){
        CodecRegistry codecRegistry = CodecRegistries.fromCodecs(
                new BsonNullCodec(),
                new ObjectIdCodec(),
                new StringCodec(),
                new BooleanCodec(),
                new ByteCodec(),
                new ByteArrayCodec(),
                new ShortCodec(),
                new IntegerCodec(),
                new LongCodec(),
                new FloatCodec(),
                new DoubleCodec(),
                new DateCodec(),
                new PatternCodec(),
                new DocumentCodec(),
                new BsonDocumentCodec()
        );
        return codecRegistry;
    }

    public static Encoder<Document> createEncoder(){
        Encoder<Document> encoder = new Encoder<Document>() {
            @Override
            public void encode(BsonWriter bsonWriter, Document document, EncoderContext encoderContext) {
                bsonWriter.writeStartDocument();
                for(String key : document.keySet()){
                    bsonWriter.writeName(key);
                    Object value = document.get(key);
                    if (null == value) {
                        bsonWriter.writeNull();
                    }else if (value instanceof ObjectId){
                        bsonWriter.writeObjectId((ObjectId) value);
                    }else if (value instanceof Boolean){
                        bsonWriter.writeBoolean((Boolean)value);
                    }else if (value instanceof Integer) {
                        bsonWriter.writeInt32((Integer) value);
                    }else if (value instanceof Long){
                        bsonWriter.writeInt64((Long)value);
                    }else if (value instanceof Double){
                        bsonWriter.writeDouble((Double)value);
                    }else if (value instanceof String){
                        bsonWriter.writeString((String)value);
                    }else if (value instanceof Date){
                        bsonWriter.writeDateTime(((Date) value).getTime());
                    }
                }
                bsonWriter.writeEndDocument();
            }

            @Override
            public Class<Document> getEncoderClass() {
                return Document.class;
            }
        };
        return encoder;
    }

    public static Decoder<Document> createDecoder(){
        Decoder<Document> decoder = new Decoder<Document>() {
            @Override
            public Document decode(BsonReader reader, DecoderContext decoderContext) {
                Document document = new Document();
                reader.readStartDocument();

                while (true) {
                    BsonType bsonType = reader.readBsonType();
                    if (bsonType == BsonType.END_OF_DOCUMENT) {
                        break;
                    }
                    String fieldName = reader.readName();
                    switch (reader.getCurrentBsonType()) {
                        case OBJECT_ID:
                            document.append(fieldName,reader.readObjectId());
                            break;
                        case BOOLEAN:
                            document.append(fieldName, reader.readBoolean());
                            break;
                        case INT32:
                            document.append(fieldName, reader.readInt32());
                            break;
                        case INT64:
                            document.append(fieldName, reader.readInt64());
                            break;
                        case DOUBLE:
                            document.append(fieldName, reader.readDouble());
                            break;
                        case STRING:
                            document.append(fieldName, reader.readString());
                            break;
                        case DATE_TIME:
                            document.append(fieldName, new Date(reader.readDateTime()));
                            break;
                        case NULL:
                            document.append(fieldName, null);
                            break;
                        default:
                            break;
                    }
                }
                return document;
            }
        };
        return decoder;
    }

    public static Gson getGson(){
        return gson;
    }

    public static void setGson(Gson gson1){
        gson = gson1;
    }

    public static void setGsonBuilder(GsonBuilder gsonBuilder){
        gson = gsonBuilder.create();
    }

    public static CodecRegistry getCodecRegistry(){
        return Convert.codecRegistry;
    }

    public static void setCodecRegistry(CodecRegistry codecRegistry){
        Convert.codecRegistry = codecRegistry;
    }

    public static Encoder<Document> getEncoder(){
        return Convert.encoder;
    }

    public static void setEncoder(Encoder<Document> encoder){
        Convert.encoder = encoder;
    }

    public static Decoder<Document> getDecoder(){
        return Convert.decoder;
    }

    public static void setDecoder(Decoder<Document> decoder){
        Convert.decoder = decoder;
    }
    //endregion

    //transform

    public static <T> T toModel(Bson bsonObject, Class<T> clazz){
        if (Document.class == bsonObject.getClass()) {
            return gson.fromJson(gson.toJson(bsonObject), clazz);
        } else {
            return gson.fromJson(bsonObject.toString(), clazz);
        }
    }

    public static <T> T toModel(String bsonObject, Class<T> clazz){
        return gson.fromJson(gson.toJson(bsonObject), clazz);
    }

    public static <T> T toBson(String json, Class<T> clazz){
        if (BsonDocument.class == clazz){
            return (T) BsonDocument.parse(json);
        }else if(Document.class == clazz){
            return (T) Document.parse(json);
        }
        return gson.fromJson(json, null == clazz? String.class : clazz);
    }

    public static Bson toBson(String json){
//        Map<String, Object> map = gson.toBson(json, new TypeToken<Map<String, Object>>() {}.getType());
        return toBson(json, Document.class);
    }

    public static <T> Bson toBson(T model){
        return Convert.toBson(Convert.toJson(model));
    }

    public static BsonDocument toBsonDocument(Bson bsonDocument){
        if (bsonDocument instanceof BsonDocument) {
            return (BsonDocument) bsonDocument;
        } else {
            return bsonDocument.toBsonDocument(Document.class, Convert.getCodecRegistry());
        }
    }

    public static List<Bson> toBsonList(List<String> jsonList){
        List<Bson> bsonList = new ArrayList<>();
        for(String json : jsonList){
            bsonList.add(Convert.toBson(json));
        }
        return bsonList;
    }

    // T:String to Bson
    // T:Bson to String
    // T:Object to String to Bson
    public static <R, T> List<R> transformList(List<T> list){
        List<R> resultList = new ArrayList<R>();
        for(T t : list){
            if (t instanceof String) {
                //如果输入String，则输出R
                resultList.add((R) Convert.toBson((String)t));
            }else if (t instanceof BSONObject){
                //如果输入BSONObject，则输出String
                resultList.add((R) Convert.toJson(t));
            }else{
                String jsonStr = Convert.toJson(t);
                resultList.add((R) Convert.toBson(jsonStr));
            }
        }
        return resultList;
    }

    public static <R, T> R transform(T input){
        if (input instanceof String){
            //如果输入String, 则输出BSONObject
            return (R) toBson((String) input);
        }else{
            //如果输入BSONObject, 则输出String
            return (R) toJson(input);
        }
    }


    /**
     * 把model转换成json格式，如果源model本身是string类型，直接返回，如果格式不正确，返回null
     * @param model 源model
     * @return json
     */
    public static <T> String toJson(T model){
        if (model instanceof String) {
            return model.toString();
        }else if (model instanceof BsonDocument){
            return gson.toJson(Document.parse(model.toString()));
        }
        return gson.toJson(model);
    }

    public static <T> List<String> toJsonList(List<T> list){
        List<String> jsonList = new ArrayList<>();
        for (T t : list) {
            jsonList.add(Convert.toJson(t));
        }
        return jsonList;
    }

    public static boolean isJson(String json) {
        if (StringUtils.isBlank(json)) {
            return false;
        }
        try {
            jsonParser.parse(json);
            return true;
        } catch (JsonParseException e) {
            //todo insert a log
            return false;
        }
    }
}
