
value record Vec3(
        double x,
        double y,
        double z
) {
    Vec3() {
        this(0, 0, 0);
    }

    Vec3 withX(double x) {
        return new Vec3(x, y, z);
    }

    Vec3 withY(double y) {
        return new Vec3(x, y, z);
    }

    Vec3 withZ(double z) {
        return new Vec3(x, y, z);
    }

    Vec3 plus(Vec3 v) {
        return new Vec3(
                x + v.x, y + v.y, z + v.z
        );
    }

    Vec3 minus(Vec3 v) {
        return new Vec3(
                x - v.x, y - v.y, z - v.z
        );
    }

    Vec3 multiply(double t) {
        return new Vec3(
                x * t, y * t, z * t
        );
    }

    Vec3 divide(double t) {
        return multiply(1 / t);
    }

    double length() {
        return Math.sqrt(lengthSquared());
    }

    double lengthSquared() {
        return x * x + y * y + z * z;
    }

    double dotProduct(Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    Vec3 crossProduct(Vec3 v) {
        return new Vec3(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    Vec3 unitVector() {
        return this.divide(length());
    }
}