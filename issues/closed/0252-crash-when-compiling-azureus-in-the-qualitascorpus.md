# Crash when compiling Azureus in the QualitasCorpus

**Status:** duplicate

ExtendJ 8.0.1-220-gfec29ac Java SE 8

When compiling the Azureus project from the QualitasCorpus ExtendJ crashes with a NullpointerException. The stacktrace is attached in the issue.

I compiled using:
java -Xss1024m -Xms4096m -jar extendj.jar @systems_10\azureus-4.8.1.2

## Attachments

- [crash3.txt](../attachments/252/crash3.txt) (uploaded by Sebastian Hjelm)

## Comments

### Jesper Öqvist - 2017-12-29

This crash seems to be caused by the same issue as #251

### Jesper Öqvist - 2017-12-29

Duplicate of #251.
