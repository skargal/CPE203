import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

public class Ore_Blob extends EntityAnimated {


    public static final String QUAKE_KEY = "quake";

    public Ore_Blob(String id, Point position, List<PImage> images, int resourceLimit,
                   int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeOreBlobActivity(WorldModel world,
                                       ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> blobTarget = world.findNearest(this.getPosition(), new Vein(this.getId(), this.getPosition(), this.getImages(), resourceLimit, resourceCount, this.getActionPeriod(), this.getAnimationPeriod()));
        long nextPeriod = this.getActionPeriod();

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveToOreBlob(world, blobTarget.get(), scheduler)) {
                ActiveEntity quake = tgtPos.createQuake(imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }
        Activity act = new Activity(this, world, imageStore, 0);
        scheduler.scheduleEvent(this,
                act.createActivityAction(world, imageStore), nextPeriod);
    }


    public boolean moveToOreBlob(WorldModel world, Entity target, EventScheduler scheduler) {

        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }



  //  public <R> R accept(Visitor<R> visitor) {
    //    return visitor.visit(this);
    //}

}

/*
final class Ore_Blob implements EntityAnimated, ActiveEntity {

    private static final String QUAKE_KEY = "quake";

    private final String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private final int actionPeriod;
    private final int animationPeriod;

    public Ore_Blob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point newPosition) {
        position = newPosition;
    }

    public void scheduleActivity(EventScheduler scheduler,
                                 WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                actionPeriod);
        scheduler.scheduleEvent(this,
                this.createAnimationAction(0), getAnimationPeriod());

    }

    public PImage getCurrentImage() {
        return images.get(imageIndex);
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    public void executeAnimation(EventScheduler scheduler, Animation animation) {
        nextImage();

        if (animation.repeatCount != 1) {
            scheduler.scheduleEvent(this, this.createAnimationAction(Math.max(animation.repeatCount - 1, 0)), getAnimationPeriod());
        }
    }

    public void executeActivity(EventScheduler scheduler, Activity activity) {
        executeOreBlobActivity(activity.world, activity.imageStore, scheduler);
    }


    private Activity createActivityAction(WorldModel world,
                                          ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    private Animation createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }

    private Point nextPositionOreBlob(WorldModel world,
                                      Point destPos) {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz,
                position.y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.getClass().getSimpleName() == "Ore"))) {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.getClass().getSimpleName() == "Ore"))) {
                newPos = position;
            }
        }

        return newPos;
    }

    private boolean moveToOreBlob(WorldModel world,
                                  Entity target, EventScheduler scheduler) {
        if (position.adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = this.nextPositionOreBlob(world, target.getPosition());

            if (!position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    private void executeOreBlobActivity(WorldModel world,
                                        ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> blobTarget = position.findNearest(world, "Vein");
        long nextPeriod = actionPeriod;

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().getPosition();

            if (this.moveToOreBlob(world, blobTarget.get(), scheduler)) {
                Quake quake = new Quake(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += actionPeriod;
                quake.scheduleActivity(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), nextPeriod);
    }


}*/
