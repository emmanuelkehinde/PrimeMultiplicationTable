package com.emmanuelkehinde.primemultiplicationtable;

import com.emmanuelkehinde.primemultiplicationtable.util.NumberUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PrimeNumberGeneratorTest {

    private List<Integer> expectedPrimeNos = Arrays.asList(2,3,5,7,11);

    @Test
    public void generatedPrimeNumberIsCorrect() {

        List<Integer> actualPrimeNos = NumberUtil.getPrimeNumbers(5);
        assertEquals(expectedPrimeNos, actualPrimeNos);
    }

    @Test
    public void generatedPrimeNumberIsNotCorrect() {
        List<Integer> actualPrimeNos = NumberUtil.getPrimeNumbers(8);
        assertNotEquals(expectedPrimeNos, actualPrimeNos);
    }
}
