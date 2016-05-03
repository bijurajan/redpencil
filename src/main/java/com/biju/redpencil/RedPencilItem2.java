package com.biju.redpencil;

import static java.math.BigDecimal.valueOf;
import static java.time.temporal.ChronoUnit.DAYS;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;

public class RedPencilItem2 {
	private static final String UPDATED_PRICE_NOT_PROVIDED = "Updated price needs to be provided before checking promotion";
	private static final int PRICE_UPDATE_DAYS_MAX = 30;
	private static final int PROMOTION_DAYS_MAX = 30;
	private static final double PERCENT_LOWER_BOUND = 0.05;
	private static final double PERCENT_UPPER_BOUND = 0.3;
	
	private BigDecimal price;
	private BigDecimal updatedPrice;
	private LocalDate initialDate;
	private LocalDate lastUpdatedDate;
	private boolean isOnPromotion;
	private LocalDate promotionStartDate;
	
	public RedPencilItem2(BigDecimal price, LocalDate initialDate){
		this.price = price;
		this.initialDate = initialDate;
	}

	public void setUpdatedPrice(BigDecimal updatedPrice, LocalDate lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
		if(this.updatedPrice == null){
			this.updatedPrice = updatedPrice;
		} else {
			this.price = this.updatedPrice;
			this.updatedPrice = updatedPrice;
		}
		if(isOnPromotion() && !isOnPromotion){
			this.isOnPromotion = true;
			this.promotionStartDate = this.lastUpdatedDate;
		}
	}
	
	public boolean isOnPromotion(){
		if(updatedPrice == null){
			throw new RuntimeException(UPDATED_PRICE_NOT_PROVIDED);
		}
		return isPriceReductionLessThanOrEqualTo5Percent(this.price, this.updatedPrice) &&
			   isPriceReductionGreaterThanOrEqualTo30Percent(this.price, this.updatedPrice) &&
			   isLastPriceUpdateGreaterThanOrEqualTo30Days(this.initialDate, this.lastUpdatedDate) &&
			   isNotOnPromotionAndForRunningMoreThan30Days(this.promotionStartDate, this.lastUpdatedDate);
	}

	private boolean isNotOnPromotionAndForRunningMoreThan30Days(LocalDate promotionStartDate, LocalDate lastUpdatedDate) {
		if(isOnPromotion){
			return DAYS.between(promotionStartDate, lastUpdatedDate) <= PROMOTION_DAYS_MAX;
		}
		return true;
	}

	private boolean isLastPriceUpdateGreaterThanOrEqualTo30Days(LocalDate initialDate, LocalDate lastUpdatedDate) {
		return DAYS.between(initialDate, lastUpdatedDate) >= PRICE_UPDATE_DAYS_MAX;
	}

	private boolean isPriceReductionLessThanOrEqualTo5Percent(BigDecimal initialPrice, BigDecimal updatedPrice) {
		BigDecimal percentageDifference = getPercentageDifference(initialPrice, updatedPrice);
		return percentageDifference.compareTo(valueOf(PERCENT_LOWER_BOUND)) >= 0;
	}
	
	private boolean isPriceReductionGreaterThanOrEqualTo30Percent(BigDecimal initialPrice, BigDecimal updatedPrice){
		BigDecimal percentageDifference = getPercentageDifference(initialPrice,updatedPrice);
		return percentageDifference.compareTo(valueOf(PERCENT_UPPER_BOUND)) <= 0;
	}

	private BigDecimal getPercentageDifference(BigDecimal initialPrice, BigDecimal updatedPrice) {
		return initialPrice.subtract(updatedPrice).divide(initialPrice, MathContext.DECIMAL128);
	}

}
