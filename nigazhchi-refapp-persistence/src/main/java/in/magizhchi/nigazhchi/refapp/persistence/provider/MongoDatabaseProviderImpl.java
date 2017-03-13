package in.magizhchi.nigazhchi.refapp.persistence.provider;

import in.magizhchi.nigazhchi.refapp.persistence.MongoClientProvider;
import in.magizhchi.nigazhchi.refapp.persistence.MongoDatabaseProvider;
import in.magizhchi.nigazhchi.refapp.utilities.PropertiesUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;

@Service(value = MongoDatabaseProvider.class, serviceFactory = true)
@Component(enabled = true, immediate = true, configurationFactory = true, policy = ConfigurationPolicy.REQUIRE,
           metatype = true)
public class MongoDatabaseProviderImpl implements MongoDatabaseProvider {

  @Property(name = PROP_ALIAS, label = "Database Alias")
  private String databaseAlias;

  @Property(name = PROP_DATABASE_NAME, label = "Database Name")
  private String databaseName;

  @Property(name = PROP_USERNAME, label = "Database UserName")
  private String userName;

  @Property(name = PROP_PASSWORD, label = "Database Password")
  private String password;

  @Property(name = PROP_CLIENT_FILTER, label = "MongoDB client reference filter")
  private String clientFilter;
  
  @Property(name = PROP_COLLECTIONS, label = "Collections in this DB")
  private String[] collections;

  @Activate
  protected void activate(ComponentContext ctx) {
    this.databaseAlias = PropertiesUtil.toString(ctx.getProperties().get(PROP_ALIAS), StringUtils.EMPTY);
    this.databaseName = PropertiesUtil.toString(ctx.getProperties().get(PROP_DATABASE_NAME), StringUtils.EMPTY);
    this.userName = PropertiesUtil.toString(ctx.getProperties().get(PROP_USERNAME), StringUtils.EMPTY);
    this.password = PropertiesUtil.toString(ctx.getProperties().get(PROP_PASSWORD), StringUtils.EMPTY);
    this.clientFilter = PropertiesUtil.toString(ctx.getProperties().get(PROP_CLIENT_FILTER), StringUtils.EMPTY);
    this.collections = PropertiesUtil.toStringArray(ctx.getProperties().get(PROP_COLLECTIONS), new String[]{});
  }

  @Override
  public MongoDatabase getMongoDatabase(MongoClientProvider mongoClientProvider) {

    return mongoClientProvider.getMongoClient().getDatabase(databaseName);

  }

  @SuppressWarnings("deprecation")
  @Override
  public DB getMongoDatabaseDeprecated(MongoClientProvider mongoClientProvider) {

    return mongoClientProvider.getMongoClient().getDB(databaseName);

  }


  @Override
  public String getDatabaseAlias() {
    return databaseAlias;
  }

  @Override
  public String getDatabaseName() {
    return databaseName;
  }

  @Override
  public String getUserName() {
    return userName;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getClientFilter() {
    return clientFilter;
  }

  @Override
  public String[] getCollections(){
    return collections;
  }


}
