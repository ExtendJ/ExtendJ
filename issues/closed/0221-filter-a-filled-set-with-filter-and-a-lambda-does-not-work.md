# Filter a filled Set with filter and a lambda does not work

**Status:** resolved

In Program:

```java
import java.util.stream.*;
import java.util.*;

public class PQL {
	public static void main(String[] args) {
		Set<Integer> y = new HashSet<Integer>();
		y.add(5);
		y = y.stream().filter(c -> c < 3).collect(Collectors.toSet());

	}

}
```

Expected result: Execute without error.

Actual result:

```
Exception in thread "main" java.lang.AbstractMethodError: Method PQL$1.test(Ljava/lang/Object;)Z is abstract
	at PQL$1.test(PQLtest.java)
	at java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:174)
	at java.util.HashMap$KeySpliterator.forEachRemaining(HashMap.java:1548)
	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:481)
	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
	at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:708)
	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
	at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:499)
	at PQL.main(PQLtest.java:21)
```

## Comments

### Jesper Öqvist - 2017-12-11

This issue was fixed in commit 51f94952f3c4a2d5e3b204680454663780623706.

### Jesper Öqvist - 2017-12-11

The test has been added to the regression test suite: `jsr335/stream/filter_01p`

Thank you for reporting the issue!
