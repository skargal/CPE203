import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import processing.core.PImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

final class WorldModel
{
   public int numRows;
   public int numCols;
   public Background background[][];
   public Entity occupancy[][];
   public Set<Entity> entities;
   private final int PROPERTY_KEY = 0;
   private final String BGND_KEY = "background";
   private final int BGND_NUM_PROPERTIES = 4;
   private final int BGND_ID = 1;
   private final int BGND_COL = 2;
   private final int BGND_ROW = 3;
   private final String MINER_KEY = "miner";
   private final int MINER_NUM_PROPERTIES = 7;
   private final int MINER_ID = 1;
   private final int MINER_COL = 2;
   private final int MINER_ROW = 3;
   private final int MINER_LIMIT = 4;
   private final int MINER_ACTION_PERIOD = 5;
   private final int MINER_ANIMATION_PERIOD = 6;
   private final String OBSTACLE_KEY = "obstacle";
   private final int OBSTACLE_NUM_PROPERTIES = 4;
   private final int OBSTACLE_ID = 1;
   private final int OBSTACLE_COL = 2;
   private final int OBSTACLE_ROW = 3;
   private final int ORE_NUM_PROPERTIES = 5;
   private final int ORE_ID = 1;
   private final int ORE_COL = 2;
   private final int ORE_ROW = 3;
   private final int ORE_ACTION_PERIOD = 4;
   private final String SMITH_KEY = "blacksmith";
   private final int SMITH_NUM_PROPERTIES = 4;
   private final int SMITH_ID = 1;
   private final int SMITH_COL = 2;
   private final int SMITH_ROW = 3;
   private final String VEIN_KEY = "vein";
   private final int VEIN_NUM_PROPERTIES = 5;
   private final int VEIN_ID = 1;
   private final int VEIN_COL = 2;
   private final int VEIN_ROW = 3;
   private final int VEIN_ACTION_PERIOD = 4;
   private final int ORE_REACH = 1;
   public final String ORE_KEY = "ore";

   private final String GHOST_KEY = "ghost";
   private final int GHOST_NUM_PROPERTIES = 7;
   private final int GHOST_ID = 1;
   private final int GHOST_COL = 2;
   private final int GHOST_ROW = 3;
   private final int GHOST_LIMIT = 4;
   private final int GHOST_ACTION_PERIOD = 5;
   private final int GHOST_ANIMATION_PERIOD = 6;

   private final String FROG_KEY = "frog";
   private final int FROG_NUM_PROPERTIES = 7;
   private final int FROG_ID = 1;
   private final int FROG_COL = 2;
   private final int FROG_ROW = 3;
   private final int FROG_LIMIT = 4;
   private final int FROG_ACTION_PERIOD = 5;
   private final int FROG_ANIMATION_PERIOD = 6;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }


   public boolean parseBackground(String [] properties,
                                  ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         setBackground(pt,
                 new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public boolean parseMiner(String [] properties,
                             ImageStore imageStore)
   {
      if (properties.length == MINER_NUM_PROPERTIES)
      {


         Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                 Integer.parseInt(properties[MINER_ROW]));
         Entity entity = pt.createMiner_Not_FullFull(properties[MINER_ID],
                 Integer.parseInt(properties[MINER_LIMIT]),
                 Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                 Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                 imageStore.getImageList(MINER_KEY));
         tryAddEntity(entity);
      }

      return properties.length == MINER_NUM_PROPERTIES;
   }





   public boolean parseObstacle(String [] properties,
                                ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Entity entity = pt.createObstacle(properties[OBSTACLE_ID],
                 imageStore.getImageList(OBSTACLE_KEY));
         tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseOre(String [] properties, ImageStore imageStore)
   {
      if (properties.length == ORE_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                 Integer.parseInt(properties[ORE_ROW]));
         Entity entity = pt.createOre(properties[ORE_ID],
                 Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                 imageStore.getImageList(ORE_KEY));
         tryAddEntity(entity);
      }

      return properties.length == ORE_NUM_PROPERTIES;
   }

   public boolean parseGhost(String [] properties,
                              ImageStore imageStore)
{
   if (properties.length == GHOST_NUM_PROPERTIES)
   {
      Point pt = new Point(Integer.parseInt(properties[GHOST_COL]),
              Integer.parseInt(properties[GHOST_ROW]));
      Entity entity = pt.createGhost(properties[GHOST_ID],
              Integer.parseInt(properties[GHOST_LIMIT]),
              Integer.parseInt(properties[GHOST_ACTION_PERIOD]),
              Integer.parseInt(properties[GHOST_ANIMATION_PERIOD]),
              imageStore.getImageList(GHOST_KEY));
      tryAddEntity(entity);
   }

   return properties.length == GHOST_NUM_PROPERTIES;
}

   public boolean parseFrog(String [] properties,
                             ImageStore imageStore)
   {
      if (properties.length == FROG_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[FROG_COL]),
                 Integer.parseInt(properties[FROG_ROW]));
         Entity entity = pt.createGhost(properties[FROG_ID],
                 Integer.parseInt(properties[FROG_LIMIT]),
                 Integer.parseInt(properties[FROG_ACTION_PERIOD]),
                 Integer.parseInt(properties[FROG_ANIMATION_PERIOD]),
                 imageStore.getImageList(FROG_KEY));
         tryAddEntity(entity);
      }

      return properties.length == FROG_NUM_PROPERTIES;
   }

   public boolean parseSmith(String [] properties,
                             ImageStore imageStore)
   {
      if (properties.length == SMITH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                 Integer.parseInt(properties[SMITH_ROW]));
         Entity entity = pt.createBlacksmith(properties[SMITH_ID],
                 imageStore.getImageList(SMITH_KEY));
         tryAddEntity(entity);
      }

      return properties.length == SMITH_NUM_PROPERTIES;
   }

   public boolean parseVein(String [] properties,
                            ImageStore imageStore)
   {
      if (properties.length == VEIN_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                 Integer.parseInt(properties[VEIN_ROW]));
         Entity entity = pt.createVein(properties[VEIN_ID],
                 Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                 imageStore.getImageList(VEIN_KEY));
         tryAddEntity(entity);
      }

      return properties.length == VEIN_NUM_PROPERTIES;
   }

   public boolean processLine(String line, ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0) {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return parseBackground(properties, imageStore);
            case MINER_KEY:
               return parseMiner(properties, imageStore);
            case OBSTACLE_KEY:
               return parseObstacle(properties, imageStore);
            case ORE_KEY:
               return parseOre(properties, imageStore);
            case SMITH_KEY:
               return parseSmith(properties, imageStore);
            case VEIN_KEY:
               return parseVein(properties, imageStore);

         }
      }

      return false;
   }


   public void tryAddEntity(Entity entity)
   {
      if (isOccupied( entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      addEntity(entity);
   }

   public boolean withinBounds(Point pos)
   {
      return pos.y >= 0 && pos.y < numRows &&
              pos.x >= 0 && pos.x < numCols;
   }

   public boolean isOccupied(Point pos)
   {
      return withinBounds( pos) &&
              getOccupancyCell(pos) != null;
   }



   /*
         Assumes that there is no entity currently occupying the
         intended destination cell.
   */
   public void addEntity(Entity entity)
   {
      if (withinBounds(entity.getPosition()))
      {
         setOccupancyCell(entity.getPosition(), entity);
         entities.add(entity);
      }
   }

   // removes Entity at current position and places at new position
   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell(oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntity(Entity entity)
   {
      removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt(Point pos)
   {
      if (withinBounds(pos)
              && getOccupancyCell(pos) != null)
      {
         Entity entity = getOccupancyCell(pos);

                  /* this moves the entity just outside of the grid for
                  debugging purposes */
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (withinBounds(pos)) {
         return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
      }
      else {
         return Optional.empty();
      }
   }

   public void setBackground(Point pos, Background background)
   {
      if (withinBounds(pos))
      {
         setBackgroundCell(pos, background);
      }
   }

   public Optional<Entity> getOccupant(Point pos)
   {
      if (isOccupied(pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public Entity getOccupancyCell(Point pos)
   {
      return occupancy[pos.y][pos.x];
   }

   public void setOccupancyCell(Point pos,
                                Entity entity)
   {
      occupancy[pos.y][pos.x] = entity;
   }

   public Background getBackgroundCell(Point pos)
   {
      return background[pos.y][pos.x];
   }

   public void setBackgroundCell(Point pos, Background background)
   {
      this.background[pos.y][pos.x] = background;
   }

   public Optional<Entity> findNearest(Point pos, Entity entityOG) {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities) {
         if (entity.getClass().equals(entityOG.getClass())) {
            ofType.add(entity);
         }
      }
      return pos.nearestEntity(ofType);
   }

   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
      {
         for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (withinBounds(newPt) &&
                    !isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }


   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!this.processLine(in.nextLine(), imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }


}