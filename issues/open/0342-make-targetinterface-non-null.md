# Make targetInterface non-null

The `targetInterface` attribute is used without null guards.

## Comments

### Jesper Öqvist - 2026-07-03

Make toClass equations safer

Handle null targetInterface result.

Undecided on whether or not to make the attribute non-null, but this
should improve robustness until it is refactored.

See #342


→ <<cset f6504bbb7c0c>>
