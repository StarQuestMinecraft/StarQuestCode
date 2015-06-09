package com.dibujaron.powerboostpurchaser;

import java.sql.Connection;

import net.countercraft.movecraft.bedspawns.Bedspawn;

public class BedspawnConnectionProvider implements ConnectionProvider{

	@Override
	public Connection getConnection() {
		return Bedspawn.cntx;
	}

}
