import java.util.Optional;

value class Dialectric implements Material {
    double refractionIndex;

    Dialectric(double refractionIndex) {
        this.refractionIndex = refractionIndex;
    }

    @Override
    public Optional<ScatteredRay> scatter(Ray r_in, HitRecord rec) {
        var attenuation = new Vec3(1.0, 1.0, 1.0);
        double ri = rec.frontFace() ? (1.0/refractionIndex) : refractionIndex;

        var unit_direction = r_in.direction().unitVector();
        var refracted = Vec3.refract(unit_direction, rec.normal(), ri);

        var scattered = new Ray(rec.p(), refracted);
        return Optional.of(new ScatteredRay(
                attenuation,
                scattered
        ));
    }
}
