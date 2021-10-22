import ProjectVersions.rlVersion

buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    java
}
allprojects {
    group = "com.openosrs.externals"
    apply<MavenPublishPlugin>()
}

allprojects {
    apply<MavenPublishPlugin>()

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }
}

apply<BootstrapPlugin>()
apply<VersionPlugin>()

subprojects {
    group = "com.ben93riggs.externals"

    project.extra["PluginProvider"] = "ben93riggs"
    project.extra["ProjectUrl"] = "https://discord.gg/mgHtrgr"
    project.extra["PluginLicense"] = "3-Clause BSD License"

    repositories {
        jcenter {
            content {
                excludeGroupByRegex("com\\.openosrs.*")
            }
        }

        exclusiveContent {
            forRepository {
                mavenLocal()
            }
            filter {
                includeGroupByRegex("com\\.openosrs.*")
            }
        }
    }

    apply<JavaPlugin>()
    apply<JavaLibraryPlugin>()

    dependencies {
        annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.12")
        annotationProcessor(group = "org.pf4j", name = "pf4j", version = "3.2.0")
        implementation(group = "com.google.inject", name = "guice", version = "4.2.3", classifier = "no_aop")
        implementation(group = "com.openosrs", name = "http-api", version = "$rlVersion+")
        implementation(group = "com.openosrs", name = "runelite-api", version = "$rlVersion+")
        implementation(group = "com.openosrs", name = "runelite-client", version = "$rlVersion+")
        implementation(group = "com.openosrs.rs", name = "runescape-api", version = "$rlVersion+")
        implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "4.5.0")
        implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "4.5.0")
        implementation(group = "io.reactivex.rxjava3", name = "rxjava", version = "3.0.2")
        implementation(group = "net.sf.jopt-simple", name = "jopt-simple", version = "5.0.4")
        implementation(group = "org.apache.commons", name = "commons-text", version = "1.8")
        implementation(group = "org.pf4j", name = "pf4j", version = "3.2.0")
        implementation(group = "org.projectlombok", name = "lombok", version = "1.18.12")
        implementation(group = "org.pushing-pixels", name = "radiance-substance", version = "2.5.1")
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        withType<Jar> {
             doLast {
                 copy {
                     from("./build/libs/")
                     into("../release/")
                 }
             }
         }

        withType<Jar> {
            doLast {
                copy {
                    from("./build/libs/")
                    into(System.getProperty("user.home") + "/.openosrs/plugins")
                }
            }
        }

        withType<AbstractArchiveTask> {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
            dirMode = 493
            fileMode = 420
        }
    }
}