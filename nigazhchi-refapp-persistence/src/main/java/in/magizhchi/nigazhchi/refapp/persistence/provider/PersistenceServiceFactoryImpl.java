package in.magizhchi.nigazhchi.refapp.persistence.provider;

import in.magizhchi.nigazhchi.refapp.persistence.PersistenceService;
import in.magizhchi.nigazhchi.refapp.persistence.PersistenceServiceFactory;

import java.util.HashMap;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.References;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.cm.ConfigurationAdmin;

@Component
@Service({PersistenceServiceFactory.class})
@References({@Reference(cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC,
                        referenceInterface = PersistenceService.class, name = "PersistenceService")})
public class PersistenceServiceFactoryImpl<T extends Object> implements PersistenceServiceFactory<T> {
  
  private final static String KEY_PROPERTY = "persistence.service.id";

  @Reference
  private ConfigurationAdmin configAdmin;

  private final Map<String, PersistenceService<T>> persistenceServiceMap = new HashMap<String, PersistenceService<T>>();

 
  public PersistenceService<T> getPersistenceService(String persistenceServiceId) {
    return persistenceServiceMap.get(persistenceServiceId);
  }

  protected void bindPersistenceService(final PersistenceService<T> persistenceService,
      final Map<String, Object> properties) {
    synchronized (this.persistenceServiceMap) {
      String key = (String) properties.get(KEY_PROPERTY);

      persistenceServiceMap.put(key, persistenceService);

    }
  }

  protected void unbindPersistenceService(final PersistenceService<T> persistenceService) {
    synchronized (this.persistenceServiceMap) { 
      persistenceServiceMap.remove(persistenceService);
    }
  }


}
