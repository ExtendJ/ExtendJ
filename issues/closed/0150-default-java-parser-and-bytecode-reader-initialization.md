# Default Java parser and bytecode reader initialization

**Status:** resolved

**ExtendJ 8.0.1**

When setting up a custom frontend for ExtendJ, one has to provide a Java parser and bytecode reader for ExtendJ to use. It would be convenient if default parsers could be initialized so that custom frontends kan skip that boilerplate setup code.

## Comments

### Jesper Öqvist - 2016-03-08

Add helper methods to build default parsers

fixes #150 (bitbucket)


→ <<cset b3ea8c6e8fbb>>
