# Broken bytecode for nested try statements

**Status:** resolved

*ExtendJ 8.0.1-240-g3d86145 Java SE 8*

Broken bytecode is generated for this test:

```java
// Test try-statement control flow.
public class Test {
  public static void main(String[] args) throws Exception {
    System.out.println(new Test().run());
  }

  public int run() throws Exception {
    int result = -1;
    try {
      try {
        return result;
      } finally {
        result = 0;
      }
    } catch(Exception ex) {
      return result;
    }
  }
}
```

Expected result: should print "-1"

Actual result: fails to run:

```
    [junit] [FAIL] runTest[run/try_06](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Error: A JNI error has occurred, please check your installation and try again
    [junit] Exception in thread "main" java.lang.VerifyError: Expecting a stackmap frame at branch target 15
    [junit] Exception Details:
    [junit]   Location:
    [junit]     Test.run()I @2: iload_2
    [junit]   Reason:
    [junit]     Expected stackmap frame at this location.
    [junit]   Bytecode:
    [junit]     0x0000000: 023d 1c3c 033d 1bac 4e03 3d2d bf00 004e
    [junit]     0x0000010: 1cac
    [junit]   Exception Handler Table:
    [junit]     bci [2, 8] => handler: 8
    [junit]     bci [2, 15] => handler: 15
    [junit]   Stackmap Table:
    [junit]     full_frame(@8,{Object[#2],Top,Integer},{Object[#44]})
    [junit]     same_locals_1_stack_item_frame(@13,Object[#37])
```

## Comments

### Jesper Öqvist - 2017-12-15

This error occurs because an exception entry jumps to a block that will be expanded. The exception handler position should be changed to the new start position for the basic block.

### Jesper Öqvist - 2017-12-16

Update exception handler after deleting block

This improves the handling of exception ranges after deleting basic blocks.
The handler label is now updated when merging a block with a previous deleted
block. Also improved trimming ranges after deleting a block.

fixes #242 (bitbucket)


→ <<cset 58987e28ef71>>
