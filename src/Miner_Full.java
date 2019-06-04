import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Miner_Full extends EntityAnimated {

    public Miner_Full(String id, Point position, List<PImage> images, int resourceLimit,
                     int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeMinerFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Blacksmith smith = new Blacksmith(this.getId(), this.getPosition(), this.getImages(), 0, 0, this.getActionPeriod(), 0);
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), smith);

        if (fullTarget.isPresent() &&
                moveToFull(world, fullTarget.get(), scheduler)) {
            transformFull(world, scheduler, imageStore);
        }
        else {
            Activity act = new Activity(this, world, imageStore, 0);
            scheduler.scheduleEvent(this,
                    act.createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity miner = this.getPosition().createMiner_Not_FullFull(this.getId(), this.resourceLimit,
                this.getActionPeriod(), this.getAnimationPeriod(), this.getImages());


        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        ActiveEntity m = (ActiveEntity)miner;
        m.scheduleActions(scheduler, world, imageStore);
    }

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {

        // If miner is next to blacksmith
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        }
        else { // Else move miner
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

}


/*
final class Miner_Full implements  EntityAnimated, ActiveEntity {

    private final String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private final int actionPeriod;
    private final int animationPeriod;

    public Miner_Full(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point newPosition) {
        position = newPosition;
    }

    public void scheduleActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this, this.createAnimationAction(0), getAnimationPeriod());
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
        this.executeMinerFullActivity(activity.world, activity.imageStore, scheduler);
    }

    private Activity createActivityAction(WorldModel world,
                                          ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    private Animation createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }

    private Point nextPositionMiner(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz,
                position.y);

        if (horiz == 0 || newPos.isOccupied(world)) {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x,
                    position.y + vert);

            if (vert == 0 || newPos.isOccupied(world)) {
                newPos = position;
            }
        }

        return newPos;
    }

    private boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (position.adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = this.nextPositionMiner(world, target.getPosition());

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

    private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Miner_Not_Full miner = new Miner_Not_Full(id, resourceLimit,
                position, actionPeriod, animationPeriod,
                images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActivity(scheduler, world, imageStore);
    }

    private void executeMinerFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = position.findNearest(world, "Blacksmith");

        if (fullTarget.isPresent() &&
                this.moveToFull(world, fullTarget.get(), scheduler)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    actionPeriod);
        }
    }

}
*/



