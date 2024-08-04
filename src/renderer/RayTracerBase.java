package renderer;

import primitives.*;
import scene.Scene;

/**
 * abstract class for ray tracing (finding colors of scene)
 *
 * @author Naomi and Noam
 */
public abstract class RayTracerBase {
    protected Scene _scene;

    /**
     * ctor
     * @param scene which we want to paint
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * finding color of  ray instruct point
     * @param ray of tracing
     * @return the color of point
     */
    public abstract Color traceRay(Ray ray);

}
