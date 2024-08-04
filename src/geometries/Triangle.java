package geometries;

import primitives.Point3D;

/**
 * polygon with 3 vertices
 * @author Noam and Naomi
 */
public class Triangle extends Polygon{
    /**
     * ctor
     * @param p1 vertex of triangle
     * @param p2 vertex of triangle
     * @param p3 vertex of triangle
     */
    public Triangle(Point3D p1,Point3D p2,Point3D p3) {
        super(p1,p2,p3);
    }

    /**
     * string of object triangle
     * @return string of object
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + _vertices +
                ", plane=" + _plane +
                '}';
    }

}
