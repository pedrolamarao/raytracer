public final class Camera {

    double aspectRatio = 1.0;
    int imageWidth = 100;
    int samplesPerPixel = 10;
    int maxDepth = 10;

    void render(Hittable world) {
        initialize();

        IO.print("P3\n");
        IO.print(imageWidth);
        IO.print(" ");
        IO.print(imageHeight);
        IO.print("\n255\n");

        for (int j = 0; j < imageHeight; j++) {
            System.err.print("\rScanlines remaining: ");
            System.err.print((imageHeight - j));
            System.err.print(" ");
            for (int i = 0; i < imageWidth; i++) {

                @Color Vec3 pixelColor = new Vec3(0, 0, 0);

                for (int sample = 0; sample < samplesPerPixel; sample++) {
                    var r = getRay(i, j);
                    pixelColor = pixelColor.plus(rayColor(r, maxDepth, world));
                }

                Colors.write(System.out, pixelColor.multiply(pixelSamplesScale));
            }
        }

        System.err.print("\rDone.                 \n");
    }


    private int imageHeight;
    double pixelSamplesScale;
    private @Point Vec3 cameraCenter;
    private @Point Vec3 pixel00Loc;
    private Vec3 pixelDeltaU;
    private Vec3 pixelDeltaV;

    private void initialize() {
        int imageHeight = (int) (imageWidth / aspectRatio);
        this.imageHeight = Math.max(imageHeight, 1);

        pixelSamplesScale = 1.0 / samplesPerPixel;

        this.cameraCenter = new Vec3(0, 0, 0);

        // Camera
        var focalLength = 1.0;
        var viewportHeight = 2.0;
        var viewportWidth = viewportHeight * (((double) imageWidth) / imageHeight);

        // Calculate the vectors across the horizontal and down the vertical viewport edges.
        var viewportU = new Vec3(viewportWidth, 0, 0);
        var viewportV = new Vec3(0, -viewportHeight, 0);

        // Calculate the horizontal and vertical delta vectors from pixel to pixel.
        this.pixelDeltaU = viewportU.divide(imageWidth);
        this.pixelDeltaV = viewportV.divide(imageHeight);

        // Calculate the location of the upper left pixel.
        var viewportUpperLeft = cameraCenter
                .minus(new Vec3(0, 0, focalLength))
                .minus(viewportU.divide(2))
                .minus(viewportV.divide(2));

        this.pixel00Loc = viewportUpperLeft.plus(
                pixelDeltaU.plus(pixelDeltaV).multiply(0.5)
        );
    }

    private Ray getRay(int i, int j) {
        // Construct a camera ray originating from the origin and directed at randomly sampled
        // point around the pixel location i, j.

        var offset = sampleSquare();
        var pixelSample = pixel00Loc
                .plus(pixelDeltaU.multiply(i + offset.x()))
                .plus(pixelDeltaV.multiply(j + offset.y()));
        var rayOrigin = cameraCenter;
        var rayDirection = pixelSample.minus(rayOrigin);

        return new Ray(rayOrigin, rayDirection);
    }

    private Vec3 sampleSquare() {
        // Returns the vector to a random point in the [-.5,-.5]-[+.5,+.5] unit square.
        return new Vec3(Math.random() - 0.5, Math.random() - 0.5, 0);
    }

    private @Color Vec3 rayColor(Ray r, int depth, Hittable world) {
        // If we've exceeded the ray bounce limit, no more light is gathered.
        if (depth <= 0) {
            return new Vec3(0,0,0);
        }


        var rec = world.hit(r, new Interval(0.001, Double.POSITIVE_INFINITY))
                .orElse(null);
        if (rec != null) {
            var direction = Vec3.randomOnHemisphere(rec.normal());
            return rayColor(new Ray(rec.p(), direction), depth - 1, world).multiply(0.5);
        }

        var unitDirection = r.direction().unitVector();
        var a = 0.5 * (unitDirection.y() + 1);
        return new Vec3(1, 1, 1).multiply(1.0 - a)
                .plus(new Vec3(0.5, 0.7, 1).multiply(a));
    }
}
