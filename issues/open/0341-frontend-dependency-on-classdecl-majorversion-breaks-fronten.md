# Frontend dependency on `ClassDecl().majorVersion` breaks frontend-only builds

_ExtendJ version_ 11.0.0

Building projects with `build.gradle` set to include `java8 frontend` but not `java8 backend` as in:

```groovy
jastadd {
  modules {
    module "template", {
      imports "java8 frontend"
      //imports "java8 backend" // error if not included
} } }
```

fails with:

```
/build-path-to/minimal-template/build/generated-src/ast/org/extendj/ast/BytecodeParser.java:48: error: cannot find symbol
      supportedMajorVersion = new ClassDecl().majorVersion();
                                             ^
  symbol:   method majorVersion()
  location: class ClassDecl
```

This affects e.g. the ExtendJ `minimal-template`.  Possibly introduced in `c7c2727b`.  Seems to also affect `java11` and others.
The workaround is trivial, but it would be nice to be able to have frontend-only builds.

My proposed solution is to move the corresponding definitions from in `java${V}/backend/Version${V}.jrag` to frontend paths, unless I am missing a deeper reason for them being in the backend?
