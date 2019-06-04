import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;


public abstract class EntityAnimated extends ActiveEntity {


    public EntityAnimated(String id, Point position, List<PImage> images,
                        int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }


    public Point nextPosition(WorldModel world, Point destPos) {

        // SingleStepPathingStrategy pathstrat = new SingleStepPathingStrategy();
        AStarPathingStrategy pathstrat = new AStarPathingStrategy();

        List<Point> pathPoints = pathstrat.computePath(this.getPosition(), destPos,
                (p -> world.withinBounds(p) && !(world.isOccupied(p))),
                ((p1, p2) -> p1.adjacent(p2)),
                PathingStrategy.CARDINAL_NEIGHBORS);

        if (pathPoints.isEmpty()){
            return this.getPosition();
        }

        return pathPoints.get(0);
    }

}