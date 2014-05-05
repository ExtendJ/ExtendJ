class ModuleDefinitions {
	String directory = ""

	ModuleDefinitions(dir) {
		directory = dir
	}

	def module(name, closure) {
		def module = new JastAddModule(name)
		module.basedir = directory
		closure.delegate = module
		closure()
		module
	}
}

