THIS FILE IS UNDER CONSTRUCTION
===============================

This is simple project, that crawls the page "http://wiprodigital.com" and all found subpages in the same domain.
The result of this crawling is a file, named following a pattern "results_dd_MM_yyyy_HH_mm_ss.xml".
This file contains information about the root page and all its subpages which could easily be used to create map of the link connections beetween the pages.


<b>Structure of the output file</b>

Information about the root page that was crawled is stored under XPATH:
* "k.cichocki.wipro.result.Result/baseUrl"
Knowing the root page, you can search for a matching url string to map from this page to its subpages in each XPATH 
* "k.cichocki.wipro.result.Result/results/entry/string", then in the same path 
* "k.cichocki.wipro.result.Result/results/entry/" you will find elements:
** "k.cichocki.wipro.result.CrawlingResult" which contain 
*** "info" which could be one of CRAWLED, EXTERNAL, MALFORMED_URL, CONNECTION_ERROR.
** "Links" which contain ements:
*** "k.cichocki.wipro.linkextractor.Link" which contain 
**** "url" the crawled url
**** "baseUrl" the url of the page that was the origin of this url, from which it was crawled. 
**** "resource" [true|false] determining if this link is a resource

<b>Requirements:</b>

* jdk >= 1.9
* maven (I have used 3.6.3, not tested on other version, environment var "JAVA_HOME" should be set to point the jdk root folder)


<b>HOW TOP BUILD:</b>


cd to '${project.dir}/' and issue command:

mvn clean package

creates a jars in:

${project.dir}/target/WiproHtmlCrawler.jar
${project.dir}/target/WiproHtmlCrawler-jar-with-dependencies.jar

test results are stored in:
${project.dir}/target/surefire-reports/

<b>How to run the jar</b>

java -jar target/WiproHtmlCrawler-jar-with-dependencies.jar [OPIONAL: destination file name]

if run without arguments, creates a file in current dir following a pattern "results_dd_MM_yyyy_HH_mm_ss.xml" containing the crawling results.



