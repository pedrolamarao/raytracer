import java.util.Optional;

value class Metal implements Material {
    @Color Vec3 albedo;

    Metal(@Color Vec3 albedo) {
        this.albedo = albedo;
    }

    @Override
    public Optional<ScatteredRay> scatter(Ray r_in, HitRecord rec) {
        var reflected = Vec3.reflect(r_in.direction(), rec.normal());
        var scattered = new Ray(rec.p(), reflected);
        return Optional.of(new ScatteredRay(
                albedo,
                scattered
        ));
    }
}
