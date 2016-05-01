package com.biju.redpencil;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RedPencil2 {
	
	private BigDecimal price;
	private BigDecimal updatedPrice;
	
	public RedPencil2(BigDecimal price){
		this.price = price;
	}

	public void setUpdatedPrice(BigDecimal updatedPrice) {
		if(this.updatedPrice == null){
			this.updatedPrice = updatedPrice;
		} else {
			this.price = updatedPrice;
		}
	}
	
	public boolean isOnPromotion(){
		return isPriceReductionLessThanOrEqualTo5Percent(this.price, this.updatedPrice) &&
			   isPriceReductionGreaterThanOrEqualTo30Percent(this.price, this.updatedPrice);
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
		return initialPrice.subtract(updatedPrice).divide(initialPrice);
	}

}
