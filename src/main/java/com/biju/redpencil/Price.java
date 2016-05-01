package com.biju.redpencil;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Price {
	private BigDecimal value;
	private LocalDate date;

}
