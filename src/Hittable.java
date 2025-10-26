import java.util.Optional;

interface Hittable {
    Optional<HitRecord> hit(
            Ray r,
            Interval rayT
    );
}