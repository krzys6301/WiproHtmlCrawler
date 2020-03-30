<b>**THIS FILE IS UNDER CONSTRUCTION**</b>

<b>HOW TOP BUILD:</b>

mvn clean package

creates a jar in:

${project.dir}/target/WiproHtmlCrawler.jar


<b>How to run:</b>

java -jar WiproHtmlCrawler.jar [optional destination file name]

if run without arguments, creates a file in current dir following a pattern "results_dd_MM_yyyy_HH_mm_ss.xml" containinge the crawling results.


<b>Requirements:</b>

* jdk >= 1.9
* maven (I have used 3.6.3, not tested on other version, environment var "JAVA_HOME" should be set to the jdk root folder)


