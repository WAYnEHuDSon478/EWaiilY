// 代码生成时间: 2025-08-30 02:59:11
package com.example.mathtool;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * A service class for mathematical operations.
 */
# 扩展功能模块
@Controller("/math")
public class MathToolService {

    // Calculates the sum of two numbers
# 增强安全性
    @Get("/sum/{number1}/{number2}")
    public BigDecimal sum(@PathVariable BigDecimal number1, @PathVariable BigDecimal number2) {
        return number1.add(number2);
    }

    // Calculates the difference between two numbers
    @Get("/subtract/{number1}/{number2}")
    public BigDecimal subtract(@PathVariable BigDecimal number1, @PathVariable BigDecimal number2) {
        return number1.subtract(number2);
    }

    // Calculates the product of two numbers
    @Get("/multiply/{number1}/{number2}")
    public BigDecimal multiply(@PathVariable BigDecimal number1, @PathVariable BigDecimal number2) {
# 扩展功能模块
        return number1.multiply(number2);
    }

    // Calculates the division of two numbers, with error handling for division by zero
    @Get("/divide/{number1}/{number2}")
# 改进用户体验
    public Optional<BigDecimal> divide(@PathVariable BigDecimal number1, @PathVariable BigDecimal number2) {
        if (number2.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return Optional.of(number1.divide(number2, BigDecimal.ROUND_HALF_UP));
    }

    // Calculates the remainder of two numbers
# 优化算法效率
    @Get("/remainder/{number1}/{number2}")
    public BigDecimal remainder(@PathVariable BigDecimal number1, @PathVariable BigDecimal number2) {
        return number1.remainder(number2);
    }

    // Calculates the power of a number
    @Get("/power/{number}/{exponent}")
    public BigDecimal power(@PathVariable BigDecimal number, @PathVariable int exponent) {
        BigDecimal result = BigDecimal.ONE;
        for (int i = 0; i < exponent; i++) {
# 添加错误处理
            result = result.multiply(number);
        }
        return result;
    }
}
