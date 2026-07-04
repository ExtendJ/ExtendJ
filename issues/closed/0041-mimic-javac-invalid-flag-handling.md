# Mimic Javac invalid flag handling

**Status:** resolved

Print an invalid flag error message when a non-flag, non-filename is encountered on the command-line. This should mimic the Javac behavior.

Currently JastAddJ assumes the unrecognized flag is a filename and prints a warning that the file does not exist, then JastAddJ crashes because it tried to open the file and failed.

## Comments

### Jesper Öqvist - 2014-01-14

Print error for non-filename, non-option argument

fixes issue #41 (bitbucket)


→ <<cset 5795e804aac2>>
