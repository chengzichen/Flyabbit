package com.fanyu.library;

import org.junit.Test;

import static com.dhc.library.utils.ArithmeticUtil.randomCommon;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);


    }

    @Test
    public void randomTest() {
        int[] ints = randomCommon(0, 9, 1);
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i]);
        }
    }

}