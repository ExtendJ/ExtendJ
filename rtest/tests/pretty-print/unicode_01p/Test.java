// Non-ASCII source must be read and pretty-printed as UTF-8, regardless of
// the platform default character encoding.
// See issue 125.
// .result=COMPILE_OUT
// .options=XprettyPrint
public class Test {
  int använd = 1;
  String sträng = "åäö €";
  char tecken = 'ö';
}
