package in.magizhchi.nigazhchi.refapp.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PropertiesUtil {
  
  public static boolean toBoolean(Object propValue, boolean defaultValue) {
    propValue = toObject(propValue);
    if (propValue instanceof Boolean) return ((Boolean) propValue).booleanValue();
    if (propValue != null) {
      return Boolean.valueOf(String.valueOf(propValue)).booleanValue();
    }

    return defaultValue;
  }

  public static String toString(Object propValue, String defaultValue) {
    propValue = toObject(propValue);
    return ((propValue != null) ? propValue.toString() : defaultValue);
  }

  public static long toLong(Object propValue, long defaultValue) {
    propValue = toObject(propValue);
    if (propValue instanceof Long) return ((Long) propValue).longValue();
    if (propValue != null) {
      try {
        return Long.valueOf(String.valueOf(propValue)).longValue();
      } catch (NumberFormatException nfe) {}
    }
    return defaultValue;
  }

  public static int toInteger(Object propValue, int defaultValue) {
    propValue = toObject(propValue);
    if (propValue instanceof Integer) return ((Integer) propValue).intValue();
    if (propValue != null) {
      try {
        return Integer.valueOf(String.valueOf(propValue)).intValue();
      } catch (NumberFormatException nfe) {}
    }
    return defaultValue;
  }

  public static double toDouble(Object propValue, double defaultValue) {
    propValue = toObject(propValue);
    if (propValue instanceof Double) return ((Double) propValue).doubleValue();
    if (propValue != null) {
      try {
        return Double.valueOf(String.valueOf(propValue)).doubleValue();
      } catch (NumberFormatException nfe) {}
    }
    return defaultValue;
  }

  public static Object toObject(Object propValue) {
    if (propValue == null) return null;
    if (propValue.getClass().isArray()) {
      Object[] prop = (Object[]) (Object[]) propValue;
      return ((prop.length > 0) ? prop[0] : null);
    }
    if (propValue instanceof Collection) {
      Collection<?> prop = (Collection<?>) propValue;
      return ((prop.isEmpty()) ? null : prop.iterator().next());
    }
    return propValue;
  }

  public static String[] toStringArray(Object propValue) {
    return toStringArray(propValue, null);
  }

  public static String[] toStringArray(Object propValue, String[] defaultArray) {
    if (propValue == null) {
      return defaultArray;
    }
    if (propValue instanceof String) {
      return new String[] {(String) propValue};
    }
    if (propValue instanceof String[]) {
      return ((String[]) (String[]) propValue);
    }
    if (propValue.getClass().isArray()) {
      Object[] valueArray = (Object[]) (Object[]) propValue;
      List<String> values = new ArrayList<String>(valueArray.length);
      for (Object value : valueArray) {
        if (value != null) {
          values.add(value.toString());
        }
      }
      return ((String[]) values.toArray(new String[values.size()]));
    }
    if (propValue instanceof Collection) {
      Collection<?> valueCollection = (Collection<?>) propValue;
      List<String> valueList = new ArrayList<String>(valueCollection.size());
      for (Iterator<?> i$ = valueCollection.iterator(); i$.hasNext();) {
        Object value = i$.next();
        if (value != null) {
          valueList.add(value.toString());
        }
      }
      return ((String[]) valueList.toArray(new String[valueList.size()]));
    }

    return defaultArray;
  }

  public static Map<String, String> toMap(Object propValue, String[] defaultArray) {
    String[] arrayValue = toStringArray(propValue, defaultArray);

    if (arrayValue == null) {
      return null;
    }

    Map<String, String> result = new LinkedHashMap<String, String>();
    for (String kv : arrayValue) {
      int indexOfEqual = kv.indexOf(61);
      if (indexOfEqual > 0) {
        String key = trimToNull(kv.substring(0, indexOfEqual));
        String value = trimToNull(kv.substring(indexOfEqual + 1));
        if (key != null) {
          result.put(key, value);
        }
      }
    }
    return result;
  }

  private static String trimToNull(String str) {
    String ts = trim(str);
    return ((isEmpty(ts)) ? null : ts);
  }

  private static String trim(String str) {
    return ((str == null) ? null : str.trim());
  }

  private static boolean isEmpty(String str) {
    return ((str == null) || (str.length() == 0));
  }
}
