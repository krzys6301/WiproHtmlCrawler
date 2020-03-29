package k.cichocki.wipro.linkextractor;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import jdk.jfr.Threshold;

public interface LinkExtractor {

	List<Link> extractLinks(URL url) throws IOException;

}
