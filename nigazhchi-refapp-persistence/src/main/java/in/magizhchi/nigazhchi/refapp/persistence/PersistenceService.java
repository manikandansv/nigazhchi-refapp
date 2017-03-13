package in.magizhchi.nigazhchi.refapp.persistence;



public interface PersistenceService<T> {

  T create(T entity);

  T update(T object, long id);

  T update(T object, String id);
  
  T update(T object, Object id);

  T load(Class<? extends T> entity, long id);

  T load(Class<? extends T> entity, String id);

  void remove(T object, long id);

  void remove(T object, String id);
  
  void remove(T object, Object id);

  boolean contains(T o);

  String getPersistenceServiceId();

}
