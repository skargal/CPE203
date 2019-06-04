import java.util.List;

import processing.core.PImage;

final class Obstacle extends Entity {


    public Obstacle(String id, Point position, List<PImage> images,
                    int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

  //  public <R> R accept(Visitor<R> visitor) {
  //      return visitor.visit(this);
   // }


}
/*
final class Obstacle implements Entity {

    private final String id;
    private Point position;
    private List<PImage> images;

    public Obstacle(String id, Point position,
                    List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point newPosition) {
        position = newPosition;
    }

    public PImage getCurrentImage() {
        return images.get(0);
    }

}*/
