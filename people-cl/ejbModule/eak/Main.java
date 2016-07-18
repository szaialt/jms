package eak;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.text.ParseException;
import java.util.Date;

import eak.entity.*;

public class Main {
  public static void main(String[] args) throws NamingException {
    // Lookup the beans with JNDI from this standalone client.
	// (The handling of NamingExceptions is omitted for this example.)
	// The initial context setup is done by the gf-client.jar,
	// which is added to the project's build path.
	Context ctx = new InitialContext();
	StatelessPersonStorage ps = (StatelessPersonStorage) ctx
				.lookup("java:global/people-app/people-ejb/PersonStorageBean!eak.StatelessPersonStorage");

   	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    Date date1 = null;
    Date date2 = null;    
    Date date3 = null;    
    Date date4 = null;    
    try {
    date1 = sdf.parse("1996-10-18");
    date2 = sdf.parse("1997-08-13");
    date3 = sdf.parse("1995-05-05");
    date4 = sdf.parse("1899-07-15");
    long a = ps.addPerson("Amália", "Mindlevery", date1, BloodGroup.A);  
    long b = ps.addPerson("Beáta", "Mindlevery", date2, BloodGroup.B);  
    long c = ps.addPerson("Cecília", "Mindlevery", date3, BloodGroup.AB);  
    long d = 0;
    try {
       d = ps.addPerson("Detti Mama", "Mindlevery", date4, BloodGroup.Zero); 
    }
    catch (StorageException ex){
      System.out.println(ex);
    }
    java.util.List<Long> ids = ps.getPeopleIds();
    for (int i = 0; i < ids.size(); i++){
       System.out.println("Azonosító: "+ids.get(i));
    }
    String name = ps.getPersonById(a);
    System.out.println(name);
    ps.removePersonById(c);
    try {
       ps.removePersonById(d);
    }
    catch (Exception ex){
      System.out.println(ex);
    }
    
	CoordinateStorage cs = (CoordinateStorage) ctx
				.lookup("java:global/people-app/people-ejb/CoordinateStorageBean!eak.CoordinateStorage");
    try {cs.prepareCoordinate(33.01, 21.3, 40.0);}
    catch (Exception ex){System.out.println(ex);}
    cs.selectPersonById(a);
    cs.prepareCoordinate(45.3, 22.6, 85.3);
    cs.prepareCoordinate(56.4, 42.1, -41.12);
    cs.selectPersonById(b);
    cs.prepareCoordinate(35.6, 84.1, 34.32);
    cs.prepareCoordinate(76.4, 81.34, 51.23);
    long b1 = cs.saveCoordinates();
    System.out.println("Beátának "+b1+"koordinátája van.");
	CoordinateStorage cs2 = (CoordinateStorage) ctx
				.lookup("java:global/people-app/people-ejb/CoordinateStorageBean!eak.CoordinateStorage");
    cs2.selectPersonById(a);
    cs2.prepareCoordinate(34.2, 56.23, 30.3);
    cs2.prepareCoordinate(63.4, 12.52, 76.38);
    cs2.prepareCoordinate(87.9, 52.64, 148.71);
    cs2.prepareCoordinate(56.3, 27.3, 54.18);
    long a1 = cs2.saveCoordinates();
    System.out.println("Amáliának "+a1+"koordinátája van.");
	CoordinateStorage cs3 = (CoordinateStorage) ctx
				.lookup("java:global/people-app/people-ejb/CoordinateStorageBean!eak.CoordinateStorage");
    cs3.selectPersonById(b);
    cs3.prepareCoordinate(56.4, 67.7, 84.45);
    cs3.prepareCoordinate(67.3, 27.82, 43.96);
    long b2 = cs3.saveCoordinates();
    System.out.println("Beátának "+b2+"koordinátája van.");
    try {cs3.prepareCoordinate(56.6, 61.64, 37.69);}
    catch (Exception ex){System.out.println(ex);}
	finally {ctx.close();}
    } catch (StorageException ex){System.out.println(ex);}
    catch (ParseException ex){System.out.println(ex);}
	finally {ctx.close();}

 }
}
