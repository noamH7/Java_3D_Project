package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import elements.Camera;
import elements.PointLight;
import geometries.Polygon;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

public class Try {
    private Scene scene = new Scene("Our try scene");

    void stars(int num) {
        int x = 0;
        int y = 0;
        for (int i = 1; i <= num; i++) {
            x = (int) (Math.random() * 3001 - 1500);
            y = (int) (Math.random() * 1500);
            Point3D point = new Point3D(x, y, -17500);
            scene.geometries.add(new Sphere(5, point).setEmission(new Color(255, 255, 255))
                    .setMaterial(new Material().setKt(1)));
            scene.lights.add(new PointLight(new Color(500, 500, 500), point));
        }
    }

    @Test
    void OurImage() {
        {
            Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                    .setViewPlaneSize(150, 150).setDistance(1000).setSuperSampling(1);

            //the sky
            scene.setBackground(new Color(0, 0, 50));//.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

//            scene.geometries.setBvh().add(
//                    //the sea
//                    new Polygon(new Point3D(-500, -10, -1000), new Point3D(500, -10, -1000),
//                            new Point3D(500, -100, 0), new Point3D(-500, -100, 0))
//                            .setEmission(new Color(java.awt.Color.BLUE)).
//                            setMaterial(new Material().setShininess(500).setKd(0.5).setKs(0.5).setKt(0.6).setKr(0.7)),
//
//                    //the moon
//                    new Sphere(20, new Point3D(-50, 50, -1000)).setEmission(new Color(java.awt.Color.gray))
//                            .setMaterial(new Material().setKt(0.8)),
//
//                    //the front of the boat side
//                    new Polygon(new Point3D(-10, -35, -475), new Point3D(110, -35, -475),
//                            new Point3D(90, -55, -500), new Point3D(25, -55, -500))
//                            .setEmission(new Color(80, 50, 0)),
//
//                    //the back of the boat side
//                    new Polygon(new Point3D(-10, -35, -545), new Point3D(110, -35, -545),
//                            new Point3D(90, -55, -520), new Point3D(25, -55, -520))
//                            .setEmission(new Color(java.awt.Color.BLACK)),
//
//                    //the front of the boat
//                    new Polygon(new Point3D(25, -55, -500), new Point3D(25, -55, -520),
//                            new Point3D(-10, -35, -545), new Point3D(-10, -35, -475)).
//                            setEmission(new Color(java.awt.Color.darkGray)),
//
//                    //the back of the boat
//                    new Polygon(new Point3D(90, -55, -500), new Point3D(90, -55, -520),
//                            new Point3D(110, -35, -545), new Point3D(110, -35, -475)).
//                            setEmission(new Color(java.awt.Color.darkGray)),
//
//                    //the floor
//                    new Polygon(new Point3D(25, -55, -500), new Point3D(25, -55, -520),
//                            new Point3D(90, -55, -520), new Point3D(90, -55, -500))
//                            .setEmission(new Color(java.awt.Color.BLACK)),
//
//                    //the mast (toren)
//                    new Polygon(new Point3D(60, -45, -510), new Point3D(60, 30, -510),
//                            new Point3D(61, 30, -510), new Point3D(61, -45, -510))
//                            .setEmission(new Color(java.awt.Color.BLACK)),
//
//                    //the flag
//                    new Triangle(new Point3D(61, 30, -510), new Point3D(61, 20, -510), new Point3D(50, 25, -510)),
//
//
//                    //the flash light in the front  of the boat
//                    new Sphere(1, new Point3D(8, -45, -515)).setEmission(new Color(java.awt.Color.white))
//                            .setMaterial(new Material().setKt(0.9)),
//
//                    new Sphere(1, new Point3D(8, -45, -505)).setEmission(new Color(java.awt.Color.white))
//                            .setMaterial(new Material().setKt(0.9)),
//
//                new Sphere(500, new Point3D(0,-0,-500)).setEmission(new Color(0,0,0))
//                .setMaterial(new Material().setKt(1).setKr(0.5)
//            )
//
//
//            );
            scene.geometries.setBvh().add(

                    new Sphere(500, new Point3D(0, -0, -500)).setEmission(new Color(0, 0, 0))
                            .setMaterial(new Material().setKt(1).setKr(0.5)),

                    //the moon and stars
                    new Sphere(20, new Point3D(-50, 50, -1000)).setEmission(new Color(java.awt.Color.gray))
                            .setMaterial(new Material().setKt(0.8)),

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
//            stars(200);

            Render render = new Render() //
//                    .setImageWriter(new ImageWriter("OurTryImage", 500, 500)) //
                    .setCamera(camera) //
                    .setRayTracer(new BasicRayTracer(scene))
                    .setMultithreading(3).setDebugPrint();
//            render.renderImage();
//            render.writeToImage();

            //shifted image
            camera.transLocation(new Vector(0, 0, 5000));
            render.setImageWriter(new ImageWriter("OurTryShiftedImage", 500, 500));
            render.renderImage();
            render.writeToImage();
        }
    }
//        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(150, 150).setDistance(1000);
//
//        scene.setBackground(new Color(0, 0, 50));
////                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
//
//        scene.geometries.add( //
//                new Polygon(new Point3D(-500, -10, -1000), new Point3D(500, -10, -1000),
//                        new Point3D(500, -100, 0), new Point3D(-500, -100, 0))
//                        .setEmission(new Color(java.awt.Color.BLUE)).
//                        setMaterial(new Material().setShininess(500).setKd(0.5).setKs(0.5).setKt(0.6).setKr(0.7)),
////                new Sphere(10, new Point3D(-1500, 1500, -17500)).setEmission(new Color(java.awt.Color.white))
////                        .setMaterial(new Material().setKt(0.9)),
//                new Sphere(20, new Point3D(-50, 50, -1000)).setEmission(new Color(java.awt.Color.gray))
//                        .setMaterial(new Material().setKt(0.8)),
//                new Polygon(new Point3D(30, -55, -500), new Point3D(70, -55, -500),
//                        new Point3D(110, -35, -475), new Point3D(-10, -35, -475))
//                        .setEmission(new Color(java.awt.Color.darkGray)),
//                new Sphere(1, new Point3D(15, -40, -483)).setEmission(new Color(java.awt.Color.white))
//                        .setMaterial(new Material().setKt(0.9)),
//                new Polygon(new Point3D(-10, -35, -545), new Point3D(110, -35, -545),
//                        new Point3D(70, -55, -520), new Point3D(30, -55, -520))
//                        .setEmission(new Color(java.awt.Color.BLACK)),
//                new Sphere(20, new Point3D(-70, -45, -450))
//                        .setEmission(new Color(80, 50, 0)),
//                new Polygon(new Point3D(-70, -60, -450), new Point3D(-150, -60, -450),
//                        new Point3D(-150, -26, -450), new Point3D(-70, -26, -450))
//                        .setEmission(new Color(80, 50, 0)),
//                new Polygon(new Point3D(-80, -25, -470), new Point3D(-80, -25, -400),
//                        new Point3D(-60, -25, -430), new Point3D(-60, -25, -500))
//                        .setEmission(new Color(0, 0, 0)),
//                new Polygon(new Point3D(-80, -25, -470), new Point3D(-80, -20, -470),
//                        new Point3D(-60, -20, -500), new Point3D(-60, -25, -500))
//                        .setEmission(new Color(50, 0, 0)),
//                new Polygon(new Point3D(-80, -25, -470), new Point3D(-80, -25, -400),
//                        new Point3D(-80, -20, -400), new Point3D(-80, -20, -470))
//                        .setEmission(new Color(50, 30, 0)),
//                new Polygon(new Point3D(-80, -25, -400), new Point3D(-80, -20, -400),
//                        new Point3D(-60, -20, -430), new Point3D(-60, -25, -430))
//                        .setEmission(new Color(50, 30, 20)),
//                new Polygon(new Point3D(-60, -20, -430), new Point3D(-60, -25, -430),
//                        new Point3D(-60, -25, -500), new Point3D(-60, -20, -500))
//                        .setEmission(new Color(70, 30, 20)),
//                new Triangle(new Point3D(-75,-25, -440),new Point3D(-65,-25, -440), new Point3D(-70,-15, -440))
//                .setEmission(new Color(java.awt.Color.orange)).setMaterial(new Material().setShininess(30).setKs(0.7).setKd(0.5)),
//
//                //
//        new Polygon(new Pont3D(84, -32, -512), new Point3D(89, -32, -512),
//                new Point3D(89, -28, -512), new Point3D(84, -28, -512))
//                .setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKt(0.9)),
//
//                new Polygon(new Point3D(84, -32, -508), new Point3D(89, -32, -508),
//                        new Point3D(89, -28, -508), new Point3D(84, -28, -508))
//                        .setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKt(0.5)),
//
//
//                new Polygon(new Point3D(84, -32, -512), new Point3D(84, -28, -512),
//                        new Point3D(84, -28, -508),new Point3D(84, -32, -508))
//                        .setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKt(0.5)),
//
//                new Polygon( new Point3D(89, -32, -508),new Point3D(89, -28, -508),
//                        new Point3D(89, -28, -512),new Point3D(89, -32, -512))
//                        .setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKt(0.5)),
//
//                new Polygon(new Point3D(84, -32, -512),new Point3D(89, -32, -512),
//                        new Point3D(89, -32, -508),new Point3D(84, -32, -508))
//                        .setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKt(0.5)),
//
//
//                new Polygon(new Point3D(84, -28, -512),new Point3D(89, -28, -512),
//                        new Point3D(89, -28, -508),new Point3D(84, -28, -508))
//                        .setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKt(0.5))
////
//
////                new Sphere(50, new Point3D(0, 0, -150)) //
////                        .setEmission(new Color(java.awt.Color.BLUE)) //
////                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
////                new Sphere(25, new Point3D(0, 0, -50)) //
////                        .setEmission(new Color(java.awt.Color.RED)) //
////                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
//        );
////        scene.lights.add( //
////                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
////                        .setKl(0.0004).setKq(0.0000006));
//
//        scene.lights.addAll(List.of(
////                new PointLight(new Color(500, 500, 500), new Point3D(-1500, 1500, -17500)),
//                new PointLight(new Color(500, 500, 500), new Point3D(-50, 50, -1000))
////                new DirectionalLight(new Color(500, 500, 500), new Vector(-1, 1, -1))
////                new SpotLight(new Color(1000, 600, 0), new Point3D(15, -40, -483),
////                        new Vector(-1, -1, -2)).setKl(0.0004).setKq(0.0000006)
//        ));
//        stars(200);
//
//        Render render = new Render() //
//                .setImageWriter(new ImageWriter("OurTryImage", 500, 500)) //
//                .setCamera(camera) //
//                .setRayTracer(new BasicRayTracer(scene));
//        render.renderImage();
//        render.writeToImage();
//
//    }
}
//         new Sphere(1, new Point3D(8, -45, -488)).setEmission(new Color(java.awt.Color.YELLOW)).setMaterial(new Material().setKt(0.9)),
//                 new Sphere(1, new Point3D(-9, -45, -508)).setEmission(new Color(java.awt.Color.YELLOW)).setMaterial(new Material().setKt(0.9))