package k.cichocki.wipro;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import k.cichocki.wipro.result.Result;
import k.cichocki.wipro.serializer.XStreamResultSerializer;

public class Crawler {


    public void crawlPage(String url, OutputStream ous) throws FileNotFoundException {
	int threadsToUse = Runtime.getRuntime().availableProcessors() * 16;
	ConcurrentCrawler crawler = ConcurrentCrawlerFactory.newDefaultConcurrentCrawler(threadsToUse);
	Result result = crawler.crawlPage(url);
	XStreamResultSerializer serializer = new XStreamResultSerializer();
	serializer.serialize(result, ous);
    }

}
