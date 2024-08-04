package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A basic implement of ray tracing
 * @author Naomi and noam
 */
public class BasicRayTracer extends RayTracerBase {

    /**
     * initial value for recursion color calculate
     */
    private static final double INITIAL_K = 1.0;

    /**
     * maximum level of recursion for color calculate
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * minimum value for stop recursion color calculate
     */
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * ctor
     *
     * @param scene which we want to paint
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * painting point in image
     *
     * @param ray from the camera to the scene
     * @return the color of the instruct point between scene amd ray
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        //if there are not instruction points return the background
        return closestPoint == null ? _scene.background : calcColor(closestPoint, ray);
    }

    /**
     * find closest intersection point with the scene's 3D model.
     *
     * @param ray to the scene.
     * @return the instruction point which closest to head of ray.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {//if there are not instruction points return null
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * calculate the color in a point of geometry
     *
     * @param geoPoint intersection point and geometry.
     * @param ray      to point of geometry.
     * @return the color of the point of geometry
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(_scene.ambientLight.getIntensity());
    }

    /**
     * calculate the color in a point of geometry with support reflection and refraction (recursive).
     *
     * @param intersection point of geometry.
     * @param ray          to point of geometry.
     * @param level        of recursive.
     * @param k            factors multiplication.
     * @return the color of the point of geometry
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * calculate the effects of reflection and refraction on the color.
     *
     * @param gp    intersection point and geometry.
     * @param ray   to point of geometry.
     * @param level of recursive.
     * @param k     factors multiplication.
     * @return the effected color at point.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;//add effect of new reflection factor
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, ray.getDirection(), n), level, material.kR, kkr);// calculate reflection's effects
        double kkt = k * material.kT;//add effect of new refraction factor
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, ray.getDirection(), n), level, material.kT, kkt));//calculate refraction's effects
        return color;
    }

    /**
     * calculate the effects of reflection or refraction ray on the color.
     *
     * @param ray   reflection or refraction ray.
     * @param level of recursive.
     * @param kx    new factor.
     * @param kkx   factors multiplication.
     * @return the effected color at point.
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        //recursive action that
        return ((gp == null ? _scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx));
    }

    /**
     * calculate the local effects on the color of all light sources
     *
     * @param geoPoint the point (and its geometry) witch we calculate its color
     * @param ray      from the camera
     * @return the local effects (color)
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, double k) {
        Vector v = ray.getDirection(); //the vector from the camera
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK; //if the geometry is not seen from the camera
        Material material = geoPoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kD = material.kD;
        double kS = material.kS;
        Color color = Color.BLACK;
        //Go through the list of light sources and collect their effects
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // check if sign(nl) == sing(nv)
                double ktr = transparency(lightSource, l, n, geoPoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    //Adding diffusion and specular calculation
                    color = color.add(calcDiffusive(kD, nl, lightIntensity),
                            calcSpecular(kS, l, n, v, nl, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * check non-shading between point to light source.
     *
     * @param lightSource the light source.
     * @param l           direction vector from the light source to the point.
     * @param n           normal vector to the geometry in the point.
     * @param geoPoint    the point and its geometry.
     * @return true if there are no shadow on the point, else return false;
     */
    private boolean unshaded(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, n, lightDirection);//create reverse ray
        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(geoPoint.point));
        if (intersections == null)
            return true;
        double lightDistance = lightSource.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            //check if the instruction point is  not behind the light source and f the geometry not transparency
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0 && gp.geometry.getMaterial().kT == 0)
                return false;
        }
        return true;
    }

    /**
     * check shading between point to light source relatively to transparency.
     *
     * @param lightSource the light source.
     * @param l           direction vector from the light source to the point.
     * @param n           normal vector to the geometry in the point.
     * @param geoPoint    the point and its geometry.
     * @return level of shadow relatively to transparency
     */
    private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, n, lightDirection);//create reverse ray
        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(geoPoint.point));
        if (intersections == null)
            return 1.0;//No shading
        double ktr = 1.0;
        double lightDistance = lightSource.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            //check if the instruction point is  not behind the light source and f the geometry not transparency
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0 )//&& gp.geometry.getMaterial().kT == 0)
                ktr *= gp.geometry.getMaterial().kT;// multiple transparency factor of all intersection points.
            if (ktr < MIN_CALC_COLOR_K)
                return 0.0;//There is full shading
        }
        return ktr;
    }

    /**
     * calculate the diffusive effect on the color of a light source on a geometry
     *
     * @param kD             diffusive factor of the material of the geometry
     * @param nl             the dot product of the normal of the geometry and the vector from the light source to the geometry
     * @param lightIntensity the intensity of the light source
     * @return the diffusive effect
     */
    private Color calcDiffusive(double kD, double nl, Color lightIntensity) {
        return lightIntensity.scale(kD * Math.abs(nl));
    }

    /**
     * calculate the specular effect on the color of a light source on a geometry
     *
     * @param kS             specular factor of the material of the geometry
     * @param l              the vector from the light source to the geometry
     * @param n              the normal of the geometry
     * @param v              the vector from the camera
     * @param nl             the dot product of n and l
     * @param nShininess     shininess factor of the material of the geometry
     * @param lightIntensity the intensity of the light source.
     * @return the specular effect
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, double nl, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * nl));
        Vector minusV = v.scale(-1);
        double shininess = Math.pow(r.dotProduct(minusV), nShininess);
        return lightIntensity.scale(kS * shininess);
    }

    /**
     * Constructing reflected ray.
     *
     * @param point instruction point with geometry.
     * @param n     normal of the geometry at the point.
     * @param v     direction vector from the light source.
     * @return reflected ray.
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        double vn = v.dotProduct(n);
        Vector projection = n.scale(vn);
        Vector r = v.subtract(projection.scale(2));
        return new Ray(point, n, r);
    }

    /**
     * Constructing refracted ray.
     *
     * @param point instruction point with geometry.
     * @param n     normal of the geometry at the point.
     * @param v     direction vector from the light source.
     * @return reflected ray.
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, n, v);
    }
}

