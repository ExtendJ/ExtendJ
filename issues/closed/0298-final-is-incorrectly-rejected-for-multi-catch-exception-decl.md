# 'final' is incorrectly rejected for multi-catch exception decls

**Status:** resolved

J8's spec says an exception variable can be declared as `final` (4.12.4, 14.20), unfortunately extendj isn't honouring that right now for multi-catches.

Sample code: `catch (final IOException | NumberFormatException | ServiceException e)`

Expected: Code is accepted.

Actual: Code is rejected w/ error msg claiming `finally` is not permitted in this context.


Suggested fix:

It seems like a simple oversight where `CatchParameterDeclaration` doesn't have a `mayBeFinal` tag.
`eq CatchParameterDeclaration.getModifiers().mayBeFinal() = true;`

## Comments

### Olivier Hamel - 2018-01-29

"An exception parameter of a multi-catch clause is implicitly declared final if it is not explicitly declared final. " -- J8 spec

While the implicitly-final bit is faithfully handled, we're just missing the you're-allowed-to-be-redundantly-verbose bit.
(NOTE: Sample code taken from the `Symphony` blog project.)

### Jesper Öqvist - 2018-01-29

Thanks for the issue! This should be simple to fix.

### Jesper Öqvist - 2018-01-30

Allow multi-catch parameters to be declared final

fixes #298 (bitbucket)


→ <<cset 3c73a5a37e92>>
