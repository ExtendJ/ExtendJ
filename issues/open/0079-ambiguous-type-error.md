# Ambiguous type error

JastAddJ does not generate an error message for ambiguous types.

Example:

    import pkg.A.*;
    class B {
        String s;// String is ambiguous if String is also declared in class pkg.A
    }

## Comments

### Jesper Öqvist - 2014-08-14

Added test: type/ambiguous_01f
