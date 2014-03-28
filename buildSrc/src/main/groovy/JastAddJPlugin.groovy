import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.file.*

class JastAddJPlugin implements Plugin<Project> {

	void apply(Project project) {
		project.task("resources", type: Copy, dependsOn: "setSupportLevel") {
			from "${project.jastaddj.resourceDir}"
			from "${project.jastaddj.genDir}", {
				include "**/*.properties"
			}
			into project.jastaddj.binDir
		}

		project.task("clean", type: Delete) {
			delete project.jastaddj.genDir
			delete project.jastaddj.tmpDir
			delete project.jastaddj.binDir
		}
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
}

