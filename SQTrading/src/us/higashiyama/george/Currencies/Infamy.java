
package us.higashiyama.george.Currencies;

import java.io.Serializable;

import org.bukkit.entity.Player;

import us.higashiyama.george.SQTrading.CC3Wrapper;
import us.higashiyama.george.SQTrading.CC3Wrapper.CC3Currency;

import com.greatmancode.craftconomy3.Cause;

public class Infamy implements Serializable, Currency {

	private static final long serialVersionUID = -222555805862092618L;
	private double infamy;

	public Infamy(double i) {

		infamy = i;
	}

	@Override
	public String getAlias() {

		return infamy + " infamy";
	}

	@Override
	public boolean hasCurrency(Player player) {

		return CC3Wrapper.hasEnough(infamy, player.getName(), CC3Currency.CREDITS);
	}

	@Override
	public void removeCurrency(Player player) {

		CC3Wrapper.withdraw(infamy, player.getName(), CC3Currency.CREDITS, Cause.PLUGIN, "SQTraders infamy withdraw");

	}

	@Override
	public void giveCurrency(Player player) {

		CC3Wrapper.deposit(infamy, player.getName(), CC3Currency.CREDITS, Cause.PLUGIN, "SQTraders infamy deposit");

	}
}
