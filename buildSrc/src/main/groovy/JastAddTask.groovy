import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.file.FileCollection

class JastAddTask extends DefaultTask {
	def astPackage
	def outdir
	def specFiles

	@TaskAction
	def jastadd() {
		ant.mkdir(dir: project.file(outdir))
		println"${project.ext.toolsDir}/jastadd2.jar"
		ant.taskdef(name: 'jastadd', classname: 'org.jastadd.JastAddTask',
			classpath: "${project.ext.toolsDir}/jastadd2.jar") { }
		ant.jastadd(package: astPackage,
			rewrite:true, beaver: true, noCacheCycle: true, outdir: project.file(outdir),
			defaultMap: 'new org.jastadd.util.RobustMap(new java.util.HashMap())') {
			specFiles.addToAntBuilder(ant, 'fileset', FileCollection.AntType.FileSet)
		}
	}
}

