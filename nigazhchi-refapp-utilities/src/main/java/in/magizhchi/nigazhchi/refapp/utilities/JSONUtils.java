package in.magizhchi.nigazhchi.refapp.utilities;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONStringer;
import org.json.JSONTokener;

public final class JSONUtils {

  public static String buildJSON(Map<String, Object> params) {
    JSONStringer stringer = new JSONStringer();
    stringer.object();

    for (Map.Entry<String, Object> param : params.entrySet()) {
      if ((param.getKey() != null) && (!("".equals(param.getKey()))) && (param.getValue() != null)
          && (!("".equals(param.getValue())))) {
        stringer.key((String) param.getKey()).value(param.getValue());
      }
    }

    return stringer.endObject().toString();
  }

  public static Map<String, Object> parseJSON(String jsonBody) {
    Map<String, Object> params = new HashMap<String, Object>();

    JSONTokener x = new JSONTokener(jsonBody);

    if (x.nextClean() != '{') {
      throw new IllegalArgumentException(String.format(
          "String '%s' is not a valid JSON object representation, a JSON object text must begin with '{'",
          new Object[] {jsonBody}));
    }
    while (true) {
      char c = x.nextClean();
      switch (c) {
        case '\0':
          throw new IllegalArgumentException(String.format(
              "String '%s' is not a valid JSON object representation, a JSON object text must end with '}'",
              new Object[] {jsonBody}));
        case '}':
          return params;
      }
      x.back();
      String key = x.nextValue().toString();

      c = x.nextClean();
      if (c == '=') {
        if (x.next() != '>') x.back();
      } else if (c != ':') {
        throw new IllegalArgumentException(String.format(
            "String '%s' is not a valid JSON object representation, expected a ':' after the key '%s'", new Object[] {
                jsonBody, key}));
      }

      Object value = x.nextValue();

      if (value != null) {
        if (value instanceof JSONArray) {
          JSONArray array = (JSONArray) value;
          Object[] values = new Object[array.length()];
          for (int i = 0; i < array.length(); ++i) {
            values[i] = array.get(i);
          }
          value = values;
        }

        params.put(key, value);
      }

      switch (x.nextClean()) {
        case ',':
        case ';':
          if (x.nextClean() == '}') {
            return params;
          }
          x.back();
          break;
        case '}':
          return params;
        default:
          throw new IllegalArgumentException("Expected a ',' or '}'");
      }
    }
  }

}
