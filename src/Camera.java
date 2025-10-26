public final class Camera {

    double aspectRatio = 1.0;
    int imageWidth = 100;

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
                var pixelCenter = pixel00Loc
                        .plus(pixelDeltaU.multiply(i))
                        .plus(pixelDeltaV.multiply(j));
                var rayDirection = pixelCenter.minus(cameraCenter);
                var r = new Ray(cameraCenter, rayDirection);

                @Color Vec3 pixelColor = rayColor(r, world);

                Colors.write(System.out, pixelColor);
            }
        }

        System.err.print("\rDone.                 \n");
    }


    private int imageHeight;
    private @Point Vec3 cameraCenter;
    private @Point Vec3 pixel00Loc;
    private Vec3 pixelDeltaU;
    private Vec3 pixelDeltaV;

    private void initialize() {
        int imageHeight = (int) (imageWidth / aspectRatio);
        this.imageHeight = Math.max(imageHeight, 1);

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

    private @Color Vec3 rayColor(Ray r, Hittable world) {
        var rec = world.hit(r, new Interval(0, Double.POSITIVE_INFINITY))
                .orElse(null);
        if (rec != null) {
            return rec.normal().plus(new Vec3(1, 1, 1)).multiply(0.5);
        }

        var unitDirection = r.direction().unitVector();
        var a = 0.5 * (unitDirection.y() + 1);
        return new Vec3(1, 1, 1).multiply(1.0 - a)
                .plus(new Vec3(0.5, 0.7, 1).multiply(a));
    }
}
