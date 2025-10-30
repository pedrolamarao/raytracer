plugins {
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    implementation("org.openjdk.jmh:jmh-core:1.37")
}

application {
    mainClass = "raytrace.Main"
}

tasks.compileJava.configure {
    javaCompiler = javaToolchains.compilerFor {
        languageVersion = JavaLanguageVersion.of(26)
        version = "26-jep401ea2+1-1"
        vendor = JvmVendorSpec.ORACLE
    }
    options.isIncremental = false
    options.compilerArgs = listOf("--enable-preview")
}

tasks.run.configure {
    javaLauncher = javaToolchains.launcherFor {
        languageVersion = JavaLanguageVersion.of(26)
        version = "26-jep401ea2+1-1"
        vendor = JvmVendorSpec.ORACLE
    }
    jvmArgs = listOf("--enable-preview")
}