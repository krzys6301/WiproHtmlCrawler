THIS FILE IS UNDER CONSTRUCTION
===============================

This is simple project, that crawls the page "http://wiprodigital.com" and all found subpages in the same domain.
The result of this crawling is a file, by default named following a pattern "results_dd_MM_yyyy_HH_mm_ss.xml" and located in the same folder as jar file, optionally you can specify other file location as a target, this is done by directly specifying single argument pointing to the file when running the jar.

This output file contains information about the root page and all its subpages which could easily be used to create map of the link connections between the pages.


<b>Structure of the output file</b>

Information about the root page that was crawled is stored under XPATH:
* "k.cichocki.wipro.result.Result/baseUrl"
Knowing the root page, you can search for a matching url string to map from this page to its subpages. 
In each XPATH:
* "k.cichocki.wipro.result.Result/results/entry/" you will find elements:
** "string" the url of the crawled page.  
** "k.cichocki.wipro.result.CrawlingResult" containing information about results of crawling for the above page 
*** "info" which could be one of CRAWLED, EXTERNAL, MALFORMED_URL, CONNECTION_ERROR.
** "Links" which contain ements:
*** "k.cichocki.wipro.linkextractor.Link" which contain 
**** "url" the crawled url
**** "baseUrl" the url of the page that was the origin of this url, from which it was crawled. 
**** "resource" [true|false] determining if this link is a resource


<b>Requirements:</b>

* jdk >= 1.9
* maven (I have used 3.6.3, not tested on other version, environment var "JAVA_HOME" should be set to point the jdk root folder)


<b>HOW TO BUILD:</b>


cd to '${project.dir}/' and issue command:

mvn clean package

creates a jars in:

* ${project.dir}/target/WiproHtmlCrawler.jar
* ${project.dir}/target/WiproHtmlCrawler-jar-with-dependencies.jar

test results are stored in:
* ${project.dir}/target/surefire-reports/

<b>How to run the jar</b>

* ${project.dir}/java -jar target/WiproHtmlCrawler-jar-with-dependencies.jar [OPTIONAL: destination file name]

if run without arguments, creates a file in current dir following a pattern "results_dd_MM_yyyy_HH_mm_ss.xml" containing the crawling results.

Running with single argument will assume it is the path to the output file, for instance invoking:

* ${project.dir}/java -jar target\WiproHtmlCrawler-jar-with-dependencies.jar out.xml

will result in storing the crawling result in a file:

* ${project.dir}/out.xml


<b>TO BE DONE</b>

* Allow for specifying the page to be crawled
* Allow for specifying the number of threads to be used;
* Add some other formats of results
* Create benchmark for crawling with different concurrency levels.
* Could crate a crawler that execute the scripts on the page and detect outgoing request, adding them to the discovered links for checking.
 

