package k.cichocki.wipro;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Start {
    public static void main(String[] args) throws FileNotFoundException {
	String url = "http://wiprodigital.com";
	String dstFileName = getFileName(args);
	Crawler crawler = new Crawler();
	try (FileOutputStream fos = new FileOutputStream(dstFileName)) {
	    crawler.crawlPage(url, fos);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

    }

    private static String getFileName(String[] args) {
	if (args.length == 0) {
	    return "result_" + date() + ".xml";
	}
	return args[0];
    }

    private static String date() {
	SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
	return sdf.format(new Date());
    }

}
