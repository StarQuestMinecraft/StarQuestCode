package com.starquestminecraft.sqcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;


public class SharedConnection {
	Connection c;
	Semaphore lock;

	public SharedConnection(Connection c) {
		this.c = c;
		lock = new Semaphore(1);
	}

	public void acquire() {
		try {
			lock.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void release() {
		lock.release();
	}

	public boolean isInUse() {
		return lock.availablePermits() < 1;
	}

	public Connection getConnection() {
		return c;
	}

	public boolean ensureValid() {
		try {
			if (c == null || c.isClosed() || !c.isValid(1)) {
				if (c != null && !c.isClosed()) {
					try {
						c.close();
					} catch (SQLException e) {
						System.out.print("Exception caught");
					}
					c = null;
				}
				if ((Pool.username.equalsIgnoreCase("")) && (Pool.password.equalsIgnoreCase(""))) {
					c = DriverManager.getConnection(Pool.dsn);
				} else
					c = DriverManager.getConnection(Pool.dsn, Pool.username, Pool.password);

				if (c == null || c.isClosed())
					return false;
			}

			return true;
		} catch (SQLException e) {
			System.out.print("Error could not Connect to db " + Pool.dsn + ": " + e.getMessage());
		}
		return false;
	}
}
