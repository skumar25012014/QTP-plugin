package be.isabel.uftplugin.test;

import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by faerts on 6/01/14.
 */
public class SummerTest {
    @Test
    public void testSum() throws Exception {
        Summer summer = new Summer();
        int result = summer.sum(1,1);

        assertEquals(2, result);
    }


    @Test
    public void testSum3() throws Exception {
        Summer summer = new Summer();
        int result = summer.sum(1,2);

        assertEquals(3, result);
    }
}
