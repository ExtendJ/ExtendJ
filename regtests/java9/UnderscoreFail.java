public class UnderscoreFail {
    String _ = "test";  // Err
    _(int i){           // Err x 2
        //
    }
    void _(){           // Err
        int _ = 1;      // Err
    }

    void f(int _){      // Err
        //
    }

    private class _ {   // Err
        _(int i){       // Err
            //
        }
    }

    private class Generics<_> { // Err

    }
}
