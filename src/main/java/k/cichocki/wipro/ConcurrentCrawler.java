package k.cichocki.wipro;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

import k.cichocki.wipro.concurrent.PageCrawlerTask;
import k.cichocki.wipro.result.Result;

public class ConcurrentCrawler {

    private Function<String, PageCrawlerTask> pageCrawlerGenerator;
    private ForkJoinPool pool;

    public ConcurrentCrawler(ForkJoinPool pool, Function<String, PageCrawlerTask> pageCrawlerGenerator) {
	this.pool = pool;
	this.pageCrawlerGenerator = pageCrawlerGenerator;

    }

    public Result crawlPage(String url) {
	PageCrawlerTask crawlerTask = pageCrawlerGenerator.apply(url);
	pool.invoke(crawlerTask);
	return new Result(url, crawlerTask.getCrawlingResults());
    }
    
    public void dispose() {
	pool.shutdown();
    }

}
