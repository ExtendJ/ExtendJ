# Performance regression since Classpath handling update

**Status:** resolved

Since 69824d9d5591252ca9fd4b255ac54ba505682e82 we have had a performance regression in JastAddJ. I belive this is because classpath initialization now always happens before parsing sources.

Previously JastAddJ could skip initializing the classpath if an error was found early (for example during parsing), now it must initialize the classpath every time because the side-effect of initialization was undesired in attribute evaluation. Classpath initialization also appears to be a lot slower now.

The classpath initialization needs to be optimized - it has caused a large performance regression in our test suites.

## Comments

### Jesper Öqvist - 2014-01-13

Finally found the cause of the regression: the new code scans folders to build a package index. The old code simply tests if the sub-folder corresponding to the package exists and is non-empty.

Scanning all sub-folders for existing packages is too slow!

### Jesper Öqvist - 2014-01-14

Fixed in b0bbd08b5dca5a1f22927782d2186cde5a2ee512
