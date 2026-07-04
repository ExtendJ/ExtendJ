# Clone overriding error

**Status:** resolved

The following piece of code does not compile :

```
#!java
package p;

interface LogEntry {
    public LogEntry clone();
}

abstract class BaseEntry implements LogEntry {
    @Override
    public LogEntry clone(){
        try{
            return (LogEntry)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error();
        }
    }
}

class INLogEntry extends BaseEntry implements LogEntry {}
```

## Comments

### Jesper Öqvist - 2016-03-17

Improve method override compatibility checking

fixes #165 (bitbucket)


→ <<cset bd81539b8e15>>

### Loïc Girault - 2016-03-17

Nice ! thank you very much
