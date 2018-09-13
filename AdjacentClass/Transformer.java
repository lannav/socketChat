package AdjacentClass;

import java.lang.reflect.Type;

public interface Transformer {
    String getString(Object obj);

    <T> T getObject(String str, Type type);
}
