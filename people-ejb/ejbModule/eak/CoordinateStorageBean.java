package eak;
import eak.entity.*;

import java.util.List;
import java.util.Date;

import javax.ejb.*;
import javax.persistence.*;

@Stateful
@Remote(CoordinateStorage.class)
public class CoordinateStorageBean implements CoordinateStorage {

	@PersistenceContext
	private EntityManager em;

  private long id;
  private List<GpsCoordinate> coords;

	@EJB(lookup="UpdateStatisticsBroadcasterBean")
	UpdateStatistics cstat;
	
    public Person getPersonById(long id){
      Person person = em.find(Person.class, id);
      return person;
    }
	
    // Visszaadja a megadott azonosítójú személyt.
    // Ha nincs találat, null-t ad vissza.


    public void selectPersonById(long id) throws StorageException {
    Person somebody = getPersonById(id);
    if (somebody == null){
        this.id =  0;
    	throw new StorageException();
      }
    if (id != this.id){
      coords.clear();
    }
    this.id = id;
  }
 
    public void prepareCoordinate(double latitude, double longitude, double height) throws StorageException {
    Person somebody = getPersonById(id);
    if (somebody == null) throw new StorageException();
    GpsCoordinate coord = new GpsCoordinate();
    coord.setLatitude(latitude);
    coord.setLongitude(longitude);
    coord.setHeight(height);
    Date date = new Date();
    coord.setTime(date);
    coords.add(coord);
  }

  @Remove
  public int saveCoordinates() throws StorageException {
    Person somebody = getPersonById(id);
    if (somebody == null) throw new StorageException();
    //Végigiterálunk a koordinátákon
    //Hozzáadnak koordinátát
    for (int i = 0; i < coords.size(); i++) {
      GpsCoordinate coord = coords.get(i);
      somebody.addCoordinate(coord);
    }
    cstat.coordinatesAdded(id, coords.size());
    this.id = 0;
    this.coords.clear();
    //Lekérik a hosszat
    return somebody.getCoordinates().size();
  }
}
