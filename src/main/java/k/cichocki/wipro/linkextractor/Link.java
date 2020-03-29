package k.cichocki.wipro.linkextractor;

import java.net.MalformedURLException;

public class Link {

    private final String url;

    private final String baseUrl;

    private final boolean resource;

    public Link(String baseUrl, String url, boolean resource) {
	this.baseUrl = baseUrl;
	this.url = url;
	this.resource = resource;
    }

    public String getBaseUrl() {
	return baseUrl;
    }

    public String getUrl() {
	return url;
    }

    public boolean isResource() {
	return resource;
    }

    public ResolvedLink resolve() throws MalformedURLException {
	return new ResolvedLink(this);
    }

    @Override
    public String toString() {
	return getClass().getName() + ": \r\n\t"
		+ "type:    " + (isResource() ? "RESOURCE " : "LINK ")+ "\r\n\t"
		+ "url:     " + url + "\r\n\t"
		+ "baseUrl: " + baseUrl;

    }

}
