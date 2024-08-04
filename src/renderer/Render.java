package renderer;

import elements.Camera;
import primitives.*;

import java.util.List;
import java.util.MissingResourceException;

/**
 * a class whose role is to create the color matrix of the image from the scene
 *
 * @author Naomi and Noam
 */
public class Render {

    /**
     * the image writer that write the image
     */
    private ImageWriter _imageWriter;

    /**
     * the camera that look to the scene
     */
    private Camera _camera;

    /**
     * the ray tracer that calculate colors in the image
     */
    private RayTracerBase _rayTracer;

    /**
     * the number of threads in the render
     */
    private int _threadsCount = 0;

    /**
     * flag to printing progress percentage
     */
    private boolean _print = false;


    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     * @author Dan
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        else {
            _threadsCount = threads;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     * @author Dan
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int _row = 0;
        public volatile int _col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = (long) maxRows * maxCols;
            _nextCounter = this._pixels / 100;
            if (Render.this._print)
//                System.out.printf("\r %02d%%", this._percents);
                System.out.println(this._percents + "%");
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         * if it is -1 - the task is finished, any other value - the progress
         * percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++_col;
            ++_counter;
            if (_col < this._maxCols) {
                target._row = this._row;
                target._col = this._col;
                if (Render.this._print && this._counter == this._nextCounter) {
                    ++this._percents;
                    this._nextCounter = this._pixels * (this._percents + 1) / 100;
                    return this._percents;
                }
                return 0;
            }
            ++_row;
            if (_row < this._maxRows) {
                _col = 0;
                target._row = this._row;
                target._col = this._col;
                if (Render.this._print && this._counter == this._nextCounter) {
                    ++this._percents;
                    this._nextCounter = this._pixels * (this._percents + 1) / 100;
                    return this._percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, false if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this._print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this._print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this._print)
                while (this._percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
//                        System.out.printf("\r %02d%%", this._percents);
                        System.out.println(this._percents + "%");
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    /**
     * setter
     * @param imageWriter the image writer that write the image
     * @return the object (changing method)
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     * setter
     * @param camera the camera that look to the scene
     * @return the object (changing method)
     */
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    /**
     * setter
     * @param rayTracer the ray tracer that calculate colors in the image
     * @return the object (changing method)
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracer = rayTracer;
        return this;
    }

    /**
     * Generate a image
     */
    public void renderImage() {
        //Check if all fields are not missing
        if (_imageWriter == null) {
            throw new MissingResourceException("The image writer is missing", ImageWriter.class.getName(), "");
        }

        if (_camera == null) {
            throw new MissingResourceException("The camera is missing", Camera.class.getName(), "");
        }


        if (_rayTracer == null) {
            throw new MissingResourceException("The ray tracer is missing", RayTracerBase.class.getName(), "");
        }

        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        if (_threadsCount == 0 )
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castBeam(nX, nY, j, i);
        else
            renderImageThreaded();
        }

    /**
     * calculate color of pixel by creating of beam rays through it.
     * @param nX number of pixels in row
     * @param nY number of pixels in column
     * @param col the column of current pixel
     * @param row the row of current pixel
     */
    private void castBeam(int nX, int nY, int col, int row) {
        List<Ray> beamRay = _camera.constructRayThroughPixel(nX, nY, col, row);//Cast beam from camera to pixel
        Color color = Color.BLACK;
        for (Ray ray : beamRay) {
            color = color.add(_rayTracer.traceRay(ray));//Sum of the colors of all the rays in beam
        }
        color = color.reduce(beamRay.size());
        _imageWriter.writePixel(col, row, color);// Color of the (j,i) pixel
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     * @author Dan
     */
    private void renderImageThreaded() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[_threadsCount];
        for (int i = _threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castBeam(nX, nY, pixel._col, pixel._row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (_print)
            System.out.print("\r100%");
    }

    /**
     * Drawing a grid of pixels
     *
     * @param interval size of pixel
     * @param color    of the grid
     */
    public void printGrid(int interval, Color color) {
        if (_imageWriter != null) {
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            for (int i = 0; i < nY; i++) {// go through all pixels
                for (int j = 0; j < nX; j++) {
                    if (i % interval == 0 || j % interval == 0) { //painting just the limits of pixel
                        _imageWriter.writePixel(j, i, color);
                    }
                }
            }
        } else {
            throw new MissingResourceException("The image writer is missing", ImageWriter.class.getName(), "");
        }

    }

    /**
     * Delegation of writeToImage of ImageWriter
     */
    public void writeToImage() {
        if (_imageWriter != null) {
            _imageWriter.writeToImage();
        } else {
            throw new MissingResourceException("", "", "");
        }
    }
}

