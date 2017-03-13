package in.magizhchi.nigazhchi.refapp.persistence.provider;

import in.magizhchi.nigazhchi.refapp.persistence.MongoDatabaseProvider;
import in.magizhchi.nigazhchi.refapp.persistence.MongoDatabaseProviderFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.References;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.cm.ConfigurationAdmin;


@Component
@Service({MongoDatabaseProviderFactory.class})
@References({@Reference(cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC,
                        referenceInterface = MongoDatabaseProvider.class, name = "MongoDatabaseProvider")})
public class MongoDatabaseProviderFactoryImpl implements MongoDatabaseProviderFactory {

  private final static String KEY_PROPERTY = "mongodatabaseprovider.property.dbName";

  @Reference
  private ConfigurationAdmin configAdmin;

  private final Map<String, MongoDatabaseProvider> mongoDatabaseProviderMap =
      new HashMap<String, MongoDatabaseProvider>();

  private final List<MongoDatabaseProvider> mongoDatabaseProviders = new ArrayList<MongoDatabaseProvider>();

  @Override
  public MongoDatabaseProvider getMongoDatabaseProviderByDBName(String dbName) {
    return mongoDatabaseProviderMap.get(dbName);
  }

  protected void bindMongoDatabaseProvider(final MongoDatabaseProvider mongoDatabaseProvider,
      final Map<String, Object> properties) {
    synchronized (this.mongoDatabaseProviderMap) {
      String key = (String) properties.get(KEY_PROPERTY);

      mongoDatabaseProviderMap.put(key, mongoDatabaseProvider);

      mongoDatabaseProviders.add(mongoDatabaseProvider);

    }
  }

  protected void unbindMongoDatabaseProvider(final MongoDatabaseProvider mongoDatabaseProvider) {
    synchronized (this.mongoDatabaseProviderMap) {
      mongoDatabaseProviderMap.remove(mongoDatabaseProvider);

      mongoDatabaseProviders.remove(mongoDatabaseProvider);
    }
  }

  @Override
  public List<MongoDatabaseProvider> getMongoDatabaseProviders() {
    return mongoDatabaseProviders;
  }

  @Override
  public MongoDatabaseProvider getMongoDatabaseProviderByCollectionName(String collectionName) {

    MongoDatabaseProvider mongoDatabaseProvider = null;

    if (mongoDatabaseProviders != null && !mongoDatabaseProviders.isEmpty()) {

      for (MongoDatabaseProvider provider : mongoDatabaseProviders) {

        List<String> collections = Arrays.asList(provider.getCollections());

        if (collections.contains(collectionName)) {

          mongoDatabaseProvider = provider;

          break;

        }

      }

    }

    return mongoDatabaseProvider;

  }

}
