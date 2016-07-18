package eak;

public interface CoordinateStorage {
  void selectPersonById(long id) throws StorageException;

  void prepareCoordinate(double latitude, double longitude, double height) throws StorageException;

  int saveCoordinates() throws StorageException;
}
