package org.bimserver.cobie.graphics3d;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.cobie.graphics.FloorArranger;
import org.bimserver.cobie.graphics.For3D;
import org.bimserver.cobie.graphics.entities.Floor;
import org.bimserver.cobie.graphics.entities.RenderableIfcProduct;

// TODO This class is all jacked up right now.
public class FloorArranger3D extends FloorArranger implements For3D
{
    public FloorArranger3D(Engine engine, Floor floor)
    {
        super(engine, floor);
    }

//  private void renderLights()
//  {
//      Color3f lightColor = getSettings().materialSettings.lightColor;
//      
//      getEngine().for3D().addAmbientLight(lightColor);
//      // engine.addDirectionalLight(Engine.DEFAULT_LIGHT_COLOR, new
//      // Vector3f(20.0f, -50.0f, -75.0f));
//
//      Point3f position = new Point3f(25.0f, -25.0f, -25.0f);
//      Vector3f direction = new Vector3f(1.0f, -1.0f, 1.0f);
//      Point3f attenuation = new Point3f(0.1f, 0.05f, 0.0f);
//      float concentration = 30.0f;
//      float spread = 2.0f;
//
//      getEngine().for3D().addSpotLight(lightColor, position, attenuation, direction, spread, concentration);
//  }
  
  private void renderText()
  {
      //engine.renderText2D("HELP!", Engine.DEFAULT_FONT_2D, new Color3f(Color.RED), new Point3f(Engine.DEFAULT_ORIGIN));
      //engine.renderText("HELP!!!", new Point3f(Engine.DEFAULT_ORIGIN));
  }
  
    /**
     * This method rotates the supplied RenderableEntity on it's side if it is taller than it is wide.
     * @return
     */
    // TODO Put this "arrange" stuff in a separate class.
    @Deprecated
    private final void rotate(RenderableIfcProduct entity)
    {
//        double rotation = Math.PI;
//        
//        double floorWidth = this.lowestFloor.getWidth();
//        double floorHeight = this.lowestFloor.getHeight();
//        
//        if (floorHeight > floorWidth)
//        {
//            rotation /= 2;
//        }
//        
//        entity.rotate(rotation);
    }
        
    private final void scale(RenderableIfcProduct entity)
    {
//        Transform3D t = new Transform3D();
//        entity.getShape().getLocalToVworld(t);
        
        double scaling = 1.0;
        
        double virtualScreenWidth = 1024;//getEngine().getCanvas().getPhysicalWidth();
        double virtualScreenHeight = 1024;//getEngine().getCanvas().getPhysicalHeight();
//        
//        double width = entity.for3D().getWidth();
//        double height = entity.for3D().getHeight();
        
        // TODO Should make sure the entity has not been transformed already!
        
        // TODO NO IDEA HOW TO FIGURE THIS OUT!!!
        //
        // Scaling the entity by the ratio of its virtual width/height to the screen's 
        // virtual width/height should scale the entity to fit almost exactly within the 
        // screen (with a slight margin due to the camera distance and perspective distortion), 
        // but...
        //
        // The entity's (Shape3D) world transform is the identity matrix; the canvas's 
        // world transform is BS (scaling by the scaling it defines shrinks the 
        // entity WAY too much). 
        //
        // It might be possible to get the virtual bounds from the entity's bounding object,
        // but there doesn't seem to be an obvious way to get the coordinates out of it.
        // NOPE! The BoundingBox returns the raw dimensions, too!
        //
        // I will fake it for now b/c the entity's raw dimensions *should* always be greater 
        // than the virtual screen dimensions.
//        double virtualWidth = width / 2; 
//        double virtualHeight = height / 2;
        
//        if (width >= height)
//        {
//            scaling = virtualScreenWidth / virtualWidth;
//        }
//        
//        else
//        {
//            scaling = virtualScreenHeight / virtualHeight;
//        }
        
        scaling = 0.01;
        //entity.scale(new Vector3d(scaling, scaling, scaling));
    }
    
//    private final Vector3d getTranslation()
//    {
//        double floorLeft = this.lowestFloor.getLeft();
//        double floorTop = this.lowestFloor.getTop();
//                
//        Point3d origin = new Point3d();
//        this.engine.getCanvas().getPixelLocationInImagePlate(0, 0, origin);
//        Orientation orientation = getSettings().renderSettings.orientation;
//        
//        double originWidth = Math3D.getHorizontalComponent(origin, orientation);
//        double originHeight = Math3D.getVerticalComponent(origin, orientation);
//        
//        double dWidth = Math3D.normalize(originWidth - floorLeft);
//        double dHeight = Math3D.normalize(originHeight - floorTop);
//        
//        Point3d translation = new Point3d();
//        Math3D.setHorizontalComponent(translation, dWidth, orientation);
//        Math3D.setVerticalComponent(translation, dHeight, orientation);        
//        
//        return new Vector3d(translation);
//    }
    
    private final void collapse(RenderableIfcProduct entity)
    {
        //entity.scale(Scale.COLLAPSE_Z.toVector3d());
    }
    
    @Override
    public final void arrange()
    {          
//        rotate(this.lowestFloor);
//        scale(space);
//        collapse(space);
        //this.lowestFloor.move(getTranslation());
    }
}
