

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
    int image_width = 256;
    int image_height = 256;

    IO.print("P3\n");
    IO.print(image_width);
    IO.print(" ");
    IO.print(image_height);
    IO.print("\n255\n");

    for (int j = 0; j < image_height; j++) {
        for (int i = 0; i < image_width; i++) {
            var r = ((double) i) / (image_width - 1);
            var g = ((double) i) / (image_height - 1);
            var b = 0.0;

            int ir = (int) (255.999 * r);
            int ig = (int) (255.999 * g);
            int ib = (int) (255.999 * b);

            Colors.write(System.out, pixelColor);
        }
    }
}
