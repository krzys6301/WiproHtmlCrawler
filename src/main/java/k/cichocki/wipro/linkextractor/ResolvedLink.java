package k.cichocki.wipro.linkextractor;

import java.net.MalformedURLException;
import java.net.URL;

public final class ResolvedLink extends Link {

    private final URL resolvedBaseUrl;

    private final URL resolvedUrl;

    private final boolean extrenal;

    protected ResolvedLink(Link link) throws MalformedURLException {
	super(link.getBaseUrl(), link.getUrl(), link.isResource());
	resolvedBaseUrl = new URL(link.getBaseUrl());
	resolvedUrl = new URL(link.getUrl());
	extrenal = !resolvedBaseUrl.getHost().equals(resolvedUrl.getHost());
    }


    public boolean isExternal() {
	return extrenal;
    }


    public URL getResolvedBaseUrl() {
        return resolvedBaseUrl;
    }


    public URL getResolvedUrl() {
        return resolvedUrl;
    }
    
    
    @Override
    public String toString() {
        return super.toString() +"\r\n\t"
        	+"resolvedUrl: " + resolvedUrl.toExternalForm() + "\r\n\t"
        	+"external: " + extrenal;
    }
    
}
