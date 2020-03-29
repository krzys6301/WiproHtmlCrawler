package k.cichocki.wipro;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

import k.cichocki.wipro.concurrent.PageCrawlerTask;
import k.cichocki.wipro.linkextractor.Link;
import k.cichocki.wipro.linkextractor.impl.jsoup.JsoupLinkExtractor;

public final class ConcurrentCrawlerFactory {

    public static ConcurrentCrawler newDefaultConcurrentCrawler(int threadsToUse) {
	
	Function<String, PageCrawlerTask> pageCrawlerGenerator = baseUrl -> new PageCrawlerTask(new Link(baseUrl, baseUrl, false), new JsoupLinkExtractor());
	ForkJoinPool pool = new ForkJoinPool(threadsToUse);
	return new ConcurrentCrawler(pool, pageCrawlerGenerator);
		
    }
}
