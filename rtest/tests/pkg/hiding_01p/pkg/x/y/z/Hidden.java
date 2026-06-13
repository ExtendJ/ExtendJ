// The type Hidden is hidden due to local field name, so a fully qualified name is used
// to access the type name.
package pkg.x.y.z;

public class Hidden {
  public static int Hidden;
  public int value;

  int getHidden() {
    return pkg.x.y.z.Hidden.Hidden;
  }
}
