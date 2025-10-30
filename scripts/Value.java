void main() throws Exception {
    List<Path> files;
    try (var list = Files.list(Path.of("src/main/java/raytrace"))) {
        files = list.toList();
    }

    for (var file : files) {
        Files.writeString(file, Files.readString(file).replace("/* value */ ", "value "));
    }
}