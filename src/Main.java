double degreesToRadians(double degrees) {
    return degrees * Math.PI / 180.0;
}


void main() {
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

    var camera = new Camera();
    camera.imageWidth = 400;
    camera.aspectRatio = 16.0 / 9.0;

    camera.render(world);
}

