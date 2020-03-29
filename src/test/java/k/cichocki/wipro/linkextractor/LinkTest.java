package k.cichocki.wipro.linkextractor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinkTest {

    String base = "http://wiprodigital.com";
    String spec = "http://wiprodigital.com/spec";

    @Test
    public void testCreateLink() {
	Link link = new Link(base, spec, false);
	assertEquals(base, link.getBaseUrl());
	assertEquals(spec, link.getUrl());
	assertEquals(link.isResource(), false);
    }

    @Test
    public void testCreateResource() {
	Link link = new Link(base, spec, true);
	assertEquals(base, link.getBaseUrl());
	assertEquals(spec, link.getUrl());
	assertEquals(link.isResource(), true);
    }
}
