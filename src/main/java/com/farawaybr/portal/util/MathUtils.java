package com.farawaybr.portal.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

	private MathUtils() {
		// TODO Auto-generated constructor stub
	}

	
	
	public static BigDecimal findHwManyPercentsYCorrespondsOverX(Number y, Number x) {
		BigDecimal ratioOfYOverX = new BigDecimal(y.doubleValue()).divide(new BigDecimal(x.doubleValue()));
		return ratioOfYOverX.multiply(new BigDecimal(100));
	}

	public static BigDecimal findHwManyPercentsYCorrespondsOverX(Number y, Number x, int quotientScale,
			RoundingMode mode) {
		BigDecimal ratioOfYOverX = new BigDecimal(y.doubleValue()).divide(new BigDecimal(x.doubleValue()),
				quotientScale, mode);
		return ratioOfYOverX.multiply(new BigDecimal(100));
	}

	public static BigDecimal findHwMuchXPercentCorrespondsOverWholeValue(BigDecimal percentageValue, BigDecimal whole) {
		BigDecimal percValueRatio100 = percentageValue.divide(new BigDecimal(100));
		return percValueRatio100.multiply(whole);
	}

	public static BigDecimal subtractValueByPercentage(Number percentage, BigDecimal value) {
		BigDecimal percentageValueOverWhole = findHwMuchXPercentCorrespondsOverWholeValue(
				new BigDecimal(percentage.doubleValue()), value);
		return value.subtract(percentageValueOverWhole);
	}

	public static BigDecimal addValueByPercentage(Number percentage, BigDecimal value) {
		BigDecimal percentageValueOverWhole = findHwMuchXPercentCorrespondsOverWholeValue(
				new BigDecimal(percentage.doubleValue()), value);
		return value.add(percentageValueOverWhole);
	}


}
