// 代码生成时间: 2025-09-18 00:39:30
package com.example.mathtool;

import io.micronaut.core.annotation.Introspected;

/**
 * A utility class that provides a set of mathematical operations.
 */
@Introspected
public class MathUtil {

    /**
     * Adds two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the sum of a and b
     */
    public static double add(double a, double b) {
        return a + b;
    }

    /**
     * Subtracts two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the difference of a and b
     */
    public static double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Multiplies two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the product of a and b
     */
    public static double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Divides two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the quotient of a and b
     * @throws ArithmeticException if b is zero
     */
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    /**
     * Calculates the power of a number.
     *
     * @param base the base number
     * @param exponent the exponent
     * @return the result of raising base to the power of exponent
     */
    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * Calculates the square root of a number.
     *
     * @param number the number to find the square root of
     * @return the square root of the number
     * @throws ArithmeticException if the number is negative
     */
    public static double sqrt(double number) {
        if (number < 0) {
            throw new ArithmeticException("Cannot calculate the square root of a negative number");
        }
        return Math.sqrt(number);
    }
}
