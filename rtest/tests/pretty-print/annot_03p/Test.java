// .result=COMPILE_OUT
// .options=XprettyPrint
@interface A {
	long value() default 777L;
}

public class Test {
	@A void m() {
	}
}
