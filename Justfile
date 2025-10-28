run:
    zig c++ -O3 reference/src/InOneWeekend/main.cc
    time ./a.out > image_ref_optimized.ppm
    rm -rf build/classes
    javac -Xlint:preview --enable-preview --release 26 src/Main.java --source-path src -d build/classes
    time java --enable-preview --class-path build/classes Main > image.ppm
    zig c++ reference/src/InOneWeekend/main.cc
    time ./a.out > imageref.ppm
