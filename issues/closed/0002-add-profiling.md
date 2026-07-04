# Add profiling

**Status:** resolved

It would be useful if JastAddJ could produce profiling data useful for benchmarking. The following profile data would be a good start:

* Total time spent parsing Java
* Total time spent parsing bytecode
* Total time spent checking for errors
* Total time spent generating code
* Number of Java files parsed
* Number of class files parsed

## Comments

### Jesper Öqvist - 2013-05-22

Added -profile option

fixes #2


→ <<cset 2cdfc8eddc00>>
