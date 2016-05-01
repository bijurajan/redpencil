package com.biju.redpencil;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RedPencilItemTest {
	
	private static final LocalDate TODAY = LocalDate.now();
	private static final LocalDate FIFTY_DAYS_AGO = TODAY.minusDays(50);

	@Test
	public void shouldAllowPromotionWhenPriceReductionIs10Percent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, FIFTY_DAYS_AGO));
		item.setUpdatedPrice(new Price(new BigDecimal(90), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsLessThan5Percent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, FIFTY_DAYS_AGO));
		item.setUpdatedPrice(new Price(new BigDecimal(97), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsLessThan5PercentForMultipleUpdates() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, TODAY.minusDays(100)));
		item.setUpdatedPrice(new Price(new BigDecimal(90), TODAY.minusDays(20)));
		item.setUpdatedPrice(new Price(new BigDecimal(90), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPriceReductionIsMoreThan30Percent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, FIFTY_DAYS_AGO));
		item.setUpdatedPrice(new Price(new BigDecimal(50), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldAllowPromotionWhenPriceReductionIs5Percent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, FIFTY_DAYS_AGO));
		item.setUpdatedPrice(new Price(new BigDecimal(95), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldAllowPromotionWhenPriceReductionIs30Percent() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, FIFTY_DAYS_AGO));
		item.setUpdatedPrice(new Price(new BigDecimal(70), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(true));
	}
	
	@Test
	public void shouldNotAllowPromotionWhenPreviousPriceChangeWasLessThan30Days() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, TODAY.minusDays(20)));
		item.setUpdatedPrice(new Price(new BigDecimal(90), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldAllowPromotionWhenPreviousPriceChangeIs30DaysAgo() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, TODAY.minusDays(30)));
		item.setUpdatedPrice(new Price(new BigDecimal(90), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(true));
	}
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void shouldThrowExceptionIfPromotionCheckIsDoneWithoutUpdatedPriceEntered() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, TODAY.minusDays(30)));
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Updated price needs to be provided before checking promotion");
		
		item.isOnPromotionAfterUpdate();
	}
	
	@Test
	public void shouldNotAllowPromotionIfPromotionHasBeenRunningForMoreThan30Days() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, TODAY.minusDays(80)));
		item.setUpdatedPrice(new Price(new BigDecimal(90), TODAY.minusDays(40)));
		item.setUpdatedPrice(new Price(new BigDecimal(80), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(false));
	}
	
	@Test
	public void shouldNotProlongPromotionIfMultipleUpdatesDone() throws Exception {
		BigDecimal initialPrice = new BigDecimal(100);
		RedPencilItem item = new RedPencilItem(new Price(initialPrice, TODAY.minusDays(80)));
		item.setUpdatedPrice(new Price(new BigDecimal(90), TODAY.minusDays(40)));
		item.setUpdatedPrice(new Price(new BigDecimal(80), TODAY.minusDays(20)));
		item.setUpdatedPrice(new Price(new BigDecimal(70), TODAY));
		
		boolean result = item.isOnPromotionAfterUpdate();
		
		assertThat(result, is(false));
	}

}
