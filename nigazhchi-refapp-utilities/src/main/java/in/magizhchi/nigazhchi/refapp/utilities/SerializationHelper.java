package in.magizhchi.nigazhchi.refapp.utilities;

import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;

/**
 * This class provides simple helper methods
 * to serialize any Java object 
 * to an XML string representation.
 * Usually this helper is used to write XML
 * representation of the Java object to a log
 * file.
 * 
 * NOTE: XStream is currently used as internal 
 * implementation for XML serialization.  
 * 
 * @author F589427 Denis Selivanov
 *
 */
public class SerializationHelper {
  /** Instance of XStream */
  private static final XStream XSTREAM = new XStream();
  
  /**
   * Converts (serialize) object to XML
   * string.
   * 
   * @param obj
   *     Instance of the object to be converted
   *     to XML string
   * @return
   *     XML representation of the object
   */
  public static String objectToXMLString(Object obj) {
    return XSTREAM.toXML(obj);
  }

  /**
   * Converts (serialize) object to XML
   * and writes it to given OutputStream.
   * 
   * @param obj
   *     Instance of the object to be converted
   *     to XML string
   * @param out
   *     Output stream to be used to write XML out.
   */
  public static void objectToXML(Object obj, OutputStream out) {
    XSTREAM.toXML(obj, out);
  }
  
}
