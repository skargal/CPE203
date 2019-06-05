import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;
import java.util.NoSuchElementException;

public class Space_Frog extends EntityAnimated {

    public static final String QUAKE_KEY = "quake";

    public Space_Frog(String id, Point position, List<PImage> images, int resourceLimit,
                 int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executesSpace_FrogActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Miner_Full miner = new Miner_Full(this.getId(), this.getPosition(), this.getImages(), 0, 0, this.getActionPeriod(), 0);
        EntityAnimated target;
        try {
            target = (Miner_Full)world.findNearest(this.getPosition(), miner).get();
        }
        catch (NoSuchElementException e) {
            try {
                Miner_Not_Full minerN = new Miner_Not_Full(this.getId(), this.getPosition(), this.getImages(), 0, 0, this.getActionPeriod(), 0);
                target = (Miner_Not_Full)world.findNearest(this.getPosition(), minerN).get();
            }
            catch (NoSuchElementException e2) {
                world.removeEntity((Entity)this);
                return;
            }
        }

        long nextPeriod = this.getActionPeriod();

        Point tgtPos = target.getPosition();
// miner disappear when space_frog next to-- and elimate all others
        if (moveToSpace_Frog(world, (Entity)target, scheduler)) {

            ActiveEntity quake = tgtPos.createQuake(imageStore.getImageList(QUAKE_KEY));

            world.addEntity((Entity)quake);
            nextPeriod += this.getActionPeriod();
            quake.scheduleActions(scheduler, world, imageStore);
        }

        Activity act = new Activity(this, world, imageStore, 0);
        scheduler.scheduleEvent(this,
                act.createActivityAction(world, imageStore),
                this.getActionPeriod());

    }


    public boolean moveToSpace_Frog(WorldModel world, Entity target, EventScheduler scheduler) {
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


    public void transformToSpace_Frog(Point p, WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        try {
            Miner_Full miner = new Miner_Full(this.getId(), this.getPosition(), this.getImages(), 0, 0, this.getActionPeriod(), 0);
            Miner_Full died = (Miner_Full)world.findNearest(this.getPosition(), miner).get();

            Entity space_frog = (this.getPosition()).createSpace_Frog(this.getId(), this.resourceLimit,
                    this.getActionPeriod(), this.getAnimationPeriod(), this.getImages());

            world.removeEntity(died);
            scheduler.unscheduleAllEvents(this);

            world.tryAddEntity(space_frog);
            ActiveEntity g = (ActiveEntity)space_frog;
            g.scheduleActions(scheduler, world, imageStore);
        }
        catch (Exception e){
            return;
        }

    }


}