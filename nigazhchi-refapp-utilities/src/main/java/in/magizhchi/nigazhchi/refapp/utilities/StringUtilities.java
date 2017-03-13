package in.magizhchi.nigazhchi.refapp.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

public final class StringUtilities {

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

}
