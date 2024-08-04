package elements;

import primitives.Color;

/**
 * base class for light
 * @author Noam and Naomi
 */
abstract class Light {
    /**
     * the intensity of the light
     */
    protected Color _intensity;

    /**
     * constructor for light
     * @param intensity of the light
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * getter
     * @return intensity of the light
     */
    public Color getIntensity() {
        return _intensity;
    }
}
