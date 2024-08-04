package primitives;

/**
 * class for material of object
 * @author Noam and Naomi
 */
public class Material {

    //the factors of material
    /**
     * diffuse factor
     */
    public double kD =0;

    /**
     * specular factor
     */
    public double kS =0;

    /**
     * shininess factor
     */
    public int nShininess =0;

    /**
     * transparency factor
     */
    public double kT=0.0;

    /**
     * reflection factor
     */
    public double kR=0.0;

    /**
     * setter
     * @param kD factor of the material
     * @return the material (chaining method)
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }
    /**
     * setter
     * @param kS factor of the material
     * @return the material (chaining method)
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter
     * @param nShininess the shininess of the material
     * @return the material (chaining method)
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * setter
     * @param kT transparency factor of the material
     * @return the material (chaining method)
     */
    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter
     * @param kR reflection factor of the material
     * @return the material (chaining method)
     */
    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }
}
