import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

public class Ore extends ActiveEntity {

    public static final String BLOB_KEY = "blob";
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    // public static final String ORE_KEY = "ore";

    public Ore(String id, Point position, List<PImage> images, int resourceLimit,
               int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);

    }

    public void executeOreActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        // Point pos = position;  // store current position before removing

        Ore_Blob blob = new Ore_Blob(this.getId() + BLOB_ID_SUFFIX,
                this.getPosition(), imageStore.getImageList(BLOB_KEY), resourceLimit, resourceCount,
                this.getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + ImageStore.rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN));

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }


   // public <R> R accept(Visitor<R> visitor) {
     //   return visitor.visit(this);
    //}
}

/*
final class Ore implements ActiveEntity {

    private static final int BLOB_ANIMATION_MAX = 150;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final String BLOB_KEY = "blob";

    private final String id;
    private Point position;
    private final int actionPeriod;
    private final List<PImage> images;

    public Ore(String id, Point position, int actionPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.images = images;
    }


    public PImage getCurrentImage() {
        return images.get(0);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point newPosition) {
        position = newPosition;
    }

    public void scheduleActivity(EventScheduler scheduler,
                                 WorldModel world, ImageStore imageStore) {

        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), actionPeriod);

    }

    public void executeActivity(EventScheduler scheduler, Activity activity) {
        this.executeOreActivity(activity.world, activity.imageStore, scheduler);
    }

    private Activity createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    private void executeOreActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = position;  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Ore_Blob blob = new Ore_Blob(id + BLOB_ID_SUFFIX,
                pos, actionPeriod / BLOB_PERIOD_SCALE, BLOB_ANIMATION_MIN + Functions.rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),  imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActivity(scheduler, world, imageStore);
    }

}*/
