package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * class for Scene 3D
 *
 * @author Noam and Naomi
 */
public class Scene {
    /**
     * name of scene
     */
    public String name;
    /**
     * background of scene
     */
    public Color background = Color.BLACK;
    /**
     * ambient light of scene
     */
    public AmbientLight ambientLight = new AmbientLight();
    /**
     * collection of geometries in scene
     */
    public Geometries geometries;
    /**
     * collection of lights in scene
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * constructor
     *
     * @param name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    /**
     * @param background of the scene
     * @return the scene (chaining method)
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * @param ambientLight the Ambient Lighting of the scene
     * @return the scene (chaining method)
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * @param geometries 3D model of the scene
     * @return the scene (chaining method)
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * @param lights The light sources of the scene.
     * @return the scene (chaining method)
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
