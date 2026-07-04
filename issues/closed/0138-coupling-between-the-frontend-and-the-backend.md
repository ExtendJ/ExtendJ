# Coupling between the frontend and the backend

**Status:** resolved

I'm working on a refactoring tool that uses only the frontend of extendj, and thus I found two unwanted dependencies toward the backend. For reproductibility just run the ant frontend task in java 8.

The problem comes from the equation boolean BodyDecl.isField() located in java4/backend/GenerateClassfile.jrag
Once this equation has been imported to be able to compile the generated java classes, the FieldDecl.hasInit() method located in java4/backend/CreateBCode.jrag is also required.
If these two methods/equations are moved in some frontend files, the frontend is correctly decoupled from the backend.

## Comments

### Jesper Öqvist - 2016-02-19

Thank you for reporting this! The coupling was introduced by a recent refactoring in the code generation. I created a job to build the Java 8 frontend on the ExtendJ continuous integration server so I don't accidentally break this again in the future.

### Loïc Girault - 2016-02-19

Thank you for correcting it !

### Jesper Öqvist - 2016-02-19

Fix Java 8 frontend build error

fixes #138 (bitbucket)


→ <<cset e33951b198a1>>
