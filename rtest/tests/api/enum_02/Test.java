// Check that correct bytecode flags are generated for enum types.
// https://bitbucket.org/extendj/extendj/issues/290/enum-types-should-not-have-acc_static-flag
// .classpath=@EXTENDJ_LIB@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericInterfaceDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.EnumDecl;
import org.extendj.ast.Modifiers;
import org.extendj.ast.Modifier;
import org.extendj.ast.Access;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.List;
import org.extendj.ast.Opt;
import org.extendj.ast.SignatureAttribute;

import java.io.ByteArrayInputStream;

public class Test {
  public static void main(String[] args) {
    EnumDecl e1 = new EnumDecl(
        new Modifiers(),
        "E1",
        new List<Access>(),
        new List<BodyDecl>());

    EnumDecl e2 = new EnumDecl(
        new Modifiers(
            new List<Modifier>(new Modifier("static"))),
        "E2",
        new List<Access>(),
        new List<BodyDecl>());

    // Compilation unit needed to evaluate attributes in e1 and e2.
    Program program = new Program();
    CompilationUnit cu = new CompilationUnit();
    cu.addTypeDecl(e1);
    cu.addTypeDecl(e2);

    checkFlags(e1);
    checkFlags(e2);
  }

  static void checkFlags(EnumDecl type) {
    int flags = type.flags();
    // Note: ACC_SUPER is generated outside flags().
    checkFlag(flags, Modifiers.ACC_ENUM);
    checkFlag(flags, Modifiers.ACC_FINAL);
    checkNotFlag(flags, Modifiers.ACC_STATIC);
  }

  static void checkFlag(int flags, int flag) {
    if ((flags & flag) == 0) {
      System.err.format("Missing enum flag: 0x%X%n", flag);
    }
  }

  static void checkNotFlag(int flags, int flag) {
    if ((flags & flag) != 0) {
      System.err.format("Enum should not have flag: 0x%X%n", flag);
    }
  }
}
