# Mirror Javac exit codes

**Status:** resolved

Javac returns different exit codes depending on what caused Javac to fail. JastAddJ currently only returns 1 on failure, independent of what the failure was caused by. It would be nice if JastAddJ used the same exit codes as Javac.

The exit codes given by Javac are:

    static final int
        EXIT_OK = 0,        // Compilation completed with no errors.
        EXIT_ERROR = 1,     // Completed but reported errors.
        EXIT_CMDERR = 2,    // Bad command-line arguments
        EXIT_SYSERR = 3,    // System error or resource exhaustion.
        EXIT_ABNORMAL = 4;  // Compiler terminated abnormally

(copied from http://bugs.sun.com/view_bug.do?bug_id=7014715)

## Comments

### Jesper Öqvist - 2014-01-14

Use the same exit codes as Javac

fixes issue #39 (bitbucket)


→ <<cset ec4299641410>>
