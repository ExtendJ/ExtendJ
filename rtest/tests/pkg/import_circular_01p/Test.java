// Test a circular import-on-demand dependency.
// The implements clause in pkg/C.java causes type lookup to load the on-demand
// import pkg.D.*, this in turn leads to evaluating the member types of pkg.D,
// which requires loading the on-demand imported types for the import
// declarations in pkg/D.java.  Since the import pkg.C.* is before the import
// pkg.E.* which provides the type X we are looking for, the member type sof
// pkg.C are evaluated first, but leads back to the original on-demand import
// lookup.
// .result=COMPILE_PASS
public class Test extends pkg.C {
}
