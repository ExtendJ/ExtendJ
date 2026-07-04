# Poor handling of long jumps in bytecode generation

**ExtendJ 8.0.1-43-gf1875d6 Java SE 8**

When a conditional statement, e.g. a while loop, has a very large conditional block, the generated jump offset may not fit inside two bytes and the code generation has to change slightly to handle this case.

Currently large conditional jumps are handled poorly by ExtendJ and we should implement a fix for this in the code generation.

An example of a problematic statement:

```java
while (a > b) {
  f();
  f();
  ...
  f();
}
```

If the bytecode for the above statement uses this scheme (as in ExtendJ currently):

```
iload_1
iload_2
if_icomple   <OFFSET>
invokestatic f()
invokestatic f()
...
invokestatic f()
```

The offset may not fit in the two-byte offset of the `if_icomple` instruction. Instead bytecode can be generated like this:

```
if _icmpgt  L1
goto_w <OFFSET>
invokestatic f()
...
```

This means we are not restricted by the two byte limit of the `if_icomp*` instruction, but instead the 4 byte limit of `goto_w`.

ExtendJ already tries to alter code generation to use wide gotos when it fails to generate code after an initial try. We could switch to the alternative code generation scheme after the first code generation attempt failed.
