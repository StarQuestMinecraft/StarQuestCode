
package us.higashiyama.george.Currencies;

import java.io.Serializable;

import org.bukkit.entity.Player;

import us.higashiyama.george.SQTrading.CC3Wrapper;
import us.higashiyama.george.SQTrading.CC3Wrapper.CC3Currency;

import com.greatmancode.craftconomy3.Cause;

public class Credits implements Serializable, Currency {

	private double credits;

	private static final long serialVersionUID = 3122520575811516789L;

	public Credits(double c) {

		credits = c;
	}

	@Override
	public String getAlias() {

		return credits + " credits";
	}

	@Override
	public boolean hasCurrency(Player player) {

		return CC3Wrapper.hasEnough(credits, player.getName(), CC3Currency.CREDITS);
	}

	@Override
	public void removeCurrency(Player player) {

		CC3Wrapper.withdraw(credits, player.getName(), CC3Currency.CREDITS, Cause.PLUGIN, "SQTraders credits withdraw");

	}

	@Override
	public void giveCurrency(Player player) {

		CC3Wrapper.deposit(credits, player.getName(), CC3Currency.CREDITS, Cause.PLUGIN, "SQTraders credits deposit");

	}

}
