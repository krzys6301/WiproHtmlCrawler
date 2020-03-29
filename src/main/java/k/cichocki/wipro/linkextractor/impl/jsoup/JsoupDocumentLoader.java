package k.cichocki.wipro.linkextractor.impl.jsoup;

import java.io.IOException;
import java.net.URL;

import org.jsoup.nodes.Document;

public interface JsoupDocumentLoader {

    public Document loadDocument(URL url) throws IOException;
}
