package k.cichocki.wipro.serializer;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;

import k.cichocki.wipro.result.Result;

public class XStreamResultSerializer {

    public void serialize(Result result, OutputStream ous) throws FileNotFoundException {
	XStream xStream = new XStream();
	xStream.toXML(result, ous);
    }
}
