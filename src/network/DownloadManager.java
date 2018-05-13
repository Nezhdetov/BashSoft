package network;

import io.OutputWriter;
import staticData.ExceptionMessages;
import staticData.SessionData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadManager {

    public static void download(String fileUrl) {
        URL url;
        ReadableByteChannel rbc = null;
        FileOutputStream fos = null;

        try {
            if (Thread.currentThread().getName().equals("main")) {
                OutputWriter.writeMessageOnNewLine("Started downloading..");
            }

            url = new URL(fileUrl);
            rbc = Channels.newChannel(url.openStream());
            String fileName = extractNameOfFile(url.toString());
            File file = new File(SessionData.currentPath + "/" + fileName);
            fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            if (Thread.currentThread().getName().equals("main")) {
                OutputWriter.writeMessageOnNewLine("Download complete.");
            }
        } catch (IOException e) {
            OutputWriter.displayException(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (rbc != null) {
                    try {
                        rbc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void downloadOnNewThread(String fileUrl) {
        Thread thread = new Thread(() -> download(fileUrl));
        thread.setDaemon(false);
        OutputWriter.writeMessageOnNewLine(
                String.format("Worker thread %d started download..", thread.getId()));
        SessionData.threadPool.add(thread);
        thread.start();
    }

    private static String extractNameOfFile(String fileUrl) throws MalformedURLException {
        int indexOfLastSlash = fileUrl.lastIndexOf('/');
        if (indexOfLastSlash == -1) {
            throw new MalformedURLException(ExceptionMessages.INVALID_PATH);
        }

        return fileUrl.substring(indexOfLastSlash + 1);
    }
}