package raytrace;

interface Hittable {
    HitRecord hit(
            Ray r,
            Interval rayT
    );
}