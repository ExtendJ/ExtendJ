import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.file.*

class JastAddPlugin implements Plugin<Project> {

	static def modules(list) {
		list.each { ModuleLoader.load it }
	}

	void apply(Project project) {
		project.task("jastaddTest") {
			doLast {
				def specFiles = project.files(
					Module.get(project.jastaddj.module).files(project, "jastadd")
				)
				specFiles.each{ println "${it}" }
			}
		}
		project.task("generateJava", dependsOn: [ "scanner", "parser" ]) {
			inputs.files { Module.get(project.jastaddj.module).files(project, "jastadd") }
			outputs.dir { project.file(project.jastaddj.genDir) }
			doLast {
				def outdir = project.jastaddj.genDir
				def specFiles = project.files(
					Module.get(project.jastaddj.module).files(project, "jastadd")
				)
				ant.mkdir(dir: project.file(outdir))
				ant.taskdef(name: "jastadd", classname: "org.jastadd.JastAddTask",
					classpath: "${project.jastaddj.toolsDir}/jastadd2.jar") { }
				ant.jastadd(
					package: project.jastaddj.astPackage,
					rewrite: true,
					beaver: true,
					noVisitCheck: true,
					noCacheCycle: true,
					outdir: project.file(outdir),
					defaultMap: "new org.jastadd.util.RobustMap(new java.util.HashMap())") {
					specFiles.addToAntBuilder(ant, "fileset", FileCollection.AntType.FileSet)
				}
			}
		}
		project.task("scanner") {
			inputs.files { Module.get(project.jastaddj.module).files(project, "scanner") }
			outputs.dir { project.file("${project.jastaddj.genDir}/scanner") }
			doLast {
				ant.mkdir(dir: "${project.jastaddj.tmpDir}/scanner")
				def specFiles = project.files(
					Module.get(project.jastaddj.module).files(project, "scanner")
				)
				ant.concat(destfile: "${project.jastaddj.tmpDir}/scanner/JavaScanner.flex",
					binary: true, force: false) {
					specFiles.addToAntBuilder(ant, "fileset", FileCollection.AntType.FileSet)
				}
				ant.mkdir(dir: "${project.jastaddj.genDir}/scanner")
				ant.taskdef(name: "jflex", classname: "JFlex.anttask.JFlexTask",
					classpath: "${project.jastaddj.toolsDir}/JFlex.jar")
				ant.jflex(file: "${project.jastaddj.tmpDir}/scanner/JavaScanner.flex",
					outdir: project.file("${project.jastaddj.genDir}/scanner"),
					nobak: true)
			}
		}

		project.task("parser") {
			inputs.files { Module.get(project.jastaddj.module).files(project, "parser") }
			outputs.dir { project.file("${project.jastaddj.genDir}/parser") }
			doLast {
				ant.mkdir(dir: project.file(project.jastaddj.tmpDir))
				def specFiles = project.files(
					Module.get(project.jastaddj.module).files(project, "parser")
				)
				ant.concat(destfile: "${project.jastaddj.tmpDir}/parser/JavaParser.all",
					binary: true, force: false) {
					specFiles.addToAntBuilder(ant, "fileset", FileCollection.AntType.FileSet)
				}
				ant.java(classname: "Main", fork: true) {
					classpath {
						pathelement(path: "${project.jastaddj.toolsDir}/JastAddParser.jar")
						pathelement(path: "${project.jastaddj.toolsDir}/beaver-rt.jar")
					}
					arg(value: "${project.jastaddj.tmpDir}/parser/JavaParser.all")
					arg(value: "${project.jastaddj.tmpDir}/parser/JavaParser.beaver")
				}
				ant.mkdir(dir: "${project.jastaddj.genDir}/parser")
				ant.taskdef(name: "beaver", classname: "beaver.comp.run.AntTask",
					classpath: "${project.jastaddj.toolsDir}/beaver-ant.jar")
				ant.beaver(file: "${project.jastaddj.tmpDir}/parser/JavaParser.beaver",
					destdir: "${project.jastaddj.genDir}/parser",
					terminalNames: true,
					compress: false,
					useSwitch: true)
			}
		}

		project.task("setSupportLevel") {
			outputs.dir { project.file(project.jastaddj.genResDir) }
			doLast {
				ant.mkdir dir: "${project.jastaddj.genResDir}"
				ant.propertyfile(file: "${project.jastaddj.genResDir}/JavaSupportLevel.properties") {
					entry(key: "javaVersion", value: "${project.jastaddj.javaVersion}")
				}
			}
		}

		project.task("cleanGen", type: Delete) {
			delete { project.jastaddj.genDir }
			delete { project.jastaddj.genResDir }
		}

		project.clean.dependsOn 'cleanGen'
		project.compileJava.dependsOn 'generateJava'
		project.processResources.dependsOn 'setSupportLevel'

		project.extensions.create("jastaddj", JastAddJExtension)
	}

}

class JastAddJExtension {
	/** module name */
	String module

	/** supported Java version */
	String javaVersion

	String astPackage
	String sourceDir
	String toolsDir
	String resourceDir
	String binDir
	String tmpDir
	String genDir
	String genResDir
}

