package com.javarush.task.task39.task3908;

import org.junit.Assert;
import org.junit.Test;

public class SolutionTest {

    @Test
    public void isPalindromePermutation1() {
        boolean actual = Solution.isPalindromePermutation("abcaacb");
        Assert.assertTrue(actual);
    }
    @Test
    public void isPalindromePermutation2() {
        boolean actual = Solution.isPalindromePermutation("abcaacbabcaacb");
        Assert.assertTrue(actual);
    }
    @Test
    public void isPalindromePermutation3() {
        boolean actual = Solution.isPalindromePermutation("aaabbbvvv");
        Assert.assertFalse(actual);
    }
}