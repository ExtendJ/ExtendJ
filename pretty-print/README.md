Pretty Printing Templates
=========================

Writing pretty-printing code is repetitive and boring. ExtendJ uses a template system
to try to minimize the specification of pretty-printing.

This directory contains template files for generating the pretty-printing code in ExtendJ.

The template code is sensitive to white-space, and the point is that the generated pretty-printer
will reflect the indentation and line-breaks used in the template code.

The template files all have names ending in `.tt`.

The template enginge used is [tinytemplate][1].
Here is a quick introduction to the syntax:

* `CompilationUnit [[...]]` - declares the template for the `CompilationUnit` AST class.
* `$(PackageDecl)` - this expands to the pretty-printed text of the `PackageDecl` child.
* `#(docComment)` - this expands to the string-converted value of the `docComment` attribute.
  Only non-parameterized attributes can be used.
* `$join(Implements,", ")` - this pretty-prints all elements of the `Implements` list child,
  separated by a comma and space.
* `$if(hasSuperClass)...$endif` - this prints the dots if the `hasSuperClass` attribute is `true`.

[1]: https://bitbucket.org/joqvist/tinytemplate
