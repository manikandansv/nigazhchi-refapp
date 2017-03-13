package in.magizhchi.nigazhchi.refapp.persistence;

import in.magizhchi.nigazhchi.refapp.utilities.PropertiesUtil;

import java.sql.Timestamp;

import org.apache.felix.scr.annotations.Property;
import org.osgi.service.component.ComponentContext;

public abstract class MagizhchiMongoModel extends Object {

  private String _id;

  private Timestamp tsLastModified;
  
  private Timestamp tsTimeCreated;

  public abstract String getCollectionName();
  
  private final static String PERSISTENCE_SERVICE_ID = "persistence.service.id";
  
  private static final String DEFAULT_PERSISTENCE_SERVICE_ID_VALUE = "mongo";
  
  @Property(name = PERSISTENCE_SERVICE_ID)
  private static String persistenceId;
  
  protected void activate(final ComponentContext ctx){
    MagizhchiMongoModel.persistenceId = PropertiesUtil.toString(ctx.getProperties().get(PERSISTENCE_SERVICE_ID), DEFAULT_PERSISTENCE_SERVICE_ID_VALUE);
  }

  public Timestamp getTsLastModified() {

    return tsLastModified;

  }
  
  public Timestamp getTsTimeCreated() {
    return tsTimeCreated;
  }

  public String getId() {
    return _id;
  }
  
  
  public void setId(String _id) {
    this._id = _id;
  }

  public void setTsLastModified(Timestamp tsLastModified) {
    this.tsLastModified = tsLastModified;
  }

  public void setTsTimeCreated(Timestamp tsTimeCreated) {
    this.tsTimeCreated = tsTimeCreated;
  }

  public static final String getPersistenceServiceId() {
    return persistenceId;
  }

}
