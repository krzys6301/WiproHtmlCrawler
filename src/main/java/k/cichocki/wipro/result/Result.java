package k.cichocki.wipro.result;

import java.util.Map;

public class Result {
    private String baseUrl;

    private Map<String, CrawlingResult> results;

    public Result(String baseUrl, Map<String, CrawlingResult> results) {
	this.baseUrl = baseUrl;
	this.results = results;
    }

    public String getBaseUrl() {
	return baseUrl;
    }

    public Map<String, CrawlingResult> getResults() {
	return results;
    }

}
