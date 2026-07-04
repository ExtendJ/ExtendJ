# Generic method with type variable depending on host generic type : Typecheck error

**Status:** resolved

The following piece of code do not compile.
```
#!java
interface I<T> {}
class C<T> implements I<T> {}

class MapOver<R> {
         public <S extends I<R>> void run(S l) {}
}

public class Test {

    public void m() {
        new MapOver<String>().run(new C<String>());
   }
}
```

When the parameterized class MapOver<String> is created a substitute method run is created but the R is not substituted in the S bound and afterward run is not even recognized as a generic method.

Here is a solution :
Add the following line to java5/grammar/generics.ast
```
GenericMethodDeclSubstituted : GenericMethodDecl ::= <Original:GenericMethodDecl>;
```

in java5/frontend/Generics.jrag :
```
#!java

 //the substitution in the parameter list produces bound type acces
 //we need to bound these accesses to the new type variables
 private void GenericMethodDecl.substitutedBodyDecl_fixTypeAccess(List<TypeVariable> tps, Access typeAccess){
        if(typeAccess instanceof BoundTypeAccess){
          BoundTypeAccess bta = (BoundTypeAccess) typeAccess;
          for(int i =0; i<getTypeParameterList().getNumChild(); i++){
            if(bta.getTypeDecl() == getTypeParameterList().getChild(i))
              bta.setTypeDecl(tps.getChild(i));
          }
        } else if(typeAccess instanceof ArrayTypeAccess){
          ArrayTypeAccess ata = (ArrayTypeAccess) typeAccess;
          substitutedBodyDecl_fixTypeAccess(tps, ata.getAccess());
        }
  }
public BodyDecl GenericMethodDecl.substitutedBodyDecl(Parameterization parTypeDecl) {

        List<TypeVariable> tps = getTypeParameterList().substitute(parTypeDecl);
        List<ParameterDeclaration> pds = getParameterList().substitute(parTypeDecl);
        for(ParameterDeclaration pd : pds){
          substitutedBodyDecl_fixTypeAccess(tps, pd.getTypeAccess());
        }

        Access returnType = getTypeAccess().substitute(parTypeDecl);
        substitutedBodyDecl_fixTypeAccess(tps, returnType);

        List<Access> excs = getExceptionList().substitute(parTypeDecl);
        for(Access a : excs)
          substitutedBodyDecl_fixTypeAccess(tps, a);

        return new GenericMethodDeclSubstituted(
                      (Modifiers) getModifiers().treeCopyNoTransform(),
                      returnType, getID(), pds, excs,
                      substituteBody(parTypeDecl), tps, this);
   }

// modify List.substitute to add the following case

public List List.substitute(Parameterization parTypeDecl) {
/* ... */
      else if (node instanceof TypeVariable) {
        TypeVariable tv = (TypeVariable) node;
        // we need a parent for the List because
        // when substitute is recursively called, in the if (node instanceof Access) case
        // the call to type() will produce a null pointer exception
        List<Access> typeBoundListSave = tv.getTypeBoundList();
        List<Access> typeBoundList = tv.getTypeBoundList().treeCopyNoTransform();
        tv.setTypeBoundList(typeBoundList);

        list.add(
          new TypeVariable(tv.getModifiers().treeCopyNoTransform(),
            tv.getID(),
            tv.getBodyDeclList().treeCopyNoTransform(),
            typeBoundList.substitute(parTypeDecl))
          );
        tv.setTypeBoundList(typeBoundListSave);
      }
/* ... */
}
```

## Comments

### Loïc Girault - 2016-03-17

I have edited the solution abose so that the following piece of code also compile :

```
#!java
package p;

interface Set<E> {
    <T> T[] toArray(T[] a);
}

public class Test {

    public void m(Set<Long> ns) {
        Long[] a = ns.toArray(new Long[0]);
    }
}
```
I'm aware that my solution is a bit of a hack. Anyway I hope it will help you to properly fix the issue.

### Jesper Öqvist - 2016-03-18

Thank you for the test cases! Your solution might be the best one, but I am just trying to see if I can find a simpler solution.

### Loïc Girault - 2016-03-18

You're welcome. I must admit that I find them a bit by accident by trying to find study cases for my tool. But happy to help ^_^
By the way my supervisers let me open source it. If you're curious you can find it there : https://github.com/lorilan/puck
Still at a prototyping phase but you can already play a bit with it.

### Jesper Öqvist - 2016-03-18

I am very grateful for these bug reports! There are lots of tricky cases like this that cause a bug in ExtendJ and they seem to occur very seldom in practice, but are still very important to fix. Every issue that I fix now is also tested with a regression test so that the bug does not occur again. One of my goals is that ExtendJ will reach a point where there are no known bugs.

### Loïc Girault - 2016-03-18

The test cases that I report are minimal example extracted from real code (freemind freecs and berkeley database and actually the last one is from the java std lib) so as you say they may be seldom but they do happen ! I totally agree with you on the importance to fix them. Furthermore my tool heavily depending on extendj, I'm more than happy to help you reach your goal ^_^

### Jesper Öqvist - 2016-03-21

I found a different solution to this issue that I prefer over the suggested solution. I recently refactored how type substitution works so that it now uses rewrites instead of nonterminal attributes, but when I fixed this issue then the rewrites prevent attribute caching and it degrades performance by a lot. However, in JastAdd I recently commited some changes to rewrites which improve this case - because attributes can be cached better during a rewrite, so to be able to commit my fix I also have to update to a new version of JastAdd. I am working on preparing the next JastAdd release now, so hopefully this gets fixed this week. This would also allow fixing issue #159 which is much easier to fix by using the next JastAdd version.

### Jesper Öqvist - 2016-03-30

I found a solution to this issue which solved some other problems at the same time. The fix does not require updating JastAdd, but I will upgrade to JastAdd 2.2.2 soon because then I can clean up some code used to influence collection attributes, and I will also be able to solve issue #159.

The solution for this issue is a big redesign of generic type substitution. I changed it so that generic type variables are no longer substituted while building ParClassDecl/ParInterfaceDecl nodes. Instead type variables are substituted during type lookup. This solves a circular dependency in the lookupParTypeDecl NTA, and also makes the type substitution simpler and more powerful. I had to change the abstract grammar a bit during this re-implementation of type substitution, so that may cause some problems in your extension unfortunately.

### Jesper Öqvist - 2016-03-30

Redesign generic type substitution

Type substitution has been redesigned for parameterized type lookup. Instead of
replacing type accesses of member declarations and supertypes while
constructing a parameterized type instance, type variables are replaced during
type lookup via Parameterization instances.

Added two new AST node types: GenericMethodDeclErased and
GenericConstructorDeclErased. The abstract grammar declarations for
ParClassDecl, ParInterfaceDecl, RawClassDecl, and RawInterfaceDecl have also
been redesigned.

fixes #163 (bitbucket)


→ <<cset f7b18c61af97>>

### Loïc Girault - 2016-03-30

Thank you very much ! I was looking forward to this fix. It seems that not to many things in my code were broken. I've already fixed the most obvious problems and only 3 of my tests are still broken.  I'll see in the next days how much work it needs to repair the remaining ones ^_^

### Loïc Girault - 2016-03-31

may I suggest to add the following method for convenience :
```
#!java
TypeDecl Parameterization.getArg(int i){
		TypeVariable tv = params.get(i);
		TypeDecl arg = typeMap.get(tv.name()).arg;
		if(arg == null)
			return tv.program().typeObject();

		return arg;
	}
```

### Jesper Öqvist - 2016-03-31

Sure, I have added the suggested method in the latest commit (9843b6e).

### Loïc Girault - 2016-03-31

Thank you !
