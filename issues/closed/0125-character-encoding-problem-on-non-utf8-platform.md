# Character encoding problem on non-UTF8 platforms

**Status:** resolved

When running pretty printing tests I get problems when I include UTF8 characters on a non-UTF8 platform. The pretty-printed output should be in UTF8.

## Comments

### Jesper Öqvist - 2026-08-14

Fixed by a780b134 ("Redesign pretty printing"), which replaced
`System.out.println(unit.prettyPrint())` by a `PrintStream` with an explicit
UTF-8 encoding.

The reading side was never affected. Source files have been read through an
`InputStreamReader` with an explicit UTF-8 encoding since before this issue was
reported.

Added regression test: `pretty-print/unicode_01p`
