class TestIncDec {
    public static void main(String[] args) {
        int i = 3;
        i = i++ + ++i; // 8
        System.out.println(i);
        i = --i - i--; // 0
        System.out.println(i);
	i++;
	System.out.println(i++);
	System.out.println(++i);
	--i;
	System.out.println(i--);
	System.out.println(--i);
    }
}
