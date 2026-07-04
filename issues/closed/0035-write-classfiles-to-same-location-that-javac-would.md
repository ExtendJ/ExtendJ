# Write classfiles to same location that javac would

**Status:** resolved

If no output directory is given to `javac` and we compile a source file in the working directory, then the generated classfile is also placed in the working directory regardless of the package declaration in the source file.

JastAddJ on the other hand will create directories matching the package declaration in the source file and generate the file there.

## Comments

### Jesper Öqvist - 2013-12-18

It seems to be so that `javac` generates classfiles in the same directory as their respective source files rather than any other directory if no destination directory is given to `javac` using the `-d` option.

### Jesper Öqvist - 2013-12-18

Changed destinationPath semantics

- classfiles are now generated in the same directory as their corresponding
  source files if the destination path option (`-d`) was not explicitly set

fixes #35 (bitbucket)


→ <<cset 6326d674d672>>
