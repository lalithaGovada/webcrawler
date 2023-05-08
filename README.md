Web Crawler
              This is a Java Spring Boot application that crawls web pages and logs the URLs that have been visited. In order to parse HTML, the application uses the Jsoup library and the Spring Boot framework to build the web page.
Installation
- Clone this repository to your local machine using git clone https://github.com/lalithaGovada/webcrawler
- Navigate to the project directory: cd url-crawler
- Build the project using Maven: mvn package
Usage
- Start the application by running java -jar target/url-crawler.jar
- Open your web browser and navigate to http://localhost:8080/crawlUrls?url=http://example.com, replacing http://example.com with the URL you want to start crawling from.
- The application will start crawling URLs within the same domain as the starting URL up to a depth of 3 by default. You can customize the depth by appending &depth=5 to the URL, replacing 5 with the desired depth.
- Visited URLs will be printed to the console and written to a file called "success.txt" in the project directory. Any errors encountered during crawling will be written to a file called "error.txt" in the project directory.
Note: This application requires Java 11 or later to be installed on your machine.
Technical information 
The UrlCrawlerController class appears to be a Spring REST controller that defines a GET endpoint /crawlUrls that takes a query parameter url. When this endpoint is called, it invokes the startCrawling method of an instance of the CrawlerUtil class, passing the provided URL as a parameter.

The CrawlerUtil class contains the main logic of the program for crawling URLs. When the startCrawling method is invoked, it creates a new URL object from the provided starting URL and invokes the crawl method with the depth parameter set to 0.

The crawl method performs the actual crawling logic. It first checks if the provided URL is in the set of visited URLs and if it is in the same domain as the starting URL. If both conditions are true, it adds the URL to the set of visited URLs, prints it to the console, and then tries to fetch the HTML of the URL using the Jsoup library. It then extracts all of the links from the HTML and recursively invokes the crawl method for each child URL, increasing the depth by 1.

The isSameDomain method is a helper method that determines whether a given URL is in the same domain as the starting URL. This is done by comparing the host of the given URL to the host of the starting URL. If the given URL's host is the same as the starting URL's host or ends with the starting URL's host preceded by a dot, it is considered to be in the same domain.

The getVisitedUrls method is a simple getter method that returns the set of visited URLs.

To use this web crawler, send a GET request to the /crawlUrls endpoint with a url query parameter set to the starting URL. The program will crawl all URLs in the same domain as the starting URL up to a certain depth, and print the visited URLs to the console and write them to a file called "success.txt". If an error occurs while crawling a URL, the error will be written to a file called "error.txt
Dependencies
This project depends on the following libraries:
Spring Boot
Jsoup
These dependencies are managed by Maven, so you don't need to install them separately.
Credits
This application was developed by Govada Lalitha. Special thanks to Monzo team for her contributions to the project.
