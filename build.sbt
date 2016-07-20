lazy val akkaPersistenceExample = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)


libraryDependencies ++= Vector(
  Library.scalaTest % "test",
  Library.actor,
  Library.persistence,
  "org.iq80.leveldb" % "leveldb"          % "0.7",
  "org.fusesource.leveldbjni"% "leveldbjni-all" % "1.8"
)

initialCommands := """|import com.akkapersistenceexample._
                      |""".stripMargin
