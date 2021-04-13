import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntStackTest {

    IntStack tester1=new IntStack(5);
    IntStack tester2=new IntStack(10, 0.7);
    IntStack tester3=new IntStack(20,0.7,0.3);

    @Test
    public void isEmpty() {
        assertTrue(tester1.isEmpty());
        tester2.push(1);
        assertFalse(tester2.isEmpty());
        assertTrue(tester3.isEmpty());
    }

    @Test
    public void clear() {
        tester1.push(1);
        assertFalse(tester1.isEmpty());
        tester1.clear();
        assertTrue(tester1.isEmpty());
        assertTrue(tester3.isEmpty());
    }

    @Test
    public void size() {
        tester1.clear();
        tester1.push(1);
        tester1.push(2);
        assertEquals(tester1.size(),2);
        tester1.clear();
        assertEquals(tester1.size(),0);
        tester2.clear();
        assertEquals(tester2.size(),0);
    }

    @Test
    public void capacity() {
        assertEquals(tester1.capacity(),5);
        assertEquals(tester2.capacity(),10);
        tester2.clear();
        int [] to_push={1,2,3,4};
        tester2.multiPush(to_push);
        tester2.multiPush(to_push);
        assertEquals(tester2.capacity(),12);

    }

    @Test
    public void peek() {
        tester1.clear();
        tester1.push(1);
        tester1.push(2);
        assertEquals(tester1.peek(),2);
        tester2.push(10);
        assertEquals(tester2.peek(),10);
        tester1.pop();
        assertEquals(tester1.peek(), 1);
    }

    @Test
    public void push() {
        tester1.clear();
        tester1.push(1);
        assertEquals(tester1.peek(),1);
        tester1.push(10);
        assertEquals(tester1.peek(),10);
        tester2.clear();
        tester2.push(100);
        assertEquals(tester2.peek(),100);
    }

    @Test
    public void pop() {
        tester1.clear();
        tester1.push(1);
        tester1.push(2);
        assertEquals(tester1.peek(),2);
        tester1.pop();
        assertEquals(tester1.peek(),1);
        tester1.push(10);
        tester1.pop();
        assertEquals(tester1.peek(),1);
    }

    @Test
    public void multiPush() {
        tester1.clear();
        int []to_push1={1,2,3,4,5};
        int []to_push3={21,22,23,24};
        tester1.multiPush(to_push1);
        tester1.pop();
        assertEquals(tester1.peek(),4);
        tester1.pop();
        assertEquals(tester1.peek(),3);
        tester1.multiPush(to_push3);
        assertEquals(tester1.peek(),24);
    }

    @Test
    public void multiPop() {
        int []to_push1={1,2,3,4,5};
        int []to_push3={21,22,23,24};
        tester1.clear();
        tester1.multiPush(to_push1);
        tester1.multiPop(3);
        assertEquals(tester1.peek(),2);
        tester1.multiPush(to_push3);
        tester1.multiPop(2);
        assertEquals(tester1.peek(),22);
        tester1.multiPop(3);
        assertEquals(tester1.peek(),1);

    }
}