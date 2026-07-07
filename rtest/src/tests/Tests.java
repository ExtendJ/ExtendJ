/* Copyright (c) 2013-2018, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *
 *   2. Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 *
 *   3. Neither the name of the copyright holder nor the names of its contributors
 *      may be used to endorse or promote products derived from this software
 *      without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tests;

/**
 * This class defines the default test sets.
 *
 * Some tests are excluded when running higher versions
 * of Java because they test features that behave slightly
 * differently than in the lower version.
 *
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public interface Tests {
  /**
   * Tests expected to fail.
   * These expose bugs in ExtendJ that should be fixed.
   */
  String[] FAILING = {
    "type/ambiguous_01f",
    "generics/static_02f",
    "jsr335/lambda/type_inf_06p", // https://bitbucket.org/extendj/extendj/issues/217/failure-in-lambda-return-type-inference
    "jsr335/lambda/err_01f", // https://bitbucket.org/extendj/extendj/issues/202/method-lookup-error-causes
    "jsr335/lambda/err_02f", // https://bitbucket.org/extendj/extendj/issues/202/method-lookup-error-causes
    "generics/method_20p", // https://bitbucket.org/extendj/extendj/issues/213/unused-type-variable-causes-type-inference
    "generics/method_22p", // https://bitbucket.org/extendj/extendj/issues/213/unused-type-variable-causes-type-inference
    "jsr335/Semantics/ConstructorReferenceAnalysis/ClassReferences/ShouldFail/syntax03",
    "jsr335/Semantics/ConstructorReferenceAnalysis/ClassReferences/ShouldFail/syntax04",
    "jsr335/Semantics/FunctionalInterfaces/ReturnTypeSubstitutable/ShouldCompile/syntax22",
    "jsr335/Semantics/FunctionalInterfaces/Signature/ShouldFail/syntax08",
    "jsr335/Semantics/LambdaTypeAnalysis/AssignmentContext/ShouldCompile/syntax28",
    "type/autoboxing_02f", // https://bitbucket.org/extendj/extendj/issues/225/illegal-autoboxing-conversion-is
    "type/autoboxing_05f", // https://bitbucket.org/extendj/extendj/issues/225/illegal-autoboxing-conversion-is
    "pkg/static_import_03p", // https://bitbucket.org/extendj/extendj/issues/227/error-should-not-be-generated-for-unused
    "jsr335/lambda/type_inf_09p", // True Java 8 type inference is needed.
    "curious", // Curious type inference problems in Java 8. Don't even work with javac.
    "jsr335/diamond/generics_01p", // https://bitbucket.org/extendj/extendj/issues/267/diamond-constructor-inference-fails-if
    "extendj/generics/container_01f", // Needs an error message (crash during code generation).
    "generics/method_26f", // TODO: add an issue for this.
    "jsr335/misc/error_01f", // Does not give good error messages.
    "jsr335/diamond/nested_01p", // https://bitbucket.org/extendj/extendj/issues/266/stack-overflow-caused-by-nested-diamond
    "jsr335/diamond/nested_02p", // https://bitbucket.org/extendj/extendj/issues/266/stack-overflow-caused-by-nested-diamond
    "jsr335/lambda/exception_ti_01p", // https://bitbucket.org/extendj/extendj/issues/308/error-in-uncaught-exception-checking-for
    "jsr335/lambda/exception_ti_02p", // https://bitbucket.org/extendj/extendj/issues/308/error-in-uncaught-exception-checking-for
    "jsr335/lambda/exception_ti_03p", // https://bitbucket.org/extendj/extendj/issues/309/java-8-exception-handling-with-inferred
    "exception/ti_java8_01p", // https://bitbucket.org/extendj/extendj/issues/309/java-8-exception-handling-with-inferred
    "extendj/err_14f", // https://bitbucket.org/extendj/extendj/issues/317/extendj-classes-accessible-by-code-being
    "generics/bounds_05f", // Compilation should fail on cyclic type variable bounds.
    "generics/bounds_06f", // Compilation should fail on cyclic type variable bounds.
    "java10/var/var_17",  // https://bitbucket.org/extendj/extendj/issues/319/type-cast-to-intersection-type-has-no
    "jsr335/intersection",  // https://bitbucket.org/extendj/extendj/issues/319/type-cast-to-intersection-type-has-no
    "java10/var/var_14", // https://bitbucket.org/extendj/extendj/issues/340/type-intersection-parsing-issue
    "java10/var/var_27", // https://bitbucket.org/extendj/extendj/issues/340/type-intersection-parsing-issue
    "java10/var/var_22", // https://bitbucket.org/extendj/extendj/issues/338/capture-types-becomes-too-general
    // Tests requiring improved Java 8 type inference:
    "jsr335/diamond/type_inf_01p",
    "jsr335/diamond/type_inf_02p",
    "jsr335/inference/array_ctor_ref_01p",
    "jsr335/inference/ctor_ref_01p",
    "jsr335/inference/lambda_01p",
    "jsr335/inference/passthrough_02p",
    "jsr335/inference/passthrough_03p",
    "jsr335/inference/passthrough_04p",
    "jsr335/inference/passthrough_05p",
    "jsr335/inference/passthrough_06p",
    "jsr335/inference/varargs_01p",
    "jsr335/stream/array_01p",
    "jsr335/stream/array_02p",
    "jsr335/stream/array_03p",
    "jsr335/stream/array_04p",
    "jsr335/stream/array_05p",
    "jsr335/stream/collect_01p",
    "jsr335/stream/collect_02p",
    "jsr335/stream/filter_02p",
    "jsr335/stream/map_01p",
    "jsr335/stream/map_02p",
    "jsr335/stream/simple_01p",
    "jsr335/stream/simple_02p",
    "jsr335/stream/varargs_01p",
    "jsr335/stream/varargs_02p",
    "jsr335/stream/varargs_03p",
    "method/varargs_05p", // Compiles, but the variable arity invocation form is wrong (ClassCastException).
    "ti/any_02p",
    "ti/any_03p",
    "ti/any_decl_01p",
    "ti/any_decl_02p",
    "ti/any_decl_03p",
    "ti/any_dep_01p",
    "ti/bl2_01p",
    "ti/bl2_02p",
    "ti/bl2_03p",
    "ti/bul3_02p",
    "ti/chain_04f", // Compiler crash (NullPointerException) with the current type inference.
    "ti/sub_binary_01p",
    "ti/sub_unary_01p",
    "ti/sub_unary_02p",
    "ti/super_binary_02p",
    "ti/super_binary_03p",
    "ti/super_dep_01p",
    "ti/super_dep_02p",
    "ti/upb_02p",
    "ti/upb_03p",
    "ti/uul3_02p",
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
  };

  /**
   * Java 8 tests.
   */
  String[] JAVA8 = {
    "jsr335",
    "extendj/jsr335",
    "api/jsr335",
    "jsr334/diamond/diamond_24p", // https://bitbucket.org/extendj/extendj/issues/173/
    "exception/ti_java8_01p",
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
    "jsr334/safe-varargs/nonfinal_01f", // @SafeVarargs is allowed on static non-final methods.
    "enum/enumset_01p", // https://bitbucket.org/extendj/extendj/issues/318/static-field-update-for-switch-over-enums
    "enum/switch_01p", // https://bitbucket.org/extendj/extendj/issues/318/static-field-update-for-switch-over-enums
    "enum/switch_02p", // https://bitbucket.org/extendj/extendj/issues/318/static-field-update-for-switch-over-enums
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
