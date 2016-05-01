package com.biju.redpencil;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;


public class RedPencil2Test {
	
	@Test
	public void shouldAllowPromotionWhenPriceReductionIs10Percent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencil2 item = new RedPencil2(initialPrice);
		item.setUpdatedPrice(new BigDecimal(90));
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsLessThanFivePercent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencil2 item = new RedPencil2(initialPrice);
		item.setUpdatedPrice(new BigDecimal(97));
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldAllowPromotionWhenPriceReductionIsFivePercent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencil2 item = new RedPencil2(initialPrice);
		item.setUpdatedPrice(new BigDecimal(95));
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsLessThanFivePercentForMultipleUpdates() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencil2 item = new RedPencil2(initialPrice);
		item.setUpdatedPrice(new BigDecimal(90));
		item.setUpdatedPrice(new BigDecimal(90));
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsGreaterThanThirtyPercent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencil2 item = new RedPencil2(initialPrice);
		item.setUpdatedPrice(new BigDecimal(60));
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldAllowPromotionWhenPriceReductionIsEqualToThirtyPercent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencil2 item = new RedPencil2(initialPrice);
		item.setUpdatedPrice(new BigDecimal(70));
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsGreaterThanThirtyPercentForMultipleUpdates() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencil2 item = new RedPencil2(initialPrice);
		item.setUpdatedPrice(new BigDecimal(90));
		item.setUpdatedPrice(new BigDecimal(90));
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
}
