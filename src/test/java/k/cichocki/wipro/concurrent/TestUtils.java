package k.cichocki.wipro.concurrent;

import java.util.Map;
import java.util.Map.Entry;

import k.cichocki.wipro.linkextractor.Link;
import k.cichocki.wipro.result.CrawlingResult;

public class TestUtils {
    public static void printResults(Map<String, CrawlingResult> results) {
	    for (Entry<String, CrawlingResult> e : results.entrySet()) {
	        System.out.println("PAGE: " + e.getKey()+" "+ e.getValue().getInfo());
	        
	        for (Link link : e.getValue().getLinks()) {
	    		System.out.println(link);
	        }
	        
	    }
	}
}
