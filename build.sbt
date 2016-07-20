lazy val akkaPersistenceExample = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)

libraryDependencies ++= Vector(
  Library.scalaTest % "test"
)

initialCommands := """|import com.akkapersistenceexample._
                      |""".stripMargin
