package in.magizhchi.nigazhchi.refapp.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;

import com.mongodb.DBObject;

public interface MongoManager<T extends MagizhchiMongoModel> {
  
  /**
   * 
   * @return
   */
  MongoCollection getMongoCollection(String clientId, String dbName, String collectionName);

  /**
   * Specifies what is the default limit
   *
   * @return
   */
  int getLimit();

  /**
   * Convenience method which returns a count of all documents in collection
   * 
   * @return
   */
  long count();

  /**
   * Returns the count of documents matching the qurey
   * 
   * @param query
   * @param parameters
   * @return
   */
  long count(String query, Object... parameters);


  /**
   * Returns a list, specifying no query parameters, applying provided limit and skip. Convenience
   * method same as calling list(0,0)
   * 
   * @return
   */
  List<T> list();

  /**
   * Returns list of documents. if you pass 10, 1 it means retrieve 10 records from first page. A
   * number less than 1 is interpreted to be 1. A negative limit defaults to 10.
   * 
   * @param limit If 0 is passed, there is no upper limit
   * @param page if 0 is passed records from first and onwards are included
   * @return
   */
  List<T> list(int limit, int page);

  /**
   * Returns list of documents. if you pass 10, 1 it means retrieve 10 records from first page. A
   * number less than 1 is interpreted to be 1. A negative limit defaults to 10.
   * 
   * @param limit If 0 is passed, there is no upper limit
   * @param page if 0 is passed records from first and onwards are included
   * @return
   */
  List<T> list(int limit, int page, String sort);

  /**
   * Returns list of documents. if you pass 10, 1 it means retrieve 10 records from first page. A
   * number less than 1 is interpreted to be 1. A negative limit defaults to 10.
   * 
   * @param limit If 0 is passed, there is no upper limit
   * @param page if 0 is passed records from first and onwards are included
   * @param query query parameter
   * @return
   */
  List<T> list(int limit, int page, String sort, String query);

  /**
   * Experimental : Returns JSON Array
   *
   * @param limit
   * @param skip
   * @param query
   * @param fields
   * @return json contains list
   */
  String listJSON(int limit, int skip, DBObject query, DBObject fields);

  /**
   * Returns an object if one exists matching the provided ObjectId
   *
   * @param id
   * @return
   */
  T find(ObjectId id);

  /**
   * return appropriate object. Cast it accordingly
   * 
   * @param id
   * @param type
   * @return
   */
  T find(ObjectId id, Class<T> type);

  /**
   * Returns an object if one exists matching the provided ObjectId
   *
   * @param id
   * @return
   */
  T find(String id, ResultHandler<T> handler);


  /**
   * Returns an object if one exists matching the provided ObjectId in String form. Before making
   * the database call, it is converted into ObjectId
   *
   * @param id
   * @return
   */
  T find(String id);

  T find(String id, String fields);

  /**
   * Saves the entity HistoryCRUD only starts on updates
   * 
   * @param entity
   */
  Object save(T entity);

  /**
   * Update object with id.
   * 
   * @param id
   * @param object
   * @return
   */
  Object update(ObjectId id, T object);

  Object update(String query, T object);

  Object update(String query, T object, Object... parameters);

  /**
   * Deletes a document based on ID. ID is converted into ObjectId. Convenience method
   *
   * @param id
   */
  void delete(String id);

  /**
   * Deletes given document
   *
   * @param id
   */
  void delete(ObjectId id);



  /**
   * Aggregate
   * 
   * @param pipeline - multiple pipelines
   * @return
   */
  List<T> aggregate(String... pipeline);

  /**
   * Aggregate - single pipeline
   * 
   * @param pipeline
   * @return
   */
  List<T> aggregate(String pipeline);

  List<T> aggregate(T obj, String... pipeline);

}
