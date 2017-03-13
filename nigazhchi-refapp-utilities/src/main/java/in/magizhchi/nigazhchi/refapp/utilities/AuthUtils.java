package in.magizhchi.nigazhchi.refapp.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.codec.binary.Base64;

public final class AuthUtils {


  public static final String AUTH_SCHEME = "Bearer";
  private static final Pattern OAUTH_HEADER = Pattern.compile("\\s*(\\w*)\\s+(.*)");
  private static final Pattern NVP = Pattern.compile("(\\S*)\\s*\\=\\s*\"([^\"]*)\"");
  public static final String MULTIPART = "multipart/";


  public static String format(Collection<? extends Map.Entry<String, Object>> parameters, String encoding) {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Object> parameter : parameters) {
      String value = (parameter.getValue() == null) ? null : String.valueOf(parameter.getValue());
      if ((!(isEmpty((String) parameter.getKey()))) && (!(isEmpty(value)))) {
        String encodedName = encode((String) parameter.getKey(), encoding);
        String encodedValue = (value != null) ? encode(value, encoding) : "";
        if (result.length() > 0) {
          result.append("&");
        }
        result.append(encodedName);
        result.append("=");
        result.append(encodedValue);
      }
    }
    return result.toString();
  }

  private static String encode(String content, String encoding) {
    try {
      return URLEncoder.encode(content, (encoding != null) ? encoding : "UTF-8");
    } catch (UnsupportedEncodingException problem) {
      throw new IllegalArgumentException(problem);
    }
  }


  public static boolean isEmpty(String value) {
    return ((value == null) || ("".equals(value)));
  }

  public static boolean hasEmptyValues(String[] array) {
    if ((array == null) || (array.length == 0)) {
      return true;
    }
    for (String s : array) {
      if (isEmpty(s)) {
        return true;
      }
    }
    return false;
  }

  public static String getAuthzMethod(String header) {
    if (header != null) {
      Matcher m = OAUTH_HEADER.matcher(header);
      if (m.matches()) {
        return m.group(1);
      }
    }

    return null;
  }

  public static Set<String> decodeScopes(String s) {
    Set<String> scopes = new HashSet<String>();
    if (!(isEmpty(s))) {
      StringTokenizer tokenizer = new StringTokenizer(s, " ");

      while (tokenizer.hasMoreElements()) {
        scopes.add(tokenizer.nextToken());
      }
    }
    return scopes;
  }

  public static String encodeScopes(Set<String> s) {
    StringBuffer scopes = new StringBuffer();
    for (String scope : s) {
      scopes.append(scope).append(" ");
    }
    return scopes.toString().trim();
  }



  public static boolean hasContentType(String requestContentType, String requiredContentType) {
    if ((isEmpty(requiredContentType)) || (isEmpty(requestContentType))) {
      return false;
    }
    StringTokenizer tokenizer = new StringTokenizer(requestContentType, ";");
    while (tokenizer.hasMoreTokens()) {
      if (requiredContentType.equals(tokenizer.nextToken())) {
        return true;
      }
    }

    return false;
  }

  public static String convertStreamToString(InputStream stream) throws IOException {

    String output = StringUtils.EMPTY;

    BufferedReader breader = new BufferedReader(new InputStreamReader(stream));
    StringBuilder responseString = new StringBuilder();
    String line = "";
    while ((line = breader.readLine()) != null) {
      responseString.append(line);
    }
    breader.close();

    output = responseString.toString();

    return output;

  }

  public static Map<String, Object> decodeForm(String form) {
    Map<String, Object> params = new HashMap<String, Object>();
    if (!(isEmpty(form))) {
      for (String nvp : form.split("\\&")) {
        int equals = nvp.indexOf(61);
        String value;
        String name;
        if (equals < 0) {
          name = decodePercent(nvp);
          value = null;
        } else {
          name = decodePercent(nvp.substring(0, equals));
          value = decodePercent(nvp.substring(equals + 1));
        }
        params.put(name, value);
      }
    }
    return params;
  }

  public static boolean isFormEncoded(String contentType) {
    if (contentType == null) {
      return false;
    }
    int semi = contentType.indexOf(";");
    if (semi >= 0) {
      contentType = contentType.substring(0, semi);
    }
    return "application/x-www-form-urlencoded".equalsIgnoreCase(contentType.trim());
  }

  public static String decodePercent(String s) {
    try {
      return URLDecoder.decode(s, "UTF-8");
    } catch (UnsupportedEncodingException wow) {
      throw new RuntimeException(wow.getMessage(), wow);
    }
  }

  public static String percentEncode(String s) {
    if (s == null) return "";
    try {
      return URLEncoder.encode(s, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    } catch (UnsupportedEncodingException wow) {
      throw new RuntimeException(wow.getMessage(), wow);
    }
  }

  public static String getAuthHeaderField(String authHeader) {
    if (authHeader != null) {
      Matcher m = OAUTH_HEADER.matcher(authHeader);
      if ((m.matches()) && ("Bearer".equalsIgnoreCase(m.group(1)))) {
        return m.group(2);
      }
    }

    return null;
  }

  public static Map<String, String> decodeOAuthHeader(String header) {
    Map<String, String> headerValues = new HashMap<String, String>();
    if (header != null) {
      Matcher m = OAUTH_HEADER.matcher(header);
      if ((m.matches()) && ("Bearer".equalsIgnoreCase(m.group(1)))) {
        for (String nvp : m.group(2).split("\\s*,\\s*")) {
          m = NVP.matcher(nvp);
          if (m.matches()) {
            String name = decodePercent(m.group(1));
            String value = decodePercent(m.group(2));
            headerValues.put(name, value);
          }
        }
      }
    }

    return headerValues;
  }

  public static String[] decodeClientAuthenticationHeader(String authenticationHeader) {
    if (isEmpty(authenticationHeader)) {
      return null;
    }
    String[] tokens = authenticationHeader.split(" ");
    if (tokens.length != 2) {
      return null;
    }
    String authType = tokens[0];
    if (!("basic".equalsIgnoreCase(authType))) {
      return null;
    }
    String encodedCreds = tokens[1];
    return decodeBase64EncodedCredentials(encodedCreds);
  }

  private static String[] decodeBase64EncodedCredentials(String encodedCreds) {
    String decodedCreds = new String(Base64.decodeBase64(encodedCreds));
    String[] creds = decodedCreds.split(":", 2);
    if (creds.length != 2) {
      return null;
    }
    if ((!(isEmpty(creds[0]))) && (!(isEmpty(creds[1])))) {
      return creds;
    }
    return null;
  }

  public static String encodeOAuthHeader(Map<String, Object> entries) {
    StringBuffer sb = new StringBuffer();
    sb.append("Bearer").append(" ");

    if (entries.get("realm") != null) {
      String value = String.valueOf(entries.get("realm"));
      if (!(isEmpty(value))) {
        sb.append("realm=\"");
        sb.append(value);
        sb.append("\",");
      }
      entries.remove("realm");
    }
    for (Map.Entry<String, Object> entry : entries.entrySet()) {
      String value = (entry.getValue() == null) ? null : String.valueOf(entry.getValue());
      if ((!(isEmpty((String) entry.getKey()))) && (!(isEmpty(value)))) {
        sb.append((String) entry.getKey());
        sb.append("=\"");
        sb.append(value);
        sb.append("\",");
      }
    }

    return sb.substring(0, sb.length() - 1);
  }

  public static String encodeAuthorizationBearerHeader(Map<String, Object> entries) {
    StringBuffer sb = new StringBuffer();
    sb.append("Bearer").append(" ");
    for (Map.Entry<String, Object> entry : entries.entrySet()) {
      String value = (entry.getValue() == null) ? null : String.valueOf(entry.getValue());
      if ((!(isEmpty((String) entry.getKey()))) && (!(isEmpty(value)))) {
        sb.append(value);
      }
    }

    return sb.toString();
  }



}
