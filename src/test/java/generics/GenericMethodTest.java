package generics;

import static org.junit.Assert.*;

public class GenericMethodTest
{
    @org.junit.Test
    public void testF() throws Exception
    {
        GenericMethod<String> gm = new GenericMethod<>();

        assertEquals(Integer.class.getName(), gm.f(1));
    }
}
