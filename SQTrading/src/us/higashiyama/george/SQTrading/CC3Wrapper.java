
package us.higashiyama.george.SQTrading;

import com.greatmancode.craftconomy3.Cause;

public class CC3Wrapper {

	public static double getBalance(String name, CC3Currency currency) {

		return SQTrading.craftconomy.getAccountManager().getAccount(name).getBalance("default", currency.getName());
	}

	public static double deposit(double amount, String name, CC3Currency currency, Cause cause, String causeReason) {

		return SQTrading.craftconomy.getAccountManager().getAccount(name).deposit(amount, "default", currency.getName(), cause, causeReason);
	}

	public static double withdraw(double amount, String name, CC3Currency currency, Cause cause, String causeReason) {

		return SQTrading.craftconomy.getAccountManager().getAccount(name).withdraw(amount, "default", currency.getName(), cause, causeReason);
	}

	public static boolean hasEnough(double amount, String name, CC3Currency currency) {

		return SQTrading.craftconomy.getAccountManager().getAccount(name).hasEnough(amount, "default", currency.getName());
	}

	public enum CC3Currency {

		CREDITS("Credits"), INFAMY("Infamy");

		private String name;

		private CC3Currency(String name) {

			this.name = name;
		}

		public String getName() {

			return this.name;
		}

	}
}
