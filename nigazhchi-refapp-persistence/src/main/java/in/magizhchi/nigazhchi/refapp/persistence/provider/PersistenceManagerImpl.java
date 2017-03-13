package in.magizhchi.nigazhchi.refapp.persistence.provider;

import in.magizhchi.nigazhchi.refapp.persistence.MagizhchiMongoModel;
import in.magizhchi.nigazhchi.refapp.persistence.PersistenceManager;
import in.magizhchi.nigazhchi.refapp.persistence.PersistenceService;
import in.magizhchi.nigazhchi.refapp.persistence.PersistenceServiceFactory;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

@Service
@Component(immediate = true, enabled = true)
public class PersistenceManagerImpl<T extends Object> implements PersistenceManager<T> {

  @Reference
  private PersistenceServiceFactory<T> persistenceServiceFactory;
  
  public PersistenceService<T> getPersistenceService(Class<T> entity) {
    
    if (entity.isAssignableFrom(MagizhchiMongoModel.class)) {

      return persistenceServiceFactory.getPersistenceService(MagizhchiMongoModel.getPersistenceServiceId());

    }

    return null;
  }

 

}
