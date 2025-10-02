// 代码生成时间: 2025-10-03 03:51:23
package com.example.discount;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.MediaType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;
import io.micronaut.core.annotation.Nullable;

@Controller("/api/discount")
public class DiscountService {

    private final DiscountRepository discountRepository;

    // 注入折扣优惠仓库
    @Inject
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Get("/apply/{productId}")
    public DiscountResponse applyDiscount(String productId) {
        try {
            // 根据产品ID查找折扣优惠
            Optional<Discount> discount = discountRepository.findByProductId(productId);

            if (discount.isPresent()) {
                Discount currentDiscount = discount.get();
                BigDecimal discountedPrice = calculateDiscountedPrice(currentDiscount.getOriginalPrice(), currentDiscount.getDiscountRate());
                // 返回折扣后的价格
                return new DiscountResponse(true, discountedPrice);
            } else {
                // 无折扣优惠，返回原价
                return new DiscountResponse(false, BigDecimal.ZERO);
            }
        } catch (Exception e) {
            // 错误处理
            return new DiscountResponse(false, BigDecimal.ZERO);
        }
    }

    // 计算折扣价格
    private BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, BigDecimal discountRate) {
        BigDecimal discountPrice = originalPrice.subtract(originalPrice.multiply(discountRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
        return discountPrice;
    }
}

/**
 * 折扣优惠实体类
 */
class Discount {
    private String productId;
    private BigDecimal originalPrice;
    private BigDecimal discountRate;

    // Getters and Setters...
}

/**
 * 折扣优惠仓库接口
 */
interface DiscountRepository {
    Optional<Discount> findByProductId(String productId);
}

/**
 * 折扣响应实体类
 */
class DiscountResponse {
    private boolean isDiscountApplied;
    private BigDecimal discountedPrice;

    public DiscountResponse(boolean isDiscountApplied, BigDecimal discountedPrice) {
        this.isDiscountApplied = isDiscountApplied;
        this.discountedPrice = discountedPrice;
    }

    public boolean isDiscountApplied() {
        return isDiscountApplied;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }
}
