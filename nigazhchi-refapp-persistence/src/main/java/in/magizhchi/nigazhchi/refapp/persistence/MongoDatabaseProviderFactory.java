package in.magizhchi.nigazhchi.refapp.persistence;

import java.util.List;

public interface MongoDatabaseProviderFactory {

  MongoDatabaseProvider getMongoDatabaseProviderByDBName(String databaseName);
  
  List<MongoDatabaseProvider> getMongoDatabaseProviders();

  MongoDatabaseProvider getMongoDatabaseProviderByCollectionName(String collectionName);
  
}
