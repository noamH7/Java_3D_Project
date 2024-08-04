package geometries;

import primitives.*;

/**
 * Common interface to all geometric shapes
 * @author Noam and Naomi
 */
public abstract class Geometry extends Intersectable {

    /**
     * self color of the object.
     */
    protected Color _emission = Color.BLACK;
    /**
     * material of the object
     */
    private Material _material = new Material();

    /**
     * getter
     * @return the color (self light) of the object.
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * setter
     * @param emission the self light of the object.
     * @return the object for linking.
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * getter
     * @return the material of object
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * setter
     * @param material of the object
     * @return the object for linking.
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * @param point on geometry
     * @return the normal to the point on the geometry
     */
    public abstract Vector getNormal(Point3D point);

}
