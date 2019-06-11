package org.asyou.mongo.transform.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by steven on 17/7/19.
 */
public abstract class ABasicTypeAdapter<T> extends TypeAdapter<T> {

    @Override
    public T read(JsonReader jsonReader) throws IOException{
//        if (jsonReader.peek() == JsonToken.NULL) {
//            jsonReader.nextNull();
//            return null;
//        }
        return reading(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, T value) throws IOException{
//        System.out.println("write: " + value + " : " + (value != null ? value.getClass().getName() : ""));
        //value 会多次为空，需要前置判断，原理未查
        if (value == null) {
            jsonWriter.nullValue();
            return;
        }
        writing(jsonWriter, value);
    }

    public abstract T reading(JsonReader jsonReader) throws IOException;

    public abstract void writing(JsonWriter jsonWriter, T value) throws IOException;

    protected boolean compareToken(JsonToken currentToken, JsonToken targetToken){
        return currentToken == targetToken;
    }

    protected boolean isNameToken(JsonToken currentToken){
        return compareToken(currentToken, JsonToken.NAME);
    }

    protected boolean isStringToken(JsonToken currentToken){
        return compareToken(currentToken, JsonToken.STRING);
    }

    protected boolean isNumberToken(JsonToken currentToken){
        return compareToken(currentToken, JsonToken.NUMBER);
    }

    protected boolean isBooleanToken(JsonToken currentToken){
        return compareToken(currentToken, JsonToken.BOOLEAN);
    }

    protected boolean isBeginToken(JsonToken currentToken){
        return compareToken(currentToken, JsonToken.BEGIN_OBJECT);
    }

    protected boolean isEndToken(JsonToken currentToken){
        return compareToken(currentToken, JsonToken.END_OBJECT);
    }

    protected boolean isBeginArrToken(JsonToken currentToken){
        return compareToken(currentToken, JsonToken.BEGIN_ARRAY);
    }

    protected boolean isEndArrToken(JsonToken currentToken){
        return compareToken(currentToken, JsonToken.END_ARRAY);
    }
}
