package in.magizhchi.nigazhchi.refapp.persistence;


public interface PersistenceManager<T extends Object> {

  PersistenceService<T> getPersistenceService(Class<T> entity);

}
