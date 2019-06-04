import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;


public class Activity extends Action {


    public Activity(Entity entity, WorldModel world,
                    ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
    }

    public void executeActivityAction(EventScheduler scheduler) {

        if (this.getEntity() instanceof Miner_Full) {
            ((Miner_Full)(this.getEntity())).executeMinerFullActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof Miner_Not_Full) {
            ((Miner_Not_Full)(this.getEntity())).executeMiner_Not_FullFullActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
        }

        else if (this.getEntity() instanceof Ghost) {
            ((Ghost)(this.getEntity())).executeGhostActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
        }

        else if (this.getEntity() instanceof Frog) {
            ((Frog)(this.getEntity())).executeFrogActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
        }
        else if (this.getEntity() instanceof Ore) {
            ((Ore)(this.getEntity())).executeOreActivity(this.getWorld(), this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof Ore_Blob) {
            ((Ore_Blob)(this.getEntity())).executeOreBlobActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof Quake) {
            ((Quake)(this.getEntity())).executeQuakeActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
        }
        else if (this.getEntity() instanceof Vein) {
            ((Vein)(this.getEntity())).executeVeinActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
        }

    }


    public Action createActivityAction(WorldModel world,
                                       ImageStore imageStore) {
        return new Activity(this.getEntity(), world, imageStore, 0);
    }

}