package eak;

public interface UpdateStatistics {
  void coordinatesAdded(long personId, long numberOfNewCoorinates);
  void personRemoved(long personId);
}