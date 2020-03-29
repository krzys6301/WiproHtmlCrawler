package k.cichocki.wipro.linkextractor.impl.jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import k.cichocki.wipro.linkextractor.Link;
import k.cichocki.wipro.linkextractor.LinkExtractor;

public class JsoupLinkExtractor implements LinkExtractor {
    
    private JsoupDocumentLoader doucmentLoader;

    public JsoupLinkExtractor() {
	this.doucmentLoader = url -> Jsoup.connect(url.toExternalForm()).get();
    }
    
    public JsoupLinkExtractor(JsoupDocumentLoader doucmentLoader)  {
	this.doucmentLoader = doucmentLoader;
      }

    @Override
    public List<Link> extractLinks(URL url) throws IOException {
	return linksStream(url).collect(Collectors.toList());
    }

    private Stream<Link> linksStream(URL url) throws IOException {
	Document doc = doucmentLoader.loadDocument(url);
	return Stream.concat(collectAttributes(doc, "href", url), collectAttributes(doc, "src", url));
    }

    private Stream<Link> collectAttributes(Document doc, String attrName, URL baseUrl) {
	return doc.getElementsByAttribute(attrName).stream()
		.map(e -> createLink(e, attrName, baseUrl.toExternalForm()));
    }

    private Link createLink(Element element, String attrName, String baseUrl) {
	boolean resource = isResource(element.normalName());
	String url = element.attr(attrName);
	return new Link(baseUrl, url, resource);
    }

    private boolean isResource(String normalName) {
	switch (normalName) {
	case "a":
	case "iframe":
	    return false;
	default:
	    return true;
	}
    }

}
