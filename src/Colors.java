import java.io.PrintStream;

public final class Colors {
    private Colors() {}

    static void write(
            PrintStream out,
            @Color Vec3 pixelColor
    ) {
        var r = pixelColor.x();
        var g = pixelColor.y();
        var b = pixelColor.z();

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
