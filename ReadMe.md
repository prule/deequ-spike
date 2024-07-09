# Deequ Spike

This is a sample project which uses a Kotlin application to call a Scala library which exercises Deequ with Spark.

From the [Deequ](https://github.com/awslabs/deequ) repository
- Deequ is a library built on top of Apache Spark for defining "unit tests for data", which measure data quality in large datasets.
- Most applications that work with data have implicit assumptions about that data, e.g., that attributes have certain types, do not contain NULL values, and so on. If these assumptions are violated, your application might crash or produce wrong outputs. The idea behind deequ is to explicitly state these assumptions in the form of a "unit-test" for data, which can be verified on a piece of data at hand.

This repository is an experiment to set up a main Kotlin module where the application will reside, and a Scala library where the Deequ/Spark code will reside. Since Deequ and Spark are Scala first it might make it easier to use Scala for these parts.

See 
- [./app/src/main/kotlin/org/example/App.kt](./app/src/main/kotlin/org/example/App.kt)
- [./lib/src/main/scala/org/example/Spike1.scala](./lib/src/main/scala/org/example/Spike1.scala)

```
├── app (kotlin - calls the library)
│   ├── build.gradle.kts
│   └── src
│       ├── main
│       │   ├── kotlin
│       │       └── org
│       │           └── example
│       │               └── App.kt
├── lib (scala - invokes deequ)
│   ├── build.gradle.kts
│   └── src
│       ├── main
│       │   └── scala
│       │       └── org
│       │           └── example
│       │               └── Spike1.scala
```

The main dependencies are:
```
\--- com.amazon.deequ:deequ:2.0.7-spark-3.5
     +--- org.scala-lang:scala-library:2.12.10 -> 2.12.18
     +--- org.apache.spark:spark-core_2.12:3.5.0
```

To run the application use
```
./gradlew clean app:run
```

When it starts it will print out the important versions:

```
% ./gradlew clean app:run

> Task :app:run

----------------------------------------------
Java version: 21.0.3
Kotlin version: 2.0.0
Scala version: version 2.12.18
----------------------------------------------
...
24/07/09 22:16:16 INFO SparkContext: Running Spark version 3.5.0
24/07/09 22:16:16 INFO SparkContext: OS info Mac OS X, 14.5, aarch64
```

Notes:

- Spark and Scala dependencies are inherited from Deequ.
- I see an error in the logs which don't seem to harm anything but need looking into
```
  Caused by: org.codehaus.commons.compiler.CompileException: File 'generated.java', Line 102, Column 1: failed to compile: org.codehaus.commons.compiler.CompileException: File 'generated.java', Line 102, Column 1: Expression "isNull_6" is not an rvalue
``` 