// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test fall-through into a hash-collision label.
 */
public class Test {
	public static void main(String[] args) {
		int count = 0;
		String str = "saw";
		switch (str) {
			case "tBK":// 0x0001BBBD
				count += 1;
			case "saw":// 0x0001BBE9
				count += 2;
			case "tBx":// 0x0001BBEA
				count += 4;
				break;
			case "tBy":// 0x0001BBEB
				count += 8;
			case "tBw":// 0x0001BBE9
				count += 16;
		}
		testEqual("Fall-through error!", 6, count);
	}
}
