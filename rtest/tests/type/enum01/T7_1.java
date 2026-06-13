interface I { void m(); }

enum E implements I {
	C1 {
		public void m() { }
	},
	C2 {
		public void m() { }
	}
}