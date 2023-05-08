package com.app.webcrawler.util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Component
public class CrawlerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerUtil.class);
    private final Set<URL> visitedUrls = new HashSet<>();
    private final int MAX_DEPTH = 5; // Maximum depth to crawl
    private String host;// The host name of the starting URL
    private BufferedWriter successWriter;// A writer to write successful crawled URLs
    private BufferedWriter errorWriter;// A writer to write URLs that failed to crawl

    public CrawlerUtil() {
        try {
            this.successWriter = new BufferedWriter(new FileWriter("success.txt"));// Creating the success file writer
            this.errorWriter = new BufferedWriter(new FileWriter("error.txt"));// Creating the error file writer
        } catch (IOException e) {
            LOGGER.error("Error while initializing file writers: {}", e.getMessage());
        }
    }
    /**
     * Starts crawling the web page with the given starting URL.
     * Initializes the host and then calls the `crawl` method to traverse the web pages recursively.
     *
     * @param startingUrl the URL to start crawling from
     */
    public void startCrawling(String startingUrl) {
        try {
            URL url = new URL(startingUrl);
            host = url.getHost();
            crawl(url, 0);// Starting to crawl the website from the starting URL
        } catch (IOException e) {
            LOGGER.error("Error while crawling:", e);
        } finally {
            try {
                successWriter.close();
                errorWriter.close();
            } catch (IOException e) {
                LOGGER.error("Error while closing file writers: {}", e.getMessage());
            }
        }
    }
    /**
     * Recursively crawls the given URL and its child URLs up to a maximum depth.
     *
     * @param url the URL to crawl
     * @param depth the current depth of the crawl
     */

    public void crawl(URL url, int depth) {
        if (depth > MAX_DEPTH) {// If maximum depth has been reached, stop crawling
            return;
        }
        if (!visitedUrls.contains(url) && isSameDomain(url)) {// If the URL has not been visited and is from the same domain, crawl it
            visitedUrls.add(url);// Adding the URL to the visited URLs set
            printVisitedUrl(url, depth);// Printing the URL that has been visited

            try {
                Document doc = Jsoup.connect(url.toString()).get();// Parsing the HTML content of the URL
                Elements links = doc.select("a[href]");

                for (Element link : links) {// Getting all the hyperlinks in the page
                    String href = link.attr("href");// Getting the hyperlink URL
                    if (!href.isEmpty()) {// If the hyperlink URL is not empty, crawl it
                        URL childUrl = new URL(url, href);// Creating the child URL object
                        crawl(childUrl, depth + 1);// Crawling the child URL
                    }
                }
                successWriter.write(url.toString() + "\n");
            } catch (MalformedURLException e) {
                LOGGER.error("Error while crawling: Invalid URL {}", url);
                writeError(url.toString(), e.getMessage());
            } catch (IOException e) {
                LOGGER.error("Error while crawling: {}", e.getMessage());
                writeError(url.toString(), e.getMessage());
            }
        }
    }
    /**
     * Checks if the given URL is in the same domain as the starting URL.
     *
     * @param url the URL to check
     * @return true if the URL is in the same domain, false otherwise
     */
    public boolean isSameDomain(URL url) { return url.getHost().endsWith(String.format(".%s", host)) || url.getHost().equals(host);}

    public Set<URL> getVisitedUrls() {
        return visitedUrls;
    }
    private void printVisitedUrl(URL url, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("    ");
        }
        System.out.println(indent + url.toString());
    }
    /**
     * Writes an error message to the error file.
     *
     * @param url the URL that caused the error
     * @param errorMessage the error message to write
     */
    private void writeError(String url, String errorMessage) {
        try {
            errorWriter.write(url + " : " + errorMessage + "\n");
        } catch (IOException e) {
            LOGGER.error("Error while writing to error file: {}", e.getMessage());
        }
    }
}
