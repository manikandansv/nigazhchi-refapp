package in.magizhchi.nigazhchi.refapp.persistence;

public interface PersistenceServiceFactory<T extends Object> {

  PersistenceService<T> getPersistenceService(String persistenceServiceId);
  
}
