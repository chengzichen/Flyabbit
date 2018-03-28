package com.dhc.timberhelper;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DiskLogHandler extends Handler {
    private final String folder;
	private final int maxFileSize;
	private final String fileName;

	public DiskLogHandler(String folder, String fileName, int maxFileSize) {
		this(getDefaultLooper(), folder, fileName, maxFileSize);
	}

	public DiskLogHandler(Looper looper, String folder, String fileName, int maxFileSize) {
		super(looper);
		this.folder = folder;
		this.fileName = fileName;
		this.maxFileSize = maxFileSize;
	}

	private static Looper getDefaultLooper() {
		HandlerThread ht = new HandlerThread("AndroidFileLogger");
		ht.start();
		return ht.getLooper();
	}

	@SuppressWarnings("checkstyle:emptyblock")
	@Override
	public void handleMessage(Message msg) {
		String content = (String) msg.obj;

		FileWriter fileWriter = null;
		File logFile = getLogFile(folder, fileName);

		try {
			fileWriter = new FileWriter(logFile, true);

			writeLog(fileWriter, content);

			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			if (fileWriter != null) {
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e1) { /* fail silently */ }
			}
		}
	}

	/**
	 * This is always called on a single background thread.
	 * Implementing classes must ONLY write to the fileWriter and nothing more.
	 * The abstract class takes care of everything else including close the stream and catching IOException
	 *
	 * @param fileWriter an instance of FileWriter already initialised to the correct file
	 */
	private void writeLog(FileWriter fileWriter, String content) throws IOException {
		fileWriter.append(content);
	}

	private File getLogFile(String folderName, String fileName) {
		File folder = new File(folderName);
		if (!folder.exists()) {
			//TODO: What if folder is not created, what happens then?
			folder.mkdirs();
		}

		int newFileCount = 0;
		File newFile;
		File existingFile = null;

		newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));
		while (newFile.exists()) {
			existingFile = newFile;
			newFileCount++;
			newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));
		}

		if (existingFile != null) {
			if (existingFile.length() >= maxFileSize) {
				return newFile;
			}
			return existingFile;
		}

		return newFile;

	}
}