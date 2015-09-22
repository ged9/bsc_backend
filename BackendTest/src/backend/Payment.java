package backend;

import java.math.BigDecimal;

public class Payment {
	private String currency;
	private BigDecimal amount;

	public Payment(String currency, BigDecimal amount) {
		this.currency = currency;
		this.amount = amount;
	}

	public static Payment getPayment(String str) {
		String[] data = str.split(" ");

		if (data.length == 2 && data[0].length() == 3) {
			try {
				return new Payment(data[0].toUpperCase(), new BigDecimal(data[1]));
			} catch (NumberFormatException e) {
				return null;
			}
		}

		return null;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
