# Prettyprint of Try-With-Resources Statement Broken

**Status:** resolved

This pertains to the JAR built from commit 5eef0cc.

Broken code is produced when parsing and subsequently pretty-printing code that contains try-with-resources statements.

Input (see attachements):
```
#!Java
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TWR {

    public static void main(String[] args) {

        try (FileOutputStream out = new FileOutputStream("out"); FileInputStream in = new FileInputStream("in")) {
            out.write(in.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

Execute:
```
#!Bash
java -cp extendj.jar org.extendj.JavaPrettyPrinter TWR.java
```

This gives the output (see attachments):
```
#!Java
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TWR {
  public static void main(String[] args) {
    try (out = new FileOutputStream("out")in = new FileInputStream("in")) {
      out.write(in.read());
    }
     catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

This code does not compile as the type declarations are missing in the resources declaration. The two resource declarations should also be separated by a semicolon. Additionally the linebreak after the closing brace of the try block is unexpected.

I looked at the code and the resource statements are represented by `ResourceDeclaration` instances whose `prettyPrint` method is defined in the `Declarator` class. The relevant part of `Java4PrettyPrint.tt`:

```
#!Text
Declarator [[$ID$DimsList$if(hasInit) = $Init$endif]]
```
This of course can't produce the full `FileOutputStream out = new FileOutputStream("out")` declaration.

I would propose adding a `ResourceDeclaration` template for pretty printing and fixing the `TryWithResources` template. A corresponding pull request is forthcoming.

## Attachments

- [TWR.java](../attachments/183/TWR.java) (uploaded by Georg Seibt)
- [pp_result.java](../attachments/183/pp_result.java) (uploaded by Georg Seibt)

## Comments

### Jesper Öqvist - 2017-03-28

Thank you for reporting this issue. I will add a test case for this to the regression suite. I see that you also made a pull request with a fix, I'll take a look at it soon.

Thank you.

### Georg Seibt - 2017-03-28

For regression testing purposes I would also add a finally block to the example.

Input:
```
#!Java
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TWR {

    public static void main(String[] args) {

        try (FileOutputStream out = new FileOutputStream("out"); FileInputStream in = new FileInputStream("in")) {
            out.write(in.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Hi!");
        }
    }
}
```

Output of 5eef0cc:

```
#!Java
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TWR {
  public static void main(String[] args) {
    try (out = new FileOutputStream("out")in = new FileInputStream("in")) {
      out.write(in.read());
    }
     catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
     finally {
      System.out.println("Hi!");
    }
  }
}
```

Output with the fixes I proposed in pull request #2:

```
#!Java

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TWR {
  public static void main(String[] args) {
    try (FileOutputStream out = new FileOutputStream("out"); FileInputStream in = new FileInputStream("in")) {
      out.write(in.read());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.out.println("Hi!");
    }
  }
}
```

### Jesper Öqvist - 2017-03-28

Thank you, but I already made a test for the issue in this commit: https://bitbucket.org/extendj/regression-tests/commits/85c56243c2a57e44531326d908925c83e7b0c3ce

Let me know if you think I missed some case.

Thanks for the nice PR.
