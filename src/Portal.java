//import java.util.List;
//import processing.core.PImage;
//import java.util.Optional;
//import java.util.Random;
//
//public class Portal extends ActiveEntity {
//
//    public static final String BLOB_KEY = "portal";
//    public static final int BLOB_ANIMATION_MIN = 50;
//    public static final int BLOB_ANIMATION_MAX = 150;
//    public static final String BLOB_ID_SUFFIX = " -- blob";
//    public static final int BLOB_PERIOD_SCALE = 4;
//    // public static final String ORE_KEY = "ore";
//
//    public Portal(String id, Point position, List<PImage> images, int resourceLimit,
//               int resourceCount, int actionPeriod, int animationPeriod) {
//        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
//
//    }
//
//    public void executePortalActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        // Point pos = position;  // store current position before removing
//
//        Ore_Blob blob = new Ore_Blob(this.getId() + BLOB_ID_SUFFIX,
//                this.getPosition(), imageStore.getImageList(BLOB_KEY), resourceLimit, resourceCount,
//                this.getActionPeriod() / BLOB_PERIOD_SCALE,
//                BLOB_ANIMATION_MIN + ImageStore.rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN));
//
//        world.removeEntity(this);
//        scheduler.unscheduleAllEvents(this);
//
//        world.addEntity(blob);
//        blob.scheduleActions(scheduler, world, imageStore);
//    }
//}

//------------------------------------------------------------------------------------------------------------------------------

import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

public class Portal extends EntityAnimated {


    public static final String QUAKE_KEY = "quake";

    public Portal(String id, Point position, List<PImage> images, int resourceLimit,
                    int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executePortalActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> blobTarget = world.findNearest(this.getPosition(), new Vein(this.getId(), this.getPosition(), this.getImages(), resourceLimit, resourceCount,
//                                      this.getActionPeriod(), this.getAnimationPeriod()));

        long nextPeriod = this.getActionPeriod();

        Point tgtPos = this.getPosition();

        ActiveEntity prt = tgtPos.createPortal("portal", resourceLimit, getActionPeriod(), getAnimationPeriod(), imageStore.getImageList("portal"));

        world.addEntity(prt);
        nextPeriod += this.getActionPeriod();
        prt.scheduleActions(scheduler, world, imageStore);

        }
    }


//    public boolean moveToOreBlob(WorldModel world, Entity target, EventScheduler scheduler) {
//
//        if (this.getPosition().adjacent(target.getPosition())) {
//            world.removeEntity(target);
//            scheduler.unscheduleAllEvents(target);
//            return true;
//        }
//        else {
//            Point nextPos = nextPosition(world, target.getPosition());
//
//            if (!this.getPosition().equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }


