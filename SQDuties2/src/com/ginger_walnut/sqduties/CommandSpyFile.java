
package com.ginger_walnut.sqduties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandSpyFile implements Runnable {

	private File file;
	private String message;
	public static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd h:mm:ss a");

	public CommandSpyFile(String p, String type, String message) {

		this.file = new File(SQDuties.getPluginMain().getDataFolder().getAbsolutePath() + "/CommandSpy/" + p + "/" + type + ".txt");
		this.message = message;
		if (!this.file.exists()) {
			try {
				this.file.getParentFile().mkdirs();
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void run() {

		try {
			Date date = new Date();
			String formattedDate = sdf.format(date);
			try {
				FileWriter filewriter = new FileWriter(this.file, true);
				BufferedWriter bw = new BufferedWriter(filewriter);
				bw.newLine();
				bw.write("[" + formattedDate + "]" + this.message);
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} catch (Exception ex) {
			System.out.println("Thread Exception: " + ex);
		}
	}

	// Runnable task = new CommandSpyFile(this.file, this.player);
	// new Thread(task, "CommandSpy").start();

}
