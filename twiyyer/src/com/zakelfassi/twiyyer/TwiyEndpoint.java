package com.zakelfassi.twiyyer;

import com.zakelfassi.twiyyer.PMF;

import com.google.api.server.spi.config.Api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "twiyyer")
public class TwiyEndpoint {

  /**
   * This method lists all the entities inserted in datastore.
   * It uses HTTP GET method.
   *
   * @return List of all entities persisted.
   */
  @SuppressWarnings({"cast", "unchecked"})
  public List<Twiy> listTwiy() {
    PersistenceManager mgr = getPersistenceManager();
    List<Twiy> result = new ArrayList<Twiy>();
    try{
      Query query = mgr.newQuery(Twiy.class);
      for (Object obj : (List<Object>) query.execute()) {
        result.add(((Twiy) obj));
      }
    } finally {
      mgr.close();
    }
    return result;
  }

  /**
   * This method gets the entity having primary key id. It uses HTTP GET method.
   *
   * @param id the primary key of the java bean.
   * @return The entity with primary key id.
   */
  public Twiy getTwiy(@Named("id") Long id) {
    PersistenceManager mgr = getPersistenceManager();
    Twiy twiy  = null;
    try {
      twiy = mgr.getObjectById(Twiy.class, id);
    } finally {
      mgr.close();
    }
    return twiy;
  }

  /**
   * This inserts the entity into App Engine datastore.
   * It uses HTTP POST method.
   *
   * @param twiy the entity to be inserted.
   * @return The inserted entity.
   */
  public Twiy insertTwiy(Twiy twiy) {
    PersistenceManager mgr = getPersistenceManager();
    try {
      mgr.makePersistent(twiy);
    } finally {
      mgr.close();
    }
    return twiy;
  }

  /**
   * This method is used for updating a entity.
   * It uses HTTP PUT method.
   *
   * @param twiy the entity to be updated.
   * @return The updated entity.
   */
  public Twiy updateTwiy(Twiy twiy) {
    PersistenceManager mgr = getPersistenceManager();
    try {
      mgr.makePersistent(twiy);
    } finally {
      mgr.close();
    }
    return twiy;
  }

  /**
   * This method removes the entity with primary key id.
   * It uses HTTP DELETE method.
   *
   * @param id the primary key of the entity to be deleted.
   * @return The deleted entity.
   */
  public Twiy removeTwiy(@Named("id") Long id) {
    PersistenceManager mgr = getPersistenceManager();
     Twiy twiy  = null;
    try {
      twiy = mgr.getObjectById(Twiy.class, id);
      mgr.deletePersistent(twiy);
    } finally {
      mgr.close();
    }
    return twiy;
  }

  private static PersistenceManager getPersistenceManager() {
    return PMF.get().getPersistenceManager();
  }

}
