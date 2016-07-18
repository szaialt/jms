package eak;
import java.util.Date;
import eak.entity.*;

public interface StatelessPersonStorage {

  String getPersonById(long id);

  java.util.List<Long> getPeopleIds();

  long addPerson(String firstName, String lastName, Date birthDate, BloodGroup bloodGroup) throws StorageException;

  boolean removePersonById(long id);

}
