# Fail if destination directory does not exist

**Status:** resolved

Javac fails if we tell it to output classfiles in a destination directory that does not exist. JastAddJ currently has different behaviour - JastAddJ will create the destination directory and proceed as normal.

JastAddJ should behave as javac does in this case.

## Comments

### Sergiy Kolesnikov - 2014-01-09

I rely on this feature of JastAddJ in multiple scripts.  Please mention it in the Migration Guide, when you change it.

### Jesper Öqvist - 2014-01-14

Will do!

### Jesper Öqvist - 2014-01-14

Fail if output directory does not exist

JastAddJ no longer silently creates the output directory. If it does not exist
already then JastAddJ exits with an error message and the exit code 2.

fixes issue #36 (bitbucket)


→ <<cset 7e1d8efbb0e2>>
