

double degreesToRadians(double degrees) {
    return degrees * Math.PI / 180.0;
}


double hitSphere(
        @Point Vec3 center,
        double radius,
        Ray r
) {
    var oc = center.minus(r.origin());
    var a = r.direction().lengthSquared();
    var h = r.direction().dotProduct(oc);
    var c = oc.lengthSquared() - radius * radius;
    var discriminant = h * h - a * c;
    if (discriminant < 0) {
        return -1;
    }
    else {
        return (h - Math.sqrt(discriminant)) / a;
    }
}

@Color Vec3 rayColor(Ray r, Hittable world) {
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

void main() {
    // Image

    var aspectRatio = 16.0 / 9.0;
    int imageWidth = 400;

    // Calculate the image height, and ensure that it's at least 1.
    int imageHeight = (int) (imageWidth / aspectRatio);
    imageHeight = Math.max(imageHeight, 1);

    // World
    var world = new HittableList();
    world.add(new Sphere(
            new Vec3(0, 0, -1),
            0.5
    ));
    world.add(new Sphere(
            new Vec3(0, -100.5, -1),
            100
    ));

    // Camera
    var focalLength = 1.0;
    var viewportHeight = 2.0;
    var viewportWidth = viewportHeight * (((double) imageWidth) / imageHeight);
    @Point Vec3 cameraCenter = new Vec3(0, 0, 0);

    // Calculate the vectors across the horizontal and down the vertical viewport edges.
    var viewportU = new Vec3(viewportWidth, 0, 0);
    var viewportV = new Vec3(0, -viewportHeight, 0);

    // Calculate the horizontal and vertical delta vectors from pixel to pixel.
    var pixelDeltaU = viewportU.divide(imageWidth);
    var pixelDeltaV = viewportV.divide(imageHeight);

    // Calculate the location of the upper left pixel.
    var viewportUpperLeft = cameraCenter
            .minus(new Vec3(0, 0, focalLength))
            .minus(viewportU.divide(2))
            .minus(viewportV.divide(2));

    var pixel00Loc = viewportUpperLeft.plus(
            pixelDeltaU.plus(pixelDeltaV).multiply(0.5)
    );

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

