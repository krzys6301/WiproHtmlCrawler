package k.cichocki.wipro.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import k.cichocki.wipro.linkextractor.Link;
import k.cichocki.wipro.linkextractor.LinkExtractor;
import k.cichocki.wipro.result.CrawlingInfo;
import k.cichocki.wipro.result.CrawlingResult;

public class PageCrawlerTest {

    @Test
    public void testAlreadyCrawled() throws IOException {
	String baseAddress = "http://wiprodigital.com";
	Link link = new Link(baseAddress, baseAddress, false);
	LinkExtractor linkExtractor = mock(LinkExtractor.class);
	when(linkExtractor.extractLinks(link.resolve().getResolvedUrl())).thenReturn(List.of(link));
	PageCrawlerTask pc = new PageCrawlerTask(link, linkExtractor);
	pc.compute();
	Map<String, CrawlingResult> results = pc.getCrawlingResults();
	assertEquals(1, results.size());
	assertTrue(results.keySet().contains(link.getUrl()));
	assertTrue(CrawlingInfo.CRAWLED.equals(results.get(link.getUrl()).getInfo()));
    }

    @Test
    public void testConnectionError() throws IOException {
	String baseAddress = "http://wiprodigital.com";
	Link link = new Link(baseAddress, baseAddress, false);
	LinkExtractor linkExtractor = mock(LinkExtractor.class);
	when(linkExtractor.extractLinks(link.resolve().getResolvedUrl()))
		.thenThrow(new IOException("connection error"));
	PageCrawlerTask pc = new PageCrawlerTask(link, linkExtractor);
	pc.compute();
	Map<String, CrawlingResult> results = pc.getCrawlingResults();
	assertEquals(1, results.size());
	assertTrue(results.keySet().contains(link.getUrl()));
	assertTrue(CrawlingInfo.CONNECTION_ERROR.equals(results.get(link.getUrl()).getInfo()));
    }

    @Test
    public void testCircullarLinks() throws IOException {
	String baseAddress = "http://wiprodigital.com";
	String subpageAddress = baseAddress + "/subpage";
	Link link = new Link(baseAddress, baseAddress, false);
	Link subpageLink = new Link(baseAddress, subpageAddress, false);
	LinkExtractor linkExtractor = mock(LinkExtractor.class);
	when(linkExtractor.extractLinks(link.resolve().getResolvedUrl())).thenReturn(List.of(subpageLink));
	when(linkExtractor.extractLinks(subpageLink.resolve().getResolvedUrl())).thenReturn(List.of(link));
	PageCrawlerTask pc = new PageCrawlerTask(link, linkExtractor);
	pc.compute();
	Map<String, CrawlingResult> results = pc.getCrawlingResults();
	assertEquals(2, results.size());
	assertTrue(results.keySet().contains(link.getUrl()));
	assertTrue(CrawlingInfo.CRAWLED.equals(results.get(link.getUrl()).getInfo()));
	assertTrue(results.keySet().contains(subpageLink.getUrl()));
	assertTrue(CrawlingInfo.CRAWLED.equals(results.get(subpageLink.getUrl()).getInfo()));

    }

    @Test
    public void testMalformedURL() throws IOException {
	String baseAddress = "http_malformed_url://malformed_url";
	Link link = new Link(baseAddress, baseAddress, false);
	PageCrawlerTask pc = new PageCrawlerTask(link, null);
	pc.compute();
	Map<String, CrawlingResult> results = pc.getCrawlingResults();
	assertEquals(1, results.size());
	assertTrue(results.keySet().contains(link.getUrl()));
	assertTrue(CrawlingInfo.MALFORMED_URL.equals(results.get(link.getUrl()).getInfo()));
    }

    @Test
    public void testResourceLink() throws IOException {
	String baseAddress = "http://wiprodigital.com/somejs.js";
	Link link = new Link(baseAddress, baseAddress, true);
	LinkExtractor linkExtractor = mock(LinkExtractor.class);
	PageCrawlerTask pc = new PageCrawlerTask(link, linkExtractor);
	pc.compute();
	Map<String, CrawlingResult> results = pc.getCrawlingResults();
	assertTrue(results.isEmpty());
    }

    @Test
    public void testEexternalLink() throws IOException {
	String baseAddress = "http://wiprodigital.com";
	String externalAddress = "http://google.com";
	Link link = new Link(baseAddress, baseAddress, false);
	Link externalLink = new Link(baseAddress, externalAddress, false);
	LinkExtractor linkExtractor = mock(LinkExtractor.class);
	when(linkExtractor.extractLinks(link.resolve().getResolvedUrl())).thenReturn(List.of(externalLink));
	when(linkExtractor.extractLinks(externalLink.resolve().getResolvedUrl())).thenReturn(List.of(link));
	PageCrawlerTask pc = new PageCrawlerTask(link, linkExtractor);
	pc.compute();
	Map<String, CrawlingResult> results = pc.getCrawlingResults();
	assertEquals(2, results.size());
	assertTrue(results.keySet().contains(link.getUrl()));
	assertTrue(CrawlingInfo.CRAWLED.equals(results.get(link.getUrl()).getInfo()));
	assertTrue(results.keySet().contains(externalLink.getUrl()));
	assertTrue(CrawlingInfo.EXTERNAL.equals(results.get(externalLink.getUrl()).getInfo()));
    }

}
