package com.biju.redpencil;

import static java.math.BigDecimal.valueOf;
import static java.time.temporal.ChronoUnit.DAYS;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RedPencilItem {
	private static final String UPDATED_PRICE_NOT_PROVIDED = "Updated price needs to be provided before checking promotion";
	private static final int PROMOTION_DAYS_LIMIT = 30;
	private static final double LOWER_BOUND = 0.05;
	private static final double UPPER_BOUND = 0.3;
	
	private final Price initialPrice;
	private Price updatedPrice;
	private LocalDate promotionSetDate;
	
	public RedPencilItem(final Price initialPrice){
		this.initialPrice = initialPrice;
	}
	
	public void setUpdatedPrice(Price updatedPrice){
		this.updatedPrice = updatedPrice;
		if(this.isOnPromotion()){
			this.promotionSetDate = updatedPrice.getDate();
		}
	}
	
	public boolean isOnPromotion() {
		if(updatedPrice == null){
			throw new RuntimeException(UPDATED_PRICE_NOT_PROVIDED);
		}
		BigDecimal priceReduction = initialPrice.getValue().subtract(updatedPrice.getValue());
		BigDecimal percentageReduction = priceReduction.divide(initialPrice.getValue());
		return 
			isUpdatedPriceDifferenceGreaterThanOrEqualTo5Percent(percentageReduction)  && 
			isUpdatedPriceDifferenceLessThanOrEqualTo30Percent(percentageReduction) &&
			isCurrentDateDifferenceMoreThanOrEqualTo30Days() &&
			isPromotionRunningForLessThanOrEqualTo30Days();
	}

	private boolean isPromotionRunningForLessThanOrEqualTo30Days() {
		if(promotionSetDate != null){
			long noOfDaysSincePromotionStart = DAYS.between(promotionSetDate, LocalDate.now());
			return noOfDaysSincePromotionStart <= PROMOTION_DAYS_LIMIT;
		}
		return true;
	}

	private boolean isCurrentDateDifferenceMoreThanOrEqualTo30Days() {
		long daysSincePriceChange = DAYS.between(initialPrice.getDate(), updatedPrice.getDate());
		return daysSincePriceChange >= PROMOTION_DAYS_LIMIT;
	}

	private boolean isUpdatedPriceDifferenceLessThanOrEqualTo30Percent(BigDecimal percentageReduction) {
		return percentageReduction.compareTo(valueOf(UPPER_BOUND)) <= 0;
	}

	private boolean isUpdatedPriceDifferenceGreaterThanOrEqualTo5Percent(BigDecimal percentageReduction) {
		return percentageReduction.compareTo(valueOf(LOWER_BOUND)) >= 0;
	}
}
