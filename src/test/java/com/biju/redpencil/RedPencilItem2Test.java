package com.biju.redpencil;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;


public class RedPencilItem2Test {
	
	private LocalDate TODAY = LocalDate.now();
	private LocalDate TWENTY_DAYS_AGO = TODAY.minusDays(20);
	private LocalDate FIFTY_DAYS_AGO = TODAY.minusDays(50);
	
	@Test
	public void shouldAllowPromotionWhenPriceReductionIs10Percent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, FIFTY_DAYS_AGO);
		item.setUpdatedPrice(new BigDecimal(90), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsLessThanFivePercent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, FIFTY_DAYS_AGO);
		item.setUpdatedPrice(new BigDecimal(97), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldAllowPromotionWhenPriceReductionIsFivePercent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, FIFTY_DAYS_AGO);
		item.setUpdatedPrice(new BigDecimal(95), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsLessThanFivePercentForMultipleUpdates() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, FIFTY_DAYS_AGO);
		item.setUpdatedPrice(new BigDecimal(90), TODAY);
		item.setUpdatedPrice(new BigDecimal(90), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsGreaterThanThirtyPercent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, FIFTY_DAYS_AGO);
		item.setUpdatedPrice(new BigDecimal(60), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldAllowPromotionWhenPriceReductionIsEqualToThirtyPercent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, FIFTY_DAYS_AGO);
		item.setUpdatedPrice(new BigDecimal(70), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsGreaterThanThirtyPercentForMultipleUpdates() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, FIFTY_DAYS_AGO);
		item.setUpdatedPrice(new BigDecimal(90), TODAY);
		item.setUpdatedPrice(new BigDecimal(90), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldNotAllowPromotionIfLastPriceUpdateWasLessThan30Days() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, TWENTY_DAYS_AGO);
		item.setUpdatedPrice(new BigDecimal(90), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldAllowPromotionIfLastPriceUpdateWas30DaysAgo() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, TODAY.minusDays(30));
		item.setUpdatedPrice(new BigDecimal(90), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldNotAllowPromotionIfPromotionIsMoreThan30Days() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, TODAY.minusDays(100));
		item.setUpdatedPrice(new BigDecimal(90), TODAY.minusDays(40));
		item.setUpdatedPrice(new BigDecimal(80), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldAllowPromotionIfPromotionIs30Days() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem2 item = new RedPencilItem2(initialPrice, TODAY.minusDays(100));
		item.setUpdatedPrice(new BigDecimal(90), TODAY.minusDays(30));
		item.setUpdatedPrice(new BigDecimal(80), TODAY);
		
		boolean result = item.isOnPromotion();
		
		assertThat(result, is(true));
	}
}
