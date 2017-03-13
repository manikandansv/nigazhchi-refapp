package in.magizhchi.nigazhchi.refapp.persistence.provider;

import java.util.List;

import in.magizhchi.nigazhchi.refapp.persistence.MagizhchiMongoModel;
import in.magizhchi.nigazhchi.refapp.persistence.MongoClientProvider;
import in.magizhchi.nigazhchi.refapp.persistence.MongoDatabaseProvider;
import in.magizhchi.nigazhchi.refapp.persistence.MongoDatabaseProviderFactory;
import in.magizhchi.nigazhchi.refapp.persistence.MongoManager;
import in.magizhchi.nigazhchi.refapp.utilities.PropertiesUtil;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;
import org.osgi.service.component.ComponentContext;

import com.mongodb.DB;
import com.mongodb.DBObject;

@Component(enabled = true, immediate = true)
@Service
public class MongoManagerImpl<T extends MagizhchiMongoModel> implements MongoManager<T> {

  private Class<T> m_clazz;

  @Reference
  private MongoClientProvider mongoClientProvider;

  @Reference
  private MongoDatabaseProviderFactory mongoDatabaseProviderFactory;

  public static final String DEFAULT_READ_LIMIT = "default.readLimit";

  @Property(name = DEFAULT_READ_LIMIT)
  private Integer limit;

  @Activate
  protected void activate(ComponentContext ctx) {
    this.limit = PropertiesUtil.toInteger(ctx.getProperties().get(DEFAULT_READ_LIMIT), 10);
  }

  private MongoCollection init(T obj) {
    
    String collectionName = obj.getCollectionName();

    MongoDatabaseProvider mongoDatabaseProvider = mongoDatabaseProviderFactory.getMongoDatabaseProviderByCollectionName(collectionName);

    DB db = mongoDatabaseProvider.getMongoDatabaseDeprecated(mongoClientProvider);

    MongoCollection mongoCollection = new Jongo(db).getCollection(collectionName);
    
    return mongoCollection;
  }

  protected void ensureIndex() {}

  @Override
  public MongoCollection getMongoCollection(String clientId, String dbName, String collectionName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getLimit() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public long count() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public long count(String query, Object... parameters) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public List<T> list() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<T> list(int limit, int page) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<T> list(int limit, int page, String sort) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<T> list(int limit, int page, String sort, String query) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String listJSON(int limit, int skip, DBObject query, DBObject fields) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T find(ObjectId id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T find(ObjectId id, Class<T> type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T find(String id, ResultHandler<T> handler) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T find(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T find(String id, String fields) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object save(T entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object update(ObjectId id, T object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object update(String query, T object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object update(String query, T object, Object... parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void delete(String id) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void delete(ObjectId id) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<T> aggregate(String... pipeline) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<T> aggregate(String pipeline) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<T> aggregate(T obj, String... pipeline) {
    // TODO Auto-generated method stub
    return null;
  };

//  @Override
//  public Object save(T obj) {
//    
//    MongoCollection mongoCollection = init(obj);
//
//    WriteResult saveResult = mongoCollection.save(obj);
//
//    Object saveId = null;
//
//    if (saveResult != null && saveResult.getUpsertedId() != null) {
//
//      saveId = saveResult.getUpsertedId();
//
//    }
//
//    destroy();
//    
//    return saveId;
//  }
//
//  /**
//   * Deletes a document based on ID. ID is converted into ObjectId. Convenience method
//   *
//   * @param id
//   */
//  @Override
//  public void delete(String id) {
//    
//    MongoCollection mongoCollection = init(obj);
//    
//    mongoCollection.remove(new ObjectId(id));
//    
//    destroy();
//  }
//
//  /**
//   * Deletes given document
//   *
//   * @param id
//   */
//  @Override
//  public void delete(ObjectId id) {
//    mongoCollection.remove(id);
//  }
//
//  /**
//   * Aggregate
//   *
//   * @param pipeline - multiple pipelines
//   * @return
//   */
//  @Override
//  public List<T> aggregate(String... pipeline) {
//
//    List<T> outputList = new ArrayList<T>();
//
//    int i = 0;
//    Aggregate agr = null;
//    for (String p : pipeline) {
//      if (i == 0) {
//        agr = mongoCollection.aggregate(p);
//      } else {
//        agr = agr.and(p);
//      }
//    }
//
//    ResultsIterator<T> itr = agr.as(m_clazz);
//
//    while (itr.hasNext()) {
//      outputList.add(itr.next());
//    }
//
//    return outputList;
//  }
//
//  /**
//   * Aggregate - single pipeline
//   *
//   * @param pipeline
//   * @return
//   */
//  @Override
//  public List<T> aggregate(String pipeline) {
//
//    List<T> outputList = new ArrayList<T>();
//
//    Aggregate agr = mongoCollection.aggregate(pipeline);
//
//    ResultsIterator<T> itr = agr.as(m_clazz);
//
//    while (itr.hasNext()) {
//      outputList.add(itr.next());
//    }
//
//    return outputList;
//  }
//
//  @Override
//  public int getLimit() {
//    return limit;
//  }
//
//  @Override
//  public long count() {
//    return mongoCollection.count();
//  }
//
//  @Override
//  public long count(String query, Object... parameters) {
//    return mongoCollection.count(query, parameters);
//  }
//
//  @Override
//  public List<T> list() {
//    return list(0, 0, null);
//  }
//
//  @Override
//  public List<T> list(int limit, int page) {
//    return list(limit, page, null);
//  }
//
//  @Override
//  public List<T> list(int limit, int page, String sort) {
//    if (page < 1) {
//      page = 1;
//    }
//    page = (page < 0) ? -page : page;
//    page--;
//    limit = (limit < 0) ? 10 : limit;
//    return copyIterator(mongoCollection.find().limit(limit).skip(page * limit).sort(sort).as(m_clazz).iterator());
//  }
//
//  @Override
//  public List<T> list(int limit, int page, String sort, String query) {
//    if (page < 1) {
//      page = 1;
//    }
//    page = (page < 0) ? -page : page;
//    page--;
//    limit = (limit < 0) ? 10 : limit;
//    return copyIterator(mongoCollection.find(query).limit(limit).skip(page * limit).sort(sort).as(m_clazz).iterator());
//  }
//
//  @Override
//  public String listJSON(int limit, int skip, DBObject query, DBObject fields) {
//
//    DBCollection col =
//        mongoDatabaseProvider.getMongoDatabaseDeprecated(mongoClientProvider).getCollection(this.m_collectionName);
//
//    return col.find(query, fields).limit(limit).skip(skip).toArray().toString();
//
//  }
//
//  @Override
//  public T find(ObjectId id) {
//    return mongoCollection.findOne(id).as(m_clazz);
//  }
//
//  @Override
//  public T find(ObjectId id, Class<T> type) {
//    return mongoCollection.findOne(id).as(type);
//  }
//
//  @Override
//  public T find(String id, ResultHandler<T> handler) {
//    return mongoCollection.findOne(new ObjectId(id)).map(handler);
//  }
//
//  @Override
//  public T find(String id) {
//    return mongoCollection.findOne(new ObjectId(id)).as(m_clazz);
//  }
//
//  @Override
//  public T find(String id, String fields) {
//    return mongoCollection.findOne(new ObjectId(id)).projection(fields).as(m_clazz);
//  }
//
//  @Override
//  public Object update(ObjectId id, T object) {
//
//    WriteResult updatedResult = mongoCollection.update(id).with(object);
//
//    Object updatedId = null;
//
//    if (updatedResult != null && updatedResult.getUpsertedId() != null) {
//
//      updatedId = updatedResult.getUpsertedId();
//
//    }
//
//    return updatedId;
//
//  }
//
//  @Override
//  public Object update(String query, T object) {
//
//    WriteResult updatedResult = mongoCollection.update(query).with(object);
//
//    Object updatedId = null;
//
//    if (updatedResult != null && updatedResult.getUpsertedId() != null) {
//
//      updatedId = updatedResult.getUpsertedId();
//
//    }
//
//    return updatedId;
//
//  }
//
//  @Override
//  public Object update(String query, T object, Object... parameters) {
//
//    WriteResult updatedResult = mongoCollection.update(query, parameters).with(object);
//
//    Object updatedId = null;
//
//    if (updatedResult != null && updatedResult.getUpsertedId() != null) {
//
//      updatedId = updatedResult.getUpsertedId();
//
//    }
//
//    return updatedId;
//
//  }
//
//
//  /**
//   * Converts an iterator to a List
//   *
//   * @param <T>
//   * @param iter
//   * @return
//   */
//  public static <T> List<T> copyIterator(Iterator<T> iter) {
//    List<T> copy = new ArrayList<T>();
//    while (iter.hasNext()) {
//      copy.add(iter.next());
//    }
//    return copy;
//  }
//
//  /**
//   * Aggregate
//   *
//   * @param pipeline - multiple pipelines
//   * @return
//   */
//  @SuppressWarnings("unchecked")
//  @Override
//  public List<T> aggregate(T obj, String... pipeline) {
//
//    List<T> outputList = new ArrayList<T>();
//
//    int i = 0;
//    Aggregate agr = null;
//    for (String p : pipeline) {
//      if (i == 0) {
//        agr = mongoCollection.aggregate(p);
//      } else {
//        agr = agr.and(p);
//      }
//    }
//
//    ResultsIterator<T> itr = agr.as((Class<T>) obj.getClass());
//
//    while (itr.hasNext()) {
//      outputList.add(itr.next());
//    }
//
//    return outputList;
//  }
//
//  @Override
//  public MongoCollection getMongoCollection(String clientId, String dbName, String collectionName) {
//
//    MongoDatabaseProvider mongoDatabaseProvider = mongoDatabaseProviderFactory.getMongoDatabaseProvider(dbName);
//
//    DB db = mongoDatabaseProvider.getMongoDatabaseDeprecated(mongoClientProvider);
//
//    MongoCollection mongoCollection = new Jongo(db).getCollection(collectionName);
//
//    return mongoCollection;
//  }

}
