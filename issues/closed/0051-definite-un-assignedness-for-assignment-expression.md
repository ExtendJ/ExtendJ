# Definite un-assignedness for assignment expression

**Status:** resolved

[JLS version 7, Chapter 16.1.8](http://docs.oracle.com/javase/specs/jls/se7/html/jls-16.html#jls-16) states:

     V is definitely unassigned after the assignment expression iff a is not V and V is definitely unassigned after b.

The attribute for assignment expression definite unassignedness does not comply with the specification:

    eq AssignExpr.isDUafter(Variable v) = getSource().isDUafter(v);

The attribute only checks the source unassignedness.

Proposed fix:

	eq AssignExpr.isDUafter(Variable v) {
		//Bug in java 4 implementation, see 16.1.8 second bullet. Must check that v != a
		if(getDest().isVariable() && getDest().varDecl() == v)
			return false;
		else
			return getSource().isDUafter(v);
	}

## Comments

### Jesper Öqvist - 2014-02-24

Test case for this bug:

    public class Test {
            public void m() {
                    final int f;
                    f = 3;
                    f = 4;// expect error because f not definitely unassigned
                    System.out.println(""+f);
            }
    }

### Jesper Öqvist - 2014-02-24

Fixed definite assignment error

fixes issue #51 (bitbucket)


→ <<cset 62c7dc33fa23>>
