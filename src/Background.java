import java.util.List;
import processing.core.PImage;

final class Background
{
   public String id;
   public List<PImage> images;
   public int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }


   public PImage getCurrentImage()
   {
      if (this instanceof Background)
      {
         return this.images
                 .get(this.imageIndex);
      }
      else
      {
         throw new UnsupportedOperationException(
                 String.format("getCurrentImage not supported for %s",
                         this));
      }
   }

}


  /*  public String getID() { return this.id;}

    public int getImageIndex() {
        return this.imageIndex;}

    public List<PImage> getImages() {
        return this.images;}

   public static Optional<PImage> getBackgroundImage(WorldModel world,
                                                     Point pos, ImageStore imageStore)
   {
      if (WorldView.withinBounds(world, pos))
      {
         return Optional.of(imageStore.getCurrentImage(getBackgroundCell(world, pos)));
      }
      else
      {
         return Optional.empty();
      }
   }

   public static Background getBackgroundCell(WorldModel world, Point pos)
   {
      return world.background[pos.y][pos.x];
   }
*/

