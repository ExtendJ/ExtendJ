# Refactor Classpath Handling

The current classpath handling in JastAddJ has some problems:

* `PathPart` and its subtypes are mutable
* `CompilationUnit` instances lack information about which `PathPart` they were loaded from
* The class loading process is not obvious
* Input streams are leaked

The current class loading process:

1. Find first source file that can provide the class. This opens an input stream for the file and saves a reference in the corresponding PathPart instance.
2. Find first bytecode file that provides the same class. This opens another input stream.
3. Select one of the sources based on source file age and precedence.
4. Parse from the selected source using the opened input stream. This may leak the input stream for the unused source since it is not closed (at least in the current implementation).

## Comments

### Jesper Öqvist - 2013-12-12

Related problems:

* `Program.isPackage(String)` iterates through all path parts seeking after one that has the package. It would be more efficient to have a package index in `Program`.
* `Program.getCompilationUnit(String)` searches through source files, which seems redundant because they have already been parsed into the program. If we must lookup already parsed compilation units then there should be a compilation unit hash map to use instead.
* `Program.initPaths()` is called for every new compilation unit lookup. We should just initialize the paths once before starting to parse and compile. This would remove the need to keep track of whether the paths were initialized or not.

### Jesper Öqvist - 2017-04-24

Most of these issues have been fixed, but the class loading process still needs to be documented somewhere.
