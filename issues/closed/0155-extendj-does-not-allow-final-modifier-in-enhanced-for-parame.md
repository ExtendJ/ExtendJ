# ExtendJ does not allow final modifier in enhanced for parameter declaration

**Status:** resolved

**ExtendJ 8.0.1-94-g5d55e94**

ExtendJ does currently not accept the `final` modifier in an enhanced for parameter declaration:

```java
int[] ints = {};
for (final int i : ints) {
}```

## Comments

### Jesper Öqvist - 2016-03-11

Allow final modifier for enhanced for parameter

fixes #155 (bitbucket)


→ <<cset d723ef3f0dfc>>
