import static org.junit.Assert.*;

public class PrefixNotationTest {

    @org.junit.Test
    public void evaluate() {
        PrefixNotation pref= new PrefixNotation();
        String str1= "+ * 10 + 5 21 / 9 4";
        int res=pref.evaluate(str1);
        assertEquals(res,262);
    }
}