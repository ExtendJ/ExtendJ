# Incorrect Diamond inference for nested inferred calls

In Java8 mode diamond access will infer incorrect bounds when an argument of another generic.

Following snippet from Jerkins (core/src/main/java/hudson/XmlFile.java):
`private static final Map<Object, Void> beingWritten = Collections.synchronizedMap(new IdentityHashMap<>());`

Expected: Infers a return type of `Map<Object, Void>`.

Actual: Infers a return type of `Map<Object, Object>`.

NOTE: Possibly related to  #267 ?

## Comments

### Jesper Öqvist - 2018-01-29

This issue is similar to #267 because it is another case where ExtendJ does not handle nested poly expressions well. The problem is really a core problem in the type inference in ExtendJ and is not easy to fix. However, I want to fix it because many Java 8 applications fail to compile because ExtendJ has too weak type inference.

I'm working on a solution in a separate branch ([typeinf](https://bitbucket.org/extendj/extendj/commits/branch/typeinf)). I have to redesign the way type inference is done to be able to include more constraints during the type inference of nested inference expressions. Many problematic cases already work in my work-in-progress solution, but some tricky cases still remain to be fixed. The example in this issue compiles fine in the current typeinf branch.
