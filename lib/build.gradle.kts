
plugins {
    // Apply the scala Plugin to add support for Scala.
    scala

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // deequ will bring in scala and spark
    api(libs.deequ)

    // Use Scalatest for testing our library
    testImplementation(libs.junit)
//    testImplementation(libs.scalatest.v2.v13)
//    testImplementation(libs.junit.v4.v13.v2.v13)

    // Need scala-xml at test runtime
//    testRuntimeOnly(libs.scala.xml.v2.v13)

}

// removing the java tool chain stops the problem with:
//> Task :lib:compileScala FAILED
//        'jvm-1.21' is not a valid choice for '-target'
//bad option: '-target:jvm-1.21'

// Apply a specific Java toolchain to ease working on different environments.
//java {
//    toolchain {
//        languageVersion = JavaLanguageVersion.of(21)
//    }
//}

