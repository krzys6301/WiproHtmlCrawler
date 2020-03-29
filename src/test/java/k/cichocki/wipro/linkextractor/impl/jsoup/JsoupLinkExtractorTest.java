package k.cichocki.wipro.linkextractor.impl.jsoup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import k.cichocki.wipro.linkextractor.Link;
import k.cichocki.wipro.linkextractor.LinkExtractor;

public class JsoupLinkExtractorTest {

    private String baseAddr = "http://wiprodigital.com";

    private URL baseUrl = toURL("http://wiprodigital.com");

    private URL pageWithImg = toURL(baseAddr + "/img");
    private URL pageWithA = toURL(baseAddr + "/a");
    private URL pageWithScript = toURL(baseAddr + "/script");
    private URL pageWithLink = toURL(baseAddr + "/link");
    private URL pageWithIframe = toURL(baseAddr + "/iframe");

    private Map<URL, Element> urlToBodyContent = new ConcurrentHashMap<>();

    private JsoupLinkExtractor linkExtractor = new JsoupLinkExtractor(creteDocumentLoader());

    @Before
    public void before() {
	urlToBodyContent.put(pageWithA, new Element("a", "href", "someUrl"));
	urlToBodyContent.put(pageWithImg, new Element("img", "src", "someImgUrl"));
	urlToBodyContent.put(pageWithScript, new Element("srcipt", "src", "someScriptUrl"));
	urlToBodyContent.put(pageWithLink, new Element("link", "href", "someLinkUrl"));
	urlToBodyContent.put(pageWithIframe, new Element("iframe", "src", "someIframeUrl"));

    }

    public void testURL(URL url, boolean isResource) throws IOException {
	List<Link> links = linkExtractor.extractLinks(url);
	assertEquals(1, links.size());
	Link link = links.get(0);
	assertEquals(isResource, link.isResource());
	assertEquals(url.toExternalForm(), link.getBaseUrl());
	assertEquals(urlToBodyContent.get(url).url, link.getUrl());
    }

    @Test
    public void testExtractA() throws IOException {
	testURL(pageWithA, false);
    }

    @Test
    public void testExtractImg() throws IOException {
	testURL(pageWithImg, true);
    }

    @Test
    public void testExtractScript() throws IOException {
	testURL(pageWithScript, true);
    }

    @Test
    public void testExtractLink() throws IOException {
	testURL(pageWithScript, true);
    }

    @Test
    public void testExtractIframe() throws IOException {
	testURL(pageWithScript, true);
    }

    @Test(expected = IOException.class)
    public void testConnectionError() throws IOException {
	JsoupDocumentLoader loader = mock(JsoupDocumentLoader.class);
	when(loader.loadDocument(baseUrl)).thenThrow(new IOException());
	JsoupLinkExtractor linkExtractor = new JsoupLinkExtractor(loader);
	linkExtractor.extractLinks(baseUrl);
    }

    private JsoupDocumentLoader creteDocumentLoader() {
	return new JsoupDocumentLoader() {

	    @Override
	    public Document loadDocument(URL url) throws IOException {
		String bodyContent = urlToBodyContent.get(url).toString();
		String html = " <!DOCTYPE html>\r\n<html><body>" + bodyContent + "</body></html>";
		return Jsoup.parse(html);
	    }
	};
    }

    private URL toURL(String url) {
	try {
	    return new URL(url);
	} catch (MalformedURLException e) {
	    throw new RuntimeException("This url for test should be valid: " + url);
	}
    }

    private static class Element {
	private String tag;
	private String attr;
	private String url;

	public Element(String tag, String attr, String url) {
	    this.tag = tag;
	    this.attr = attr;
	    this.url = url;
	}

	public String toString() {
	    return "<" + tag + " " + attr + "=\"" + url + "\"/>";
	}
    }
}
