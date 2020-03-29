package k.cichocki.wipro.concurrent;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

import k.cichocki.wipro.linkextractor.Link;
import k.cichocki.wipro.linkextractor.LinkExtractor;
import k.cichocki.wipro.linkextractor.ResolvedLink;
import k.cichocki.wipro.result.CrawlingInfo;
import k.cichocki.wipro.result.CrawlingResult;

public class PageCrawlerTask extends RecursiveAction {

    private static final long serialVersionUID = 1128898404123679379L;

    private final ConcurrentHashMap<String, CrawlingResult> crawlingResults;

    private final Link link;

    private final LinkExtractor extractor;

    public PageCrawlerTask(Link link, LinkExtractor extractor) {
	this.crawlingResults = new ConcurrentHashMap<String, CrawlingResult>();
	this.extractor = extractor;
	this.link = link;
    }

    private PageCrawlerTask(Link link, LinkExtractor extractor, ConcurrentHashMap<String, CrawlingResult> crawlingResults) {
	this.link = link;
	this.extractor = extractor;
	this.crawlingResults = crawlingResults;
    }

    private CrawlingResult crawlPage() {
	ResolvedLink resolvedLink;
	try {
	    resolvedLink = link.resolve();
	} catch (MalformedURLException e) {
	    return new CrawlingResult(link, CrawlingInfo.MALFORMED_URL);
	}
	if (resolvedLink.isExternal()) {
	    return new CrawlingResult(link, CrawlingInfo.EXTERNAL);
	}
	List<Link> links;
	try {
	    links = extractor.extractLinks(resolvedLink.getResolvedUrl());
	} catch (Exception e) {
	    return new CrawlingResult(link, CrawlingInfo.CONNECTION_ERROR);
	}
	return new CrawlingResult(link, links, CrawlingInfo.CRAWLED);
    }

    private boolean isResource() {
	return link.isResource();
    }

    public Map<String, CrawlingResult> getCrawlingResults() {
	return crawlingResults;
    }

    @Override
    protected void compute() {
	if (isAlreadyCrawled() || isResource()) {
	    return;
	}

	CrawlingResult result = crawlPage();
	addResult(result);
	List<PageCrawlerTask> subCrawlers = createSubtasks(result.getLinks());
	invokeAll(subCrawlers);
    }

    private boolean isAlreadyCrawled() {
	return crawlingResults.containsKey(link.getUrl());
    }

    private List<PageCrawlerTask> createSubtasks(List<Link> links) {
	return links.stream().map(this::createCrawler).collect(Collectors.toList());
    }

    private void addResult(CrawlingResult result) {
	crawlingResults.put(result.getLink().getUrl(), result);
    }

    private PageCrawlerTask createCrawler(Link link) {
	return new PageCrawlerTask(link, extractor, crawlingResults);
    }

}