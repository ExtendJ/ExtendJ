// Test that line numbers are correct in MethodDecl's inside interfaces
// https://bitbucket.org/extendj/extendj/pull-requests/4/
// .result: COMPILE_FAIL
public interface Test {
  String
  dupeStr(
  );
  String dupeStr();

  void
  dupeVoid(
  );
  void dupeVoid();

  <
    T
  >
  String
  dupeGenStr(
  );
  <T> String dupeGenStr();

  <
    T
  >
  void
  dupeGenVoid(
  );
  <T> void dupeGenVoid();
}
