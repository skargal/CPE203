/*
public interface ActiveEntity extends Entity {
    void executeActivity(EventScheduler scheduler, Activity activity);
    void scheduleActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

}
*/

import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;


public abstract class ActiveEntity extends Entity {


    public ActiveEntity(String id, Point position, List<PImage> images,
                        int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }


    protected void scheduleActions(EventScheduler scheduler,
                                   WorldModel world, ImageStore imageStore) {
        Activity act = new Activity(this, world, imageStore, 0);
        Animation anim = new Animation(this, world, imageStore, 0);
        scheduler.scheduleEvent(this, act.createActivityAction(world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this, anim.createAnimationAction(0),
                this.getAnimationPeriod());
    }

}