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

        int rByte = (int) (255.999 * r);
        int gByte = (int) (255.999 * g);
        int bByte = (int) (255.999 * b);

        out.print(rByte);
        out.print(" ");
        out.print(gByte);
        out.print(" ");
        out.print(bByte);
        out.print("\n");
    }
}
