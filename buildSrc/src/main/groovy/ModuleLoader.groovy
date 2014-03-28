class ModuleLoader {
	static void load(dir, name) {
		def source = new File(dir, name)
		if (!source.exists()) {
			throw new Error("Could not find module: ${dir}/${name}")
		}
		def code = source.text
		def closure = new GroovyShell().evaluate("{->${code}}")
		closure.delegate = new ModuleDefinitions(dir)
		closure()
	}

}
