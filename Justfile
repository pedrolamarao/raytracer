run:
    @rm -rf build/classes
    @javac -Xlint:preview --enable-preview --release 26 src/Main.java --source-path src -d build/classes
    java --enable-preview --class-path build/classes Main > image.ppm
