package AdjacentClass;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class Transformer_JSON implements Transformer {
    private static Transformer_JSON Instance;
    private Gson gson = new Gson();

    private Transformer_JSON(){}

    public String getString(Object obj){
        return gson.toJson(obj);
    }

    public <T> T getObject(String str, Type type){
        return gson.fromJson(str, type);
    }

    public static Transformer_JSON getInstance() {
        if(Instance == null)
            Instance = new Transformer_JSON();

        return Instance;
    }
}
