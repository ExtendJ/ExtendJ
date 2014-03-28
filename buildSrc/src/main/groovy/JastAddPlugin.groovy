import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.file.*

class JastAddPlugin implements Plugin<Project> {

	static def modules(clos) {
		clos.delegate = ModuleLoader
		clos()
	}

	void apply(Project project) {
		project.task("gen", dependsOn: [ "scanner", "parser" ]) << {
			def outdir = project.jastaddj.genDir
			def specFiles = project.files(
				Module.get(project.jastaddj.module).files(project, "jastadd")
			)
			ant.mkdir(dir: project.file(outdir))
			ant.taskdef(name: "jastadd", classname: "org.jastadd.JastAddTask",
				classpath: "${project.jastaddj.toolsDir}/jastadd2.jar") { }
			ant.jastadd(package: project.jastaddj.astPackage,
				rewrite:true, beaver: true, noCacheCycle: true, outdir: project.file(outdir),
				defaultMap: "new org.jastadd.util.RobustMap(new java.util.HashMap())") {
				specFiles.addToAntBuilder(ant, "fileset", FileCollection.AntType.FileSet)
			}
		}
		project.task("scanner") << {
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

		project.task("parser") << {
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

		project.task("jar", dependsOn: "build") << {
			ant.jar(destfile: "jastaddj.jar") {
				manifest {
					attribute(name: "Main-Class", value: "org.jastadd.jastaddj.JavaCompiler")
				}
				fileset(dir: project.jastaddj.binDir) {
					include(name: "**/*")
				}
			}
		}

		project.task("setSupportLevel") << {
			ant.propertyfile(file: "${project.jastaddj.genDir}/JavaSupportLevel.properties") {
				entry(key: "javaVersion", value: "${project.jastaddj.javaVersion}")
			}
		}

		project.extensions.create("jastaddj", JastAddJExtension)
	}

}

