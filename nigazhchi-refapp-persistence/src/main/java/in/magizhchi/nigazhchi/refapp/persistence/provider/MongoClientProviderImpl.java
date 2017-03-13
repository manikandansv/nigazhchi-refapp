package in.magizhchi.nigazhchi.refapp.persistence.provider;

import in.magizhchi.nigazhchi.refapp.persistence.MongoClientProvider;
import in.magizhchi.nigazhchi.refapp.utilities.PropertiesUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.Tag;
import com.mongodb.TagSet;
import com.mongodb.WriteConcern;

@Service(value = MongoClientProvider.class)
@Component(enabled = true, immediate = true, metatype = true)
public class MongoClientProviderImpl implements MongoClientProvider {

  @Property(name = PROP_CLIENT_ID, label = "Client Id")
  private String clientId;

  @Property(name = PROP_URI, label = "Client URI")
  private String uri;

  @Property(name = PROP_ALWAYS_USE_MBEANS, label = "whether JMX beans registered by the driver should always be MBeans")
  private boolean alwaysUseMBeans;

  @Property(name = PROP_CONNECTIONS_PER_HOST,
            label = "maximum number of connections allowed per host for this Mongo instance")
  private Integer connectionsPerHost;

  @Property(name = PROP_CONNECT_TIMEOUT, label = "connection timeout in milliseconds")
  private Integer connectionTimeout;

  @Property(name = PROP_CURSOR_FINALIZER_ENABLED, label = "Sets whether cursor finalizers are enabled")
  private boolean cursorFinalizerEnabled;

  @Property(name = PROP_DESCRIPTION, label = "The description for Mongo instances created with these options")
  private String description;

  @Property(name = PROP_HEARTBEAT_CONNECT_TIMEOUT,
            label = "Sets the connect timeout for connections used for the cluster heartbeat")
  private Integer heartbeatConnectTimeout;

  @Property(name = PROP_HEARTBEAT_FREQUENCY, label = "Sets the heartbeat frequency")
  private Integer heartbeatFrequency;

  @Property(name = PROP_HEARTBEAT_SOCKET_TIMEOUT,
            label = "Sets the socket timeout for connections used for the cluster heartbeat")
  private Integer heartbeatSocketTimeout;

  @Property(name = PROP_LOCAL_THRESHOLD, label = "Client Id")
  private Integer localThreshold;

  @Property(name = PROP_MAX_CONNECTION_IDLE_TIME, label = "Client Id")
  private Integer maxConnectionIdleTime;

  @Property(name = PROP_MAX_CONNECTION_LIFE_TIME, label = "Client Id")
  private Integer maxConnectionLifeTime;

  @Property(name = PROP_MAX_WAIT_TIME, label = "Client Id")
  private Integer maxWaitTime;

  @Property(name = PROP_MIN_CONNECTIONS_PER_HOST, label = "Client Id")
  private Integer minConnectionsPerHost;

  @Property(name = PROP_MIN_HEARTBEAT_FREQUENCY, label = "Client Id")
  private Integer minHeartbeatFrequency;

  @Property(name = PROP_READ_PREFERENCE_TYPE, label = "Client Id")
  private Integer readPreferenceType;

  @Property(name = PROP_READ_PREFERENCE_TAGS, label = "Client Id")
  private String[] readPreferenceTags;

  @Property(name = PROP_REQUIRED_REPLICA_SET_NAME, label = "Client Id")
  private String requiredReplicaSetName;

  @Property(name = PROP_SERVER_SELECTION_TIMEOUT, label = "Client Id")
  private Integer serverSelectionTimeout;

  @Property(name = PROP_SOCKET_KEEP_ALIVE, label = "Client Id")
  private boolean socketKeepAlive;

  @Property(name = PROP_SOCKET_TIMEOUT, label = "Client Id")
  private Integer socketTimeout;

  @Property(name = PROP_SSL_ENABLED, label = "Client Id")
  private boolean sslEnabled;

  @Property(name = PROP_SSL_INVALID_HOST_NAME_ALLOWED, label = "Client Id")
  private boolean sslInvalidHostNameAllowed;

  @Property(name = PROP_THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER, label = "Client Id")
  private Integer threadsAllowedToBlockForConnectionMultiplier;

  @Property(name = PROP_WRITECONCERNW, label = "Client Id")
  private Integer writeConcernW;

  @Property(name = PROP_WTIMEOUT, label = "Client Id")
  private Integer writeConcernWtimeout;

  @Property(name = PROP_FSYNC, label = "Client Id")
  private boolean writeConcernFsync;

  @Property(name = PROP_J, label = "Client Id")
  private boolean writeConcernJ;

  private MongoClient mongoClient;

  private List<String> uris = new ArrayList<String>();

  @Activate
  protected void activate(ComponentContext ctx) {

    configClient(ctx);

    boolean isValid = validateURI(uri, uris);

    if (isValid) {

      MongoClientOptions options = createMongoClientOptions();

      mongoClient = createMongoClient(uris, options);
    }

  }
  
  @Deactivate
  protected void deactivate(ComponentContext ctx){
    
    if (mongoClient != null){
      
      mongoClient.close();
      
    }
    
  }

  @SuppressWarnings("deprecation")
  private MongoClientOptions createMongoClientOptions() {
    MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();

    optionsBuilder.alwaysUseMBeans(this.alwaysUseMBeans);
    optionsBuilder.connectionsPerHost(this.connectionsPerHost);
    optionsBuilder.connectTimeout(this.connectionTimeout);
    optionsBuilder.cursorFinalizerEnabled(this.cursorFinalizerEnabled);
    optionsBuilder.description(this.description);
    optionsBuilder.heartbeatConnectTimeout(this.heartbeatConnectTimeout);
    optionsBuilder.heartbeatFrequency(this.heartbeatFrequency);
    optionsBuilder.heartbeatSocketTimeout(this.heartbeatSocketTimeout);
    optionsBuilder.localThreshold(this.localThreshold);
    optionsBuilder.maxConnectionIdleTime(this.maxConnectionIdleTime);
    optionsBuilder.maxConnectionLifeTime(this.maxConnectionLifeTime);
    optionsBuilder.maxWaitTime(this.maxWaitTime);
    optionsBuilder.minConnectionsPerHost(this.minConnectionsPerHost);
    optionsBuilder.minHeartbeatFrequency(this.minHeartbeatFrequency);
    optionsBuilder.serverSelectionTimeout(this.serverSelectionTimeout);
    optionsBuilder.socketKeepAlive(this.socketKeepAlive);
    optionsBuilder.socketTimeout(this.socketTimeout);
    optionsBuilder.sslEnabled(this.sslEnabled);
    optionsBuilder.sslInvalidHostNameAllowed(this.sslInvalidHostNameAllowed);
    optionsBuilder.threadsAllowedToBlockForConnectionMultiplier(this.threadsAllowedToBlockForConnectionMultiplier);
    WriteConcern writeConcern =
        new WriteConcern(this.writeConcernW, this.writeConcernWtimeout, this.writeConcernFsync, this.writeConcernJ);
    optionsBuilder.writeConcern(writeConcern);

    if (!this.requiredReplicaSetName.isEmpty()) optionsBuilder.requiredReplicaSetName(this.requiredReplicaSetName);

    List<Tag> tags = new ArrayList<>();

    if (this.readPreferenceTags != null) {
      for (String tag : this.readPreferenceTags) {
        String[] elements = tag.split("=");
        tags.add(new Tag(elements[0], elements[1]));
      }
    }

    TagSet tagSet = new TagSet(tags);

    switch (this.readPreferenceType) {
      case 1:
        optionsBuilder.readPreference(ReadPreference.nearest(tagSet));
        break;
      case 2:
        optionsBuilder.readPreference(ReadPreference.primary());
        break;
      case 3:
        optionsBuilder.readPreference(ReadPreference.primaryPreferred(tagSet));
        break;
      case 4:
        optionsBuilder.readPreference(ReadPreference.secondary(tagSet));
        break;
      case 5:
        optionsBuilder.readPreference(ReadPreference.secondaryPreferred(tagSet));
        break;
    }

    return optionsBuilder.build();
  }

  private MongoClient createMongoClient(List<String> uriList, MongoClientOptions options) {

    String currentURI = null;

    List<ServerAddress> serverAddresses = new ArrayList<>(uris.size());
    try {

      for (String uri : uriList) {

        currentURI = uri;

        serverAddresses.add(createServerAddress(currentURI));

      }

      if (serverAddresses.size() == 1)
        return new MongoClient(serverAddresses.get(0), options);
      else
        return new MongoClient(serverAddresses, options);
    } catch (UnknownHostException | URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }

  private ServerAddress createServerAddress(String uriProperty) throws URISyntaxException, UnknownHostException {
    URI uri = new URI(uriProperty);
    int port = uri.getPort();
    ServerAddress serverAddress =
        port == -1 ? new ServerAddress(uri.getHost()) : new ServerAddress(uri.getHost(), uri.getPort());
    return serverAddress;
  }

  private void configClient(ComponentContext ctx) {
    this.clientId = PropertiesUtil.toString(ctx.getProperties().get(PROP_CLIENT_ID), StringUtils.EMPTY);
    this.alwaysUseMBeans = PropertiesUtil.toBoolean(ctx.getProperties().get(PROP_ALWAYS_USE_MBEANS), false);
    this.connectionsPerHost = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_CONNECTIONS_PER_HOST), 100);
    this.connectionTimeout = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_CONNECT_TIMEOUT), 100);
    this.cursorFinalizerEnabled = PropertiesUtil.toBoolean(ctx.getProperties().get(PROP_CURSOR_FINALIZER_ENABLED), true);
    this.description = PropertiesUtil.toString(ctx.getProperties().get(PROP_DESCRIPTION), StringUtils.EMPTY);
    this.heartbeatConnectTimeout = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_HEARTBEAT_CONNECT_TIMEOUT), 20000);
    this.heartbeatFrequency = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_HEARTBEAT_FREQUENCY), 10000);
    this.heartbeatSocketTimeout = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_HEARTBEAT_SOCKET_TIMEOUT), 20000);
    this.localThreshold = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_LOCAL_THRESHOLD), 15);
    this.maxConnectionIdleTime = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_MAX_CONNECTION_IDLE_TIME), 0);
    this.maxConnectionLifeTime = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_MAX_CONNECTION_LIFE_TIME), 0);
    this.maxWaitTime = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_MAX_WAIT_TIME), 120000);
    this.minConnectionsPerHost = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_MIN_CONNECTIONS_PER_HOST), 0);
    this.minHeartbeatFrequency = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_MIN_HEARTBEAT_FREQUENCY), 500);
    this.readPreferenceTags =
        PropertiesUtil.toStringArray(ctx.getProperties().get(PROP_READ_PREFERENCE_TAGS), new String[] {});
    this.readPreferenceType = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_READ_PREFERENCE_TYPE), 0);
    this.requiredReplicaSetName =
        PropertiesUtil.toString(ctx.getProperties().get(PROP_REQUIRED_REPLICA_SET_NAME), StringUtils.EMPTY);
    this.serverSelectionTimeout = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_SERVER_SELECTION_TIMEOUT), 30000);
    this.socketKeepAlive = PropertiesUtil.toBoolean(ctx.getProperties().get(PROP_SOCKET_KEEP_ALIVE), false);
    this.socketTimeout = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_SOCKET_TIMEOUT), 0);
    this.sslEnabled = PropertiesUtil.toBoolean(ctx.getProperties().get(PROP_SSL_ENABLED), false);
    this.sslInvalidHostNameAllowed =
        PropertiesUtil.toBoolean(ctx.getProperties().get(PROP_SSL_INVALID_HOST_NAME_ALLOWED), false);
    this.threadsAllowedToBlockForConnectionMultiplier =
        PropertiesUtil.toInteger(ctx.getProperties().get(PROP_THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER), 5);
    this.uri = PropertiesUtil.toString(ctx.getProperties().get(PROP_URI), StringUtils.EMPTY);
    this.writeConcernFsync = PropertiesUtil.toBoolean(ctx.getProperties().get(PROP_FSYNC), false);
    this.writeConcernJ = PropertiesUtil.toBoolean(ctx.getProperties().get(PROP_J), false);
    this.writeConcernW = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_WRITECONCERNW), 1);
    this.writeConcernWtimeout = PropertiesUtil.toInteger(ctx.getProperties().get(PROP_WTIMEOUT), 0);
  }

  @Override
  public MongoClient getMongoClient() {
    return mongoClient;
  }

  @Override
  public String getClientId() {
    return clientId;
  }

  @Override
  public String[] getURIs() {

    String[] str = null;

    if (uris != null && !uris.isEmpty()) {

      str = new String[uris.size()];

      return uris.toArray(str);

    }

    return str;

  }


  private boolean validateURI(String value, Collection<String> uris) {

    if (StringUtils.isNotEmpty(value)) {
      // The regex \s matches whitepsace. The extra \ is needed because of how
      // it's treated in java
      // strings. The split is done on any number of whitespace chars followed by
      // a comma followed by
      // any number of whitespace chars. What is left is the URI(s).

      for (String targetURI : value.split("\\s*,\\s*")) {
        String uri = targetURI.trim();
        String[] segments = uri.split("/");

        if (!uri.startsWith("mongodb://") || uri.endsWith("/") || segments.length < 3 || segments.length > 5)
          return false;

        if (uris != null) uris.add(uri);
      }
    } else {
      return false;
    }

    return true;

  }

}
