plugins {
    id("application")
}

tasks.withType<JavaCompile> {
    javaCompiler = javaToolchains.compilerFor {
        languageVersion = JavaLanguageVersion.of(26)
        version = "26-jep401ea2+1-1"
        vendor = JvmVendorSpec.ORACLE
    }
    options.isIncremental = false
    options.compilerArgs = listOf("--enable-preview")
}