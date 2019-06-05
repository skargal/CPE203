import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import processing.core.*;
import processing.event.MouseEvent;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.Dimension;

public final class VirtualWorld
        extends PApplet
{
   static List<Integer> dim = new ArrayList<Integer>(4);

   public void settings()
   {
      //size(displayWidth, displayHeight);
      fullScreen();
      dim.add(width);
      dim.add(height);
   }

   static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

   public static final int TIMER_ACTION_PERIOD = 100;

   public static final int VIEW_WIDTH = (int)screenSize.getWidth();
   public static final int VIEW_HEIGHT = (int)screenSize.getHeight();

   public static final int TILE_WIDTH = 32;
   public static final int TILE_HEIGHT = 32;
   public static final int WORLD_WIDTH_SCALE = 1;
   public static final int WORLD_HEIGHT_SCALE = 1;

   public static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH; // 640/32 = 20
   public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT; // 480/32 = 15
   public static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE; // 20*2 = 40
   public static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE; // 15*2 = 30

   public static final String IMAGE_LIST_FILE_NAME = "imagelist";
   public static final String DEFAULT_IMAGE_NAME = "background_default";
   public static final int DEFAULT_IMAGE_COLOR = 0x808080;

   public static final String LOAD_FILE_NAME = "gaia.sav";

   public static final String FAST_FLAG = "-fast";
   public static final String FASTER_FLAG = "-faster";
   public static final String FASTEST_FLAG = "-fastest";
   public static final double FAST_SCALE = 0.5;
   public static final double FASTER_SCALE = 0.25;
   public static final double FASTEST_SCALE = 0.10;

   public static double timeScale = 1.0;

   public ImageStore imageStore;
   public WorldModel world;
   public WorldView view;
   public EventScheduler scheduler;

   public long next_time;


   /*
      Processing entry point for "sketch" setup.
   */
   public void setup()
   {
      this.imageStore = new ImageStore(
              createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
              createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
              TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }

      view.drawViewport();
   }

   void drawCrosshair(){
      int x = mouseX;
      int y = mouseY;

//      if(for(Entity t : world.entities){
//         ((t.getClass())) == (Crosshair.class);}
//         )
   }

   public void mouseClicked(MouseEvent mouseEvent) {

      int x = mouseEvent.getX() / TILE_WIDTH;
      int y = mouseEvent.getY() / TILE_HEIGHT;

      Point p = view.viewport.viewportToWorld(x, y);
//change grass to portal to change background
      Point p2 = view.viewport.viewportToWorld(x-1, y);
      world.setBackground(p2, new Background("portal1", imageStore.getImageList("portal1")));
      Point p3 = view.viewport.viewportToWorld(x+1, y);
      world.setBackground(p3, new Background("portal1", imageStore.getImageList("portal1")));
      Point p4 = view.viewport.viewportToWorld(x, y-1);
      world.setBackground(p4, new Background("portal1", imageStore.getImageList("portal1")));
      Point p5 = view.viewport.viewportToWorld(x, y+1);
      world.setBackground(p5, new Background("portal", imageStore.getImageList("portal")));
      Point p6 = view.viewport.viewportToWorld(x+1, y+1);
      world.setBackground(p6, new Background("portal", imageStore.getImageList("portal")));
      Point p7 = view.viewport.viewportToWorld(x-1, y-1);
      world.setBackground(p7, new Background("portal", imageStore.getImageList("portal")));
      Point p8 = view.viewport.viewportToWorld(x+1, y-1);
      world.setBackground(p8, new Background("portal", imageStore.getImageList("portal")));
      Point p9 = view.viewport.viewportToWorld(x-1, y+1);
      world.setBackground(p9, new Background("portal", imageStore.getImageList("portal")));

      /*Portal por2 = new Portal("id", new Point(p.x +1, p.y), imageStore.getImageList("portal"), 0, 0, 5, 5);
      por2.executePortalActivity(world, imageStore, scheduler);

      Portal por3 = new Portal("id", new Point(p.x -1, p.y), imageStore.getImageList("portal"), 0, 0, 5, 5);
      por3.executePortalActivity(world, imageStore, scheduler);

      Portal por4 = new Portal("id", new Point(p.x, p.y + 1), imageStore.getImageList("portal"), 0, 0, 5, 5);
      por4.executePortalActivity(world, imageStore, scheduler);

      Portal por5 = new Portal("id", new Point(p.x, p.y - 1), imageStore.getImageList("portal"), 0, 0, 5, 5);
      por5.executePortalActivity(world, imageStore, scheduler);

      Portal por6 = new Portal("id", new Point(p.x + 1, p.y + 1), imageStore.getImageList("portal"), 0, 0, 5, 5);
      por6.executePortalActivity(world, imageStore, scheduler);

      Portal por7 = new Portal("id", new Point(p.x + 1, p.y - 1), imageStore.getImageList("portal"), 0, 0, 5, 5);
      por7.executePortalActivity(world, imageStore, scheduler);

      Portal por8 = new Portal("id", new Point(p.x - 1, p.y + 1), imageStore.getImageList("portal"), 0, 0, 5, 5);
      por8.executePortalActivity(world, imageStore, scheduler);

      Portal por9 = new Portal("id", new Point(p.x - 1, p.y - 1), imageStore.getImageList("portal"), 0, 0, 5, 5);
      por9.executePortalActivity(world, imageStore, scheduler);world.setBackground(p, new Background("portal", imageStore.getImageList("portal")));
*/

      Space_Frog g = new Space_Frog("id", p, imageStore.getImageList("space_frog"), 0, 0, 0, 0);
      g.transformToSpace_Frog(p, world, scheduler, imageStore);

      Portal por = new Portal("id", p, imageStore.getImageList("portal"), 0, 0, 5, 5);
      por.executePortalActivity(world, imageStore, scheduler);


   }

//   public void mouseOver(MouseEvent mouseEvent) {
//
//      int x = mouseEvent.getX() / TILE_WIDTH;
//      int y = mouseEvent.getY() / TILE_HEIGHT;
//
//      Point p = view.viewport.viewportToWorld(x, y);
//
//
//        Crosshair c = new Crosshair("id", p, imageStore.getImageList("crosshair"), 0, 0, 0, 0);
//        c.executeCrosshairActivity(world, imageStore, scheduler);
//   }

   public void keyPressed()
   {
      if (key == CODED)
      {
         int dx = 0;
         int dy = 0;

         switch (keyCode)
         {
            case UP:
               dy = -1;
               break;
            case DOWN:
               dy = 1;
               break;
            case LEFT:
               dx = -1;
               break;
            case RIGHT:
               dx = 1;
               break;
         }
         //view.shiftView(dx, dy);
      }
   }


   public static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
              imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   public static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
                                  PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public static void loadWorld(WorldModel world, String filename,
                                ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         world.load(in, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public static void scheduleActions(WorldModel world,
                                      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.entities)
      {
         if (entity instanceof ActiveEntity) {
            ActiveEntity aEntity = (ActiveEntity)entity;
            aEntity.scheduleActions(scheduler, world, imageStore);
         }
      }
   }

   public static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   public static void main(String [] args)
   {
      //PApplet.main("--present", "Fullscreen");
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }
}