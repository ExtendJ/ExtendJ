// SPDX-License-Identifier: BSD-3-Clause
package tests;

/**
 * This class defines default test sets for Java compatibility levels.
 *
 * <p>Some tests are excluded when running higher versions
 * of Java because they test features that behave slightly
 * differently than in the lower version.
 */
public interface Tests {
  /**
   * Tests expected to fail.
   * These expose bugs in ExtendJ that should be fixed.
   */
  String[] FAILING = {
    "type/ambiguous_01f",
    "generics/static_02f",
    "jsr335/lambda/type_inf_06p", // issue 217
    "jsr335/Semantics/ConstructorReferenceAnalysis/ClassReferences/ShouldFail/syntax03",
    "jsr335/Semantics/ConstructorReferenceAnalysis/ClassReferences/ShouldFail/syntax04",
    "jsr335/Semantics/FunctionalInterfaces/ReturnTypeSubstitutable/ShouldCompile/syntax22",
    "jsr335/Semantics/FunctionalInterfaces/Signature/ShouldFail/syntax08",
    "jsr335/Semantics/LambdaTypeAnalysis/AssignmentContext/ShouldCompile/syntax28",
    "type/autoboxing_02f", // issue 225
    "type/autoboxing_05f", // issue 225
    "pkg/static_import_03p", // issue 227
    "jsr335/lambda/type_inf_09p", // True Java 8 type inference is needed.
    "jsr335/diamond/generics_01p", // issue 267
    "extendj/generics/container_01f", // Needs an error message (crash during code generation).
    "generics/method_26f", // TODO: add an issue for this.
    "jsr335/misc/error_01f", // Does not give good error messages.
    "jsr335/diamond/nested_01p", // issue 266
    "jsr335/diamond/nested_02p", // issue 266
    "extendj/err_14f", // issue 317
    "generics/bounds_05f", // Compilation should fail on cyclic type variable bounds.
    "generics/bounds_06f", // Compilation should fail on cyclic type variable bounds.
    "java10/var/var_17",  // issue 319
    "jsr335/intersection/intersection_01",  // issue 319
    "jsr335/intersection/intersection_02",  // issue 319
    "jsr335/intersection/intersection_03",  // issue 319
    "jsr335/intersection/intersection_04",  // issue 319
    "jsr335/intersection/intersection_05",  // issue 319
    "jsr335/intersection/intersection_06",  // issue 319
    "java10/var/var_14", // issue 340
    "java10/var/var_27", // issue 340
  };

  /**
   * Java 6 specific tests.
   *
   * <p>These are tests that use the @Override annotaiton on interface
   * declared methods. This is not allowed in Java 5.
   */
  String[] JAVA6 = {
    "classes/super_01p",  // Overriding Runnable.run().
    "generics/override_15p",  // Overriding Map.entrySet().
    "generics/override_18p",  // Overriding custom interface method.
    "pkg/static_import_02p",  // Overriding Runnable.run().
    "method/infinite_01p",  // Overriding Runnable.run().
  };

  /**
   * Tests that should be excluded for Java 8, because they test features that
   * changed since Java 7 and no longer work the same way.
   */
  String[] EXCLUDE_JAVA7 = {
    "exception/rethrow_01f", // Rethrowing Throwable gives an error.
  };

  /**
   * Java 7 tests.
   */
  String[] JAVA7 = {
    "jsr334",
    "api/jsr334",
    "pretty-print/jsr334",
    "method/overload_04p",  // Uses @SafeVarargs annotation introduced in Java 7.
    "api/modifiers_02p",  // ACC_SYNTHETIC added for fields.
    "generics/method_20p",  // Requires Java 7 method type inference.
  };

  /**
   * Java 8 tests.
   */
  String[] JAVA8 = {
    "jsr335",
    "ti", // Improved Java 8 type inference tests.
    "extendj/jsr335",
    "api/jsr335",
    "exception/ti_java8_01p",
    "api", // Compiles against extendj.jar, which is Java 8 bytecode.
    "generics/method_22p",  // Requires Java 8 method type inference.
    "method/varargs_04p",  // Requires Java 8 method type inference.
    "method/varargs_05p",  // Requires Java 8 method type inference.
  };

  /**
   * Tests that should be excluded for Java 8, because they test features that
   * changed since Java 7 and no longer work the same way.
   */
  String[] EXCLUDE_JAVA8 = {
    "jsr334/diamond/diamond_18f",
    "generics/inference_07f", // More powerful type inference in Java 8.
    "generics/method_17p",
    "generics/method_25f", // Changed error message.
    "exception/ti_java5_01f",
  };


  /*
  * Test that are exclusive to Java 9
  */
  String[] JAVA9 = {
    "java9",
  };

  /**
   * Tests that should be excluded for Java 9, because they test features that
   * changed since previous versions and no longer work the same way.
   */
  String[] EXCLUDE_JAVA9 = {
    "annotation/param_01p", // Annotations changed toString() output.
    "annotation/param_02p", // Annotations changed toString() output.
    "codegen/annotation01", // Annotations changed toString() output.
    "jsr334/diamond/diamond_03f", // Diamond is allowed in anonymous class instance expressions.
    "jsr334/diamond/diamond_25f", // Error message changed in Java 9.
    "jsr334/safe-varargs/nonfinal_01f", // @SafeVarargs is allowed on static non-final methods.
    "enum/enumset_01p", // issue 318
    "enum/switch_01p", // issue 318
    "enum/switch_02p", // issue 318
    "java9/diamond/diamond_06", // Bug in Java 9 diamond access implementation
  };

  /*
  * Test that are exclusive to Java 10
  */
  String[] JAVA10 = {
    "java10",
  };

  String[] EXCLUDE_JAVA10 = {
  };

  /*
  * Test that are exclusive to Java 11
  */
  String[] JAVA11 = {
    "java11",
  };

  String[] EXCLUDE_JAVA11 = {
    "java10/var/var_lambda_01",
    "java10/var/var_lambda_02",
  };

  /**
   * Tests that test ExtendJ-specific behaviour (error messages, pretty printing, api).
   */
  String[] EXTENDJ_ONLY = {
    "extendj",
    "api",
    "pretty-print",
    "jsr335/Parsing", // ExtendJ-specific tests.
  };
}
