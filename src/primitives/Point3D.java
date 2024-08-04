package primitives;

/**
 * Basic Point in 3D including 3 coordinate
 *
 * @author noam and naomi
 */
public class Point3D {
    /**
     * 3 Coordinates for a single point in 3D
     */
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    /**
     * set for exception- when we have vector 0
     */
    public static final Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * getter
     * @return x coordinate of point
     */
    public double getX() {
        return _x.coord;
    }

    /**
     * getter
     * @return y coordinate of point
     */
    public double getY() {
        return _y.coord;
    }

    /**
     * getter
     * @return z coordinate of point
     */
    public double getZ() {
        return _z.coord;
    }

    /**
     * ctor that get 3 Coordinates and call the second ctor
     *
     * @param x coordinate of point
     * @param y coordinate of point
     * @param z coordinate of point
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    /**
     * ctor that get 3 numbers
     *
     * @param x value of point
     * @param y value of point
     * @param z value of point
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * equals 2 Point3D
     *
     * @param o object to compare
     * @return true if they are equals else return false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    /**
     * string of object point 3D
     * @return string of object
     */
    @Override
    public String toString() {
        return "(" + _x + ", " + _y + ", " + _z + ")";
    }

    /**
     * the distance Squared between p1 to current Point3D
     *
     * @param p1 other point
     * @return the distance Squared
     */
    public double distanceSquared(Point3D p1) {
        double xDistance = (_x.coord - p1._x.coord);
        double yDistance = (_y.coord - p1._y.coord);
        double zDistance = (_z.coord - p1._z.coord);
        return xDistance * xDistance + yDistance * yDistance + zDistance * zDistance;
    }

    /**
     * the distance between p1 to current Point3D using distanceSquared function
     *
     * @param p1 other point
     * @return the distance
     */
    public double distance(Point3D p1) {
        return Math.sqrt(distanceSquared(p1));
    }

    /**
     * the subtract between p1 to current Point3D that create vector
     *
     * @param p1 other point
     * @return the vector that created from the subtract
     */
    public Vector subtract(Point3D p1) {
        return new Vector(_x.coord - p1._x.coord,
                _y.coord - p1._y.coord,
                _z.coord - p1._z.coord);

    }

    /**
     * add vector to current Point3D
     *
     * @param v vector to add
     * @return the new point
     */
    public Point3D add(Vector v) {
        return new Point3D(_x.coord + v._head._x.coord,
                _y.coord + v._head._y.coord,
                _z.coord + v._head._z.coord);
    }


}
