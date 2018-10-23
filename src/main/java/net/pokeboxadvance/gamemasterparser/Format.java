package net.pokeboxadvance.gamemasterparser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formatting utility.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-08-15
 */
public class Format {

  private static final Logger LOGGER = LogManager.getLogger();

  public static String putInQuotes(String string) {
    return "\"" + string + "\"";
  }

  public static String putInQuotes(Object object) {
    return "\"" + object.toString() + "\"";
  }

  public static String toVariableName(Object object) {
    return toVariableName(ArrayUtils.contains(object.getClass().getInterfaces(), new Named() {
      @Override
      public String getName() {
        return null;
      }
    }) ? ((Named) object).getName() : object.toString());
  }

  public static String toVariableName(Named named) {
    return toVariableName(named.getName());
  }

  public static String toVariableName(String string) {
    return string == null ? null : string.replaceAll("[-. ]", "_").toUpperCase();
  }

  public static String objectifyField(String name, Object value) {
    if (name == null) {
      LOGGER.warn("Field name is null");
    }
    StringBuilder json = new StringBuilder();
    json.append(Format.putInQuotes(name));
    json.append(": ");
    if (value instanceof String) {
      json.append(Format.putInQuotes(value));
    } else if (value != null && value.getClass().isArray()) {
      json.append(Format.arrayToString(value));
    } else {
      json.append(value);
    }
    return json.toString();
  }

  public static String toECMAScriptObject(Map<String, Object> fieldMap) {
    StringBuilder def = new StringBuilder(
        "const " + Format.toVariableName(fieldMap.get("name")) + " = {");
    int count = 0;
    for (Entry<String, Object> entry : fieldMap.entrySet()) {
      try {
        def.append(Format.objectifyField(entry.getKey(), entry.getValue()));

        if (++count != fieldMap.size()) {
          def.append(", ");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    def.append("};");
    return def.toString();
  }

  public static String arrayToString(Object array) {
    StringBuilder string = new StringBuilder("[");
    int length = Array.getLength(array);
    for (int i = 0; i < length; i++) {
      string.append(Array.get(array, i));
      if (i != length - 1) {
        string.append(", ");
      }
    }
    string.append(']');
    return string.toString();
  }

  public static void logDebug(String string) {
    LOGGER.debug(string);
  }
}
