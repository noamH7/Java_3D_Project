package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OurImage {
    private Scene scene = new Scene("Our scene");

    /**
     * create stars (randomly in the sky)
     *
     * @param num number of the stars
     */
    Geometries stars(int num) {
        Geometries stars = new Geometries();
        int x = 0;
        int y = 0;
        for (int i = 1; i <= num; i++) {
            x = (int) (Math.random() * 3000 - 1500);
            y = (int) (Math.random() * 1500);
            Point3D point = new Point3D(x, y, -17000);
            stars.add(new Sphere(5, point).setEmission(new Color(255, 240, 240))
                    .setMaterial(new Material().setKt(0.9)));
        }
        return stars;
    }

    @Test
    void OurImage() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(150, 150).setDistance(1000).setSuperSampling(9);

        //the sky
        scene.setBackground(new Color(0, 0, 50));//.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.setBvh().add(


                new Geometries(
                        //the moon and stars
                        new Sphere(20, new Point3D(-50, 50, -1000)).setEmission(new Color(java.awt.Color.gray))
                                .setMaterial(new Material().setKt(0.8)),

                        stars(200)).setBvh(),


                new Geometries(
                        //the sea
                        new Polygon(new Point3D(-500, -10, -1000), new Point3D(500, -10, -1000),
                                new Point3D(500, -100, 0), new Point3D(-500, -100, 0))
                                .setEmission(new Color(java.awt.Color.BLUE)).
                                setMaterial(new Material().setShininess(500).setKd(0.5).setKs(0.5).setKt(0.6).setKr(0.7)),
                        new Geometries(
                                new Geometries(
                                        //the front of the boat side
                                        new Polygon(new Point3D(-10, -35, -475), new Point3D(110, -35, -475),
                                                new Point3D(90, -55, -500), new Point3D(25, -55, -500))
                                                .setEmission(new Color(80, 50, 0)),

                                        //the back of the boat side
                                        new Polygon(new Point3D(-10, -35, -545), new Point3D(110, -35, -545),
                                                new Point3D(90, -55, -520), new Point3D(25, -55, -520))
                                                .setEmission(new Color(java.awt.Color.BLACK)),

                                        //the front of the boat
                                        new Polygon(new Point3D(25, -55, -500), new Point3D(25, -55, -520),
                                                new Point3D(-10, -35, -545), new Point3D(-10, -35, -475)).
                                                setEmission(new Color(java.awt.Color.darkGray)),

                                        //the back of the boat
                                        new Polygon(new Point3D(90, -55, -500), new Point3D(90, -55, -520),
                                                new Point3D(110, -35, -545), new Point3D(110, -35, -475)).
                                                setEmission(new Color(java.awt.Color.darkGray)),

                                        //the floor
                                        new Polygon(new Point3D(25, -55, -500), new Point3D(25, -55, -520),
                                                new Point3D(90, -55, -520), new Point3D(90, -55, -500))
                                                .setEmission(new Color(java.awt.Color.BLACK)),

                                        //the flash light in the front  of the boat
                                        new Sphere(1, new Point3D(8, -45, -515)).setEmission(new Color(java.awt.Color.white))
                                                .setMaterial(new Material().setKt(0.9)),

                                        new Sphere(1, new Point3D(8, -45, -505)).setEmission(new Color(java.awt.Color.white))
                                                .setMaterial(new Material().setKt(0.9))
                                        ).setBvh(),
                                new Geometries(
                                        //the mast (toren)
                                        new Polygon(new Point3D(60, -45, -510), new Point3D(60, 30, -510),
                                                new Point3D(61, 30, -510), new Point3D(61, -45, -510))
                                                .setEmission(new Color(java.awt.Color.BLACK)),

                                        //the flag
                                        new Triangle(new Point3D(61, 30, -510), new Point3D(61, 20, -510), new Point3D(50, 25, -510))
                                ).setBvh()
                        ).setBvh()
                ).setBvh()


        );

        scene.lights.addAll(List.of(
                //the light of the moon
                new PointLight(new Color(500, 500, 500), new Point3D(-50, 50, -1000)),
                //the light of the flash light in the front  of the boat
                new SpotLight(new Color(1000, 600, 0), new Point3D(8, -45, -515),
                        new Vector(-1, 0, -0)).setKl(0.0004).setKq(0.0000006),
                new SpotLight(new Color(1000, 600, 0), new Point3D(8, -45, -505),
                        new Vector(-1, 1, 2)).setKl(0.0004).setKq(0.0000006)

                )
        );

        Render render = new Render() //
                .setImageWriter(new ImageWriter("OurImage", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene))
                .setMultithreading(3).setDebugPrint();
        render.renderImage();
        render.writeToImage();

        //shifted image
        camera.transLocation(new Vector(20, 20, 0));
        render.setImageWriter(new ImageWriter("OurShiftingImage", 500, 500));
        render.renderImage();
        render.writeToImage();
    }
}

//package renderer;
//
//import elements.*;
//import geometries.Plane;
//import geometries.Polygon;
//import geometries.Sphere;
//import geometries.Triangle;
//import org.junit.jupiter.api.Test;
//import primitives.Color;
//import primitives.Material;
//import primitives.Point3D;
//import primitives.Vector;
//import scene.Scene;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class OurImage {
//    private Scene scene = new Scene("Our scene");
//
//    /**
//     * create stars (randomly in the sky)
//     *
//     * @param num number of the stars
//     */
//    void stars(int num) {
//        int x = 0;
//        int y = 0;
//        for (int i = 1; i <= num; ++i) {
//            x = (int) (Math.random() * 3000 - 1500);
//            y = (int) (Math.random() * 1500);
//            Point3D point = new Point3D(x, y, -20000);
//            scene.geometries.add(new Sphere(5, point).setEmission(new Color(255, 240, 240))
//                    .setMaterial(new Material().setKt(0.9)));
//        }
//    }
//
//    @Test
//    void OurImage() {
//        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setViewPlaneSize(150, 150).setDistance(1000).setSuperSampling(9);
//
//        //the sky
//        scene.setBackground(new Color(0, 0, 50));//.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
//
//        scene.geometries.setBvh().add(
//                //the sea
//                new Polygon(new Point3D(-500, -10, -1000), new Point3D(500, -10, -1000),
//                        new Point3D(500, -100, 0), new Point3D(-500, -100, 0))
//                        .setEmission(new Color(java.awt.Color.BLUE)).
//                        setMaterial(new Material().setShininess(500).setKd(0.5).setKs(0.5).setKt(0.6).setKr(0.7)),
//
//                //the moon
//                new Sphere(20, new Point3D(-50, 50, -1000)).setEmission(new Color(java.awt.Color.gray))
//                        .setMaterial(new Material().setKt(0.8)),
//
//                //the front of the boat side
//                new Polygon(new Point3D(-10, -35, -475), new Point3D(110, -35, -475),
//                        new Point3D(90, -55, -500), new Point3D(25, -55, -500))
//                        .setEmission(new Color(80, 50, 0)),
//
//                //the back of the boat side
//                new Polygon(new Point3D(-10, -35, -545), new Point3D(110, -35, -545),
//                        new Point3D(90, -55, -520), new Point3D(25, -55, -520))
//                        .setEmission(new Color(java.awt.Color.BLACK)),
//
//                //the front of the boat
//                new Polygon(new Point3D(25, -55, -500), new Point3D(25, -55, -520),
//                        new Point3D(-10, -35, -545), new Point3D(-10, -35, -475)).
//                        setEmission(new Color(java.awt.Color.darkGray)),
//
//                //the back of the boat
//                new Polygon(new Point3D(90, -55, -500), new Point3D(90, -55, -520),
//                        new Point3D(110, -35, -545), new Point3D(110, -35, -475)).
//                        setEmission(new Color(java.awt.Color.darkGray)),
//
//                //the floor
//                new Polygon(new Point3D(25, -55, -500), new Point3D(25, -55, -520),
//                        new Point3D(90, -55, -520), new Point3D(90, -55, -500))
//                        .setEmission(new Color(java.awt.Color.BLACK)),
//
//                //the mast (toren)
//                new Polygon(new Point3D(60, -45, -510), new Point3D(60, 30, -510),
//                        new Point3D(61, 30, -510), new Point3D(61, -45, -510))
//                        .setEmission(new Color(java.awt.Color.BLACK)),
//
//                //the flag
//                new Triangle(new Point3D(61, 30, -510), new Point3D(61, 20, -510), new Point3D(50, 25, -510)),
//
//
//                //the flash light in the front  of the boat
//                new Sphere(1, new Point3D(8, -45, -515)).setEmission(new Color(java.awt.Color.white))
//                        .setMaterial(new Material().setKt(0.9)),
//
//                new Sphere(1, new Point3D(8, -45, -505)).setEmission(new Color(java.awt.Color.white))
//                        .setMaterial(new Material().setKt(0.9))
//
//        );
//
//        scene.lights.addAll(List.of(
//                //the light of the moon
//                new PointLight(new Color(500, 500, 500), new Point3D(-50, 50, -1000)),
//                //the light of the flash light in the front  of the boat
//                new SpotLight(new Color(1000, 600, 0), new Point3D(8, -45, -515),
//                        new Vector(-1, 0, -0)).setKl(0.0004).setKq(0.0000006),
//                new SpotLight(new Color(1000, 600, 0), new Point3D(8, -45, -505),
//                        new Vector(-1, 1, 2)).setKl(0.0004).setKq(0.0000006)
//
//                )
//        );
//        stars(200);
//
//        Render render = new Render() //
//                .setImageWriter(new ImageWriter("OurImage", 500, 500)) //
//                .setCamera(camera) //
//                .setRayTracer(new BasicRayTracer(scene))
//                .setMultithreading(3).setDebugPrint();
//        render.renderImage();
//        render.writeToImage();
//
//        //shifted image
//        camera.transLocation(new Vector(20, 20, 0));
//        render.setImageWriter(new ImageWriter("OurShiftingImage", 500, 500));
//        render.renderImage();
//        render.writeToImage();
//    }
//}
