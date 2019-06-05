import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;


public class Animation extends Action {

    public Animation(Entity entity, WorldModel world,
                     ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        executeAnimationAction(scheduler);
    }

    public void executeAnimationAction(EventScheduler scheduler){
        this.getEntity().nextImage();

        if (this.getRepeatCount() != 1) {
            if (this.getEntity() instanceof Miner_Full) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Miner_Full)(this.getEntity())).getAnimationPeriod());
            }
            else if (this.getEntity() instanceof Miner_Not_Full) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Miner_Not_Full)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Space_Frog) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Space_Frog)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Ore) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Ore)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Ore_Blob) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Ore_Blob)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Portal) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Portal)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Quake) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Quake)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Vein) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Vein)(this.getEntity())).getAnimationPeriod());
            }

            /*else if (this.getEntity() instanceof Crosshair) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Crosshair)(this.getEntity())).getAnimationPeriod());
            }
*/

        }
    }


    public Action createAnimationAction(int repeatCount) {
        return new Animation(this.getEntity(), null, null, repeatCount);
    }
}