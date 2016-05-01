package com.biju.redpencil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.Data;

@Data
public class RedPencilItem2 {
	
	private BigDecimal price;
	private BigDecimal updatedPrice;
	private LocalDate initialDate;
	private LocalDate lastUpdatedDate;
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
		if(isOnPromotion()){
			this.promotionStartDate = this.lastUpdatedDate;
		}
	}
	
	public boolean isOnPromotion(){
		return isPriceReductionLessThanOrEqualTo5Percent(this.price, this.updatedPrice) &&
			   isPriceReductionGreaterThanOrEqualTo30Percent(this.price, this.updatedPrice) &&
			   isLastPriceUpdateGreaterThanOrEqualTo30Days(this.initialDate, this.lastUpdatedDate) &&
			   isNotOnPromotionAndForRunningMoreThan30Days(this.promotionStartDate, this.lastUpdatedDate);
	}

	private boolean isNotOnPromotionAndForRunningMoreThan30Days(LocalDate promotionStartDate, LocalDate lastUpdatedDate) {
		if(promotionStartDate != null){
			long between = ChronoUnit.DAYS.between(promotionStartDate, lastUpdatedDate);
			return between <= 30;
		}
		return true;
	}

	private boolean isLastPriceUpdateGreaterThanOrEqualTo30Days(LocalDate initialDate, LocalDate lastUpdatedDate) {
		long between = ChronoUnit.DAYS.between(initialDate, lastUpdatedDate);
		return between >= 30;
	}

	private boolean isPriceReductionLessThanOrEqualTo5Percent(BigDecimal initialPrice, BigDecimal updatedPrice) {
		BigDecimal percentageDifference = getPercentageDifference(initialPrice, updatedPrice);
		return percentageDifference.compareTo(BigDecimal.valueOf(0.05)) >= 0;
	}
	
	private boolean isPriceReductionGreaterThanOrEqualTo30Percent(BigDecimal initialPrice, BigDecimal updatedPrice){
		BigDecimal percentageDifference = getPercentageDifference(initialPrice,updatedPrice);
		return percentageDifference.compareTo(BigDecimal.valueOf(0.3)) <= 0;
	}

	private BigDecimal getPercentageDifference(BigDecimal initialPrice, BigDecimal updatedPrice) {
		return initialPrice.subtract(updatedPrice).divide(initialPrice, MathContext.DECIMAL128);
	}

}
