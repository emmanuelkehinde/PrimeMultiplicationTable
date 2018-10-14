package com.emmanuelkehinde.primemultiplicationtable.util;

import java.util.ArrayList;
import java.util.List;

public class NumberUtil {

    public static List<Integer> getPrimeNumbers(int numberOfPrimeNos) {

        List<Integer> primeNos = new ArrayList<>();
        int count = 0;
        int num = 2;
        while(count != numberOfPrimeNos) {  // while count!= number of prime numbers entered keep searching..
            boolean prime = true;   // to determine whether the number is prime or not
            for (int i = 2; i <= (int)Math.sqrt(num); i++) {
                if (num % i == 0) {
                    prime = false;  // if number divides any other number its not a prime so set prime to false and break the loop.
                    break;
                }
            }
            if (prime) {
                count++;
                primeNos.add(num);
            }
            num++; //see if next number is prime or not.
        }
        return primeNos;

    }

    public static int productOf(int i, int j) {
        return i*j;
    }
}
