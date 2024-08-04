package elements;

import primitives.*;

/**
 * class for Ambient Lighting
 * @author Naomi and Noam
 */
public class AmbientLight extends Light {

    /**
     * constructor
     * @param iA Original light intensity
     * @param kA Discount factor
     * The constructor calculate the final light intensity
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }

    /**
     * default ctor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}
