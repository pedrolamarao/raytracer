import java.io.PrintStream;

public final class Colors {
    private Colors() {
    }

    private static double linear_to_gamma(double linear_component) {
        if (linear_component > 0) {
            return Math.sqrt(linear_component);
        } else {
            return 0;
        }
    }

    static void write(
            PrintStream out,
            @Color Vec3 pixelColor
    ) {
        var r = pixelColor.x();
        var g = pixelColor.y();
        var b = pixelColor.z();

        // Apply a linear to gamma transform for gamma 2
        r = linear_to_gamma(r);
        g = linear_to_gamma(g);
        b = linear_to_gamma(b);

        var intensity = new Interval(0.000, 0.999);

        int rByte = (int) (256 * intensity.clamp(r));
        int gByte = (int) (256 * intensity.clamp(g));
        int bByte = (int) (256 * intensity.clamp(b));

        out.print(rByte);
        out.print(" ");
        out.print(gByte);
        out.print(" ");
        out.print(bByte);
        out.print("\n");
    }
}
