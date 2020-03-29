package k.cichocki.wipro.linkextractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;

import org.junit.Test;

public class ResolvedLinkTest {
    String base = "http://wiprodigital.com";
    String spec = "http://wiprodigital.com/spec";
    String external = "http://www.google.pl";
    String malformed = "#malformed_url";

    @Test
    public void testResolveLink() throws MalformedURLException {
	Link link = new Link(base, spec, false);
	ResolvedLink resolvedLink = link.resolve();
	assertNotNull(resolvedLink);
    }

    @Test
    public void testResolveLinkIsReource() throws MalformedURLException {
	Link link = new Link(base, spec, true);
	ResolvedLink resolvedLink = link.resolve();
	assertEquals(resolvedLink.isResource(), true);
    }

    @Test
    public void testResolveLinkIsRegularLink() throws MalformedURLException {
	Link link = new Link(base, spec, false);
	ResolvedLink resolvedLink = link.resolve();
	assertEquals(resolvedLink.isResource(), false);
    }

    @Test
    public void testIntenalLink() throws MalformedURLException {
	Link link = new Link(base, spec, false);
	ResolvedLink resolvedLink = link.resolve();
	assertEquals(resolvedLink.isExternal(), false);
    }

    @Test
    public void testExtrenalLink() throws MalformedURLException {
	Link link = new Link(base, external, false);
	ResolvedLink resolvedLink = link.resolve();
	assertEquals(resolvedLink.isExternal(), true);
    }

    @Test(expected = MalformedURLException.class)
    public void testMalformedUrlLink() throws MalformedURLException {
	Link link = new Link(base, malformed, false);
	ResolvedLink resolvedLink = link.resolve();
    }

}
