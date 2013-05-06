libraryDependencies ++= Seq("org.specs2" %% "specs2" % "1.14" % "test")
  
resolvers ++= Seq(
    "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

resolvers += "Scala Tools Snapshots" at "http://scala-tools.org/repo-snapshots/"

libraryDependencies += "com.typesafe" %% "scalalogging-slf4j" % "1.0.1"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "6.0.4"


