package k.cichocki.wipro.result;

import java.util.Collections;
import java.util.List;

import k.cichocki.wipro.linkextractor.Link;

public class CrawlingResult {

    private final CrawlingInfo info;

    private final List<Link> links;

    private final Link link;

    public CrawlingResult(Link link, CrawlingInfo info) {
	this.link = link;
	this.info = info;
	this.links = Collections.emptyList();
    }

    public CrawlingResult(Link link, List<Link> links, CrawlingInfo info) {
	this.link = link;
	this.info = info;
	this.links = links;
    }

    public Link getLink() {
	return link;
    }

    public CrawlingInfo getInfo() {
	return info;
    }

    public List<Link> getLinks() {
	return links;
    }

    public String toString() {
	StringBuilder sb = new StringBuilder(info + ", link: " + link + "\r\n");
	sb.append(" conatins " + links.size() + " subpages");

//		for (Link subPage : getLinks()) {
//			sb.append("\t" + (subPage.isResource() ? "RESOURCE" : "LINK") + subPage.getUrl() + "\r\n");
//		}
	return sb.toString();
    }

}
