package backend;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Data {
	private List<Payment> listPayments;
	private Map<String, BigDecimal> mapAmounts;
	private Map<String, BigDecimal> mapRates;

	public Data() {
		listPayments = new ArrayList<Payment>();
		mapAmounts = new TreeMap<String, BigDecimal>();
		mapRates = new TreeMap<String, BigDecimal>();

		initDefaults();
	}

	public void addPayment(Payment payment) {
		synchronized (mapAmounts) {
			listPayments.add(payment);
			BigDecimal valueOld = mapAmounts.get(payment.getCurrency());

			if (valueOld == null) {
				valueOld = new BigDecimal(0);
			}

			mapAmounts.put(payment.getCurrency(), payment.getAmount().add(valueOld));
		}
	}

	public void printData() {
		synchronized (mapAmounts) {
			BigDecimal value;
			BigDecimal rate;
			StringBuilder sb;
			MathContext mc = new MathContext(6, RoundingMode.HALF_EVEN);
			System.out.println("Amounts at " + new Date() + ":");
			for (String code : mapAmounts.keySet()) {
				value = mapAmounts.get(code);
				if (value.doubleValue() != 0) {
					sb = new StringBuilder();
					sb.append(code).append(" ").append(value);
					rate = mapRates.get(code);
					if (rate != null) {
						sb.append(" (USD ").append(value.divide(rate, mc))
							.append(")");
					}
					System.out.println(sb.toString());
				}
			}

			System.out.println();
		}
	}

	private void initDefaults() {
		mapRates.put("GBP", new BigDecimal(0.6454));
		mapRates.put("CAD", new BigDecimal(1.32206));
		mapRates.put("EUR", new BigDecimal(0.8947));
		mapRates.put("HKD", new BigDecimal(7.75));
		mapRates.put("RMB", new BigDecimal(6.374));
		mapRates.put("NZD", new BigDecimal(1.5807));
	}
}
