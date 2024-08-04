package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for spot light
 * @author Noam and Naomi
 */
public class SpotLight extends PointLight{
   private Vector _direction;

    /**
     * constructor
     * @param intensity of the spot light
     * @param position of the spot light
     * @param direction of the spot light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     * @param p point on object
     * @return the vector from the spot light to the object
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0, _direction.dotProduct(getL(p))));
    }
}
