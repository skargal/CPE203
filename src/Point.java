import java.util.*;

import processing.core.PImage;

import java.util.List;

/*final class Point
{
   public int x;
   public int y;

   private final String QUAKE_ID = "quake";
   private final int QUAKE_ACTION_PERIOD = 1100;
   private final int QUAKE_ANIMATION_PERIOD = 100;

   // Below Variables were added for A*
   public int INFINITE = 2147483647;
   private int g = INFINITE; // Distance from Start
   private int h; // Heuristic Distance
   private int f = INFINITE; // Total Distance

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
              ((Point)other).x == this.x &&
              ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public int distanceSquared(Point p1, Point p2)
   {
      int deltaX = p1.x - p2.x;
      int deltaY = p1.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }






   // Calculates nearest entity from list using pythagorean theorem
   public Optional<Entity> nearestEntity(List<Entity> entities)
   {
      if (entities.isEmpty()) {
         return Optional.empty();
      }
      else {
         // takes first entity and finds its distance from this
         Entity nearest = entities.get(0);
         int nearestDistance = distanceSquared(nearest.getPosition(), this);

         // compares distance of nearest entity to this with all same entities until nearest found
         for (Entity other : entities) {
            int otherDistance = distanceSquared(other.getPosition(), this);

            if (otherDistance < nearestDistance) {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   public boolean adjacent(Point p2)
   {
      return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) ||
              (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
   }

   // Functions Below were added for A*

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public int getG() {
      return g;
   }

   public void setG(int newG) {
      this.g = newG;
   }

   public int getH() {
      return h;
   }

   public void setH(int newH) {
      this.h = newH;
   }

   public int getF() {
      return f;
   }

   public void setF(int newF) {
      this.f = newF;
   }

   public int calculateHeuristic(Point goal) { // Meant to be set to h
      return Math.abs(goal.getY() - this.getY()) + Math.abs(goal.getX() - this.getX());
   }

}*/

import java.util.LinkedList;
        import java.util.List;
        import java.util.Optional;

final class Point {
   private static final int ORE_REACH = 1;
   public final int x;
   public final int y;

   private final String QUAKE_ID = "quake";
   private final int QUAKE_ACTION_PERIOD = 1100;
   private final int QUAKE_ANIMATION_PERIOD = 100;

   // Internal heruristic variables used for the A* search algorithm
   private Point cameFrom;
   private double h;
   private double g = 2147483647;

   public Point(int x, int y) {
      this.x = x;
      this.y = y;
   }

  /* public Optional<Entity> findNearest(WorldModel world, String entityClassName) {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : world.entities) {
         if (entity.getClass().getSimpleName() == entityClassName) {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType);
   }*/

   public boolean isOccupied(WorldModel world) {
      return withinBounds(world) &&
              world.getOccupancyCell(this) != null;
   }

   public boolean withinBounds(WorldModel world) {
      return y >= 0 && y < world.numRows &&
              x >= 0 && x < world.numCols;
   }

   public Optional<Point> findOpenAround(WorldModel world) {
      for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
         for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
            Point newPt = new Point(x + dx, y + dy);
            if (newPt.withinBounds(world) &&
                    !newPt.isOccupied(world)) {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public boolean adjacent(Point p2) {
      return (x == p2.x && Math.abs(y - p2.y) == 1) ||
              (y == p2.y && Math.abs(x - p2.x) == 1);
   }

   public String toString() {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other) {
      return other instanceof Point &&
              ((Point) other).x == this.x &&
              ((Point) other).y == this.y;
   }

   public int hashCode() {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public  double distance(Point p2) {
      return Math.hypot(x-p2.x, y-p2.y);
   }

   public int distanceSquared(Point p2) {
      int deltaX = x - p2.x;
      int deltaY = y - p2.y;
      return deltaX * deltaX + deltaY * deltaY;
   }

   public Optional<Entity> nearestEntity(List<Entity> entities) {
      if (entities.isEmpty()) {
         return Optional.empty();
      } else {
         Entity nearest = entities.get(0);
         int nearestDistance = nearest.getPosition().distanceSquared(this);

         for (Entity other : entities) {
            int otherDistance = other.getPosition().distanceSquared(this);

            if (otherDistance < nearestDistance) {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }


   //Internal methods used for the A* search algorithim
   public Point getCameFrom() { return cameFrom;}

   public void setCameFrom(Point cameFrom) {this.cameFrom = cameFrom;}

   public double getH() {return h;}

   public void setH(double h) {this.h = h;}

   public double getG() {return g;}

   public void setG(double g) {this.g = g;}

   public double getHeuristic() {return g + h;}

   public Obstacle createObstacle(String id, List<PImage> images)
   {
      return new Obstacle(id, this, images, 0, 0, 0, 0);
   }


   public Space_Frog createSpace_Frog(String id, int resourceLimit,
   int actionPeriod, int animationPeriod,
   List<PImage> images)
   {
      return new Space_Frog(id, this, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }

   public Portal createPortal(String id, int resourceLimit,
                            int actionPeriod, int animationPeriod,
                            List<PImage> images)
   {
      return new Portal(id, this, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }

  /* public Crosshair createCrosshair(String id, Point position, List<PImage> images, int resourceLimit,
                                   int resourceCount, int actionPeriod, int animationPeriod)
   {
      return new Crosshair(id, this, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }*/

   public Ore createOre(String id, int actionPeriod,
                        List<PImage> images)
   {
      return new Ore(id, this, images, 0, 0, actionPeriod, 0);
   }

   public Quake createQuake(List<PImage> images)
   {
      return new Quake(QUAKE_ID, this, images, 0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
   }

   public Vein createVein(String id, int actionPeriod,
                          List<PImage> images)
   {
      return new Vein(id, this, images, 0, 0, actionPeriod, 0);
   }


   public Blacksmith createBlacksmith(String id,
                                      List<PImage> images)
   {
      return new Blacksmith(id, this, images, 0, 0, 0, 0);
   }

   public Miner_Full createMinerFull(String id, int resourceLimit,
                                     int actionPeriod, int animationPeriod,
                                     List<PImage> images)
   {
      return new Miner_Full(id, this, images,
              resourceLimit, resourceLimit, actionPeriod, animationPeriod);
   }

   public Miner_Not_Full createMiner_Not_FullFull(String id, int resourceLimit,
                                            int actionPeriod, int animationPeriod,
                                            List<PImage> images)
   {
      return new Miner_Not_Full(id, this, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }



}