package com.app.webcrawler;

import com.app.webcrawler.util.CrawlerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.URL;
import org.junit.jupiter.api.*;


import java.util.Set;

@SpringBootTest
class WebcrawlerApplicationTests {
	@Autowired
	CrawlerUtil crawlerUtil;

	@Test
	void contextLoads() {

	}
	private CrawlerUtil crawler;

	@BeforeEach
	void setUp() {
		crawler = new CrawlerUtil();
	}


	@Test
	void getVisitedUrlsTest(){
		crawlerUtil.startCrawling("https://google.com/");
	}
	void testCrawl() throws Exception {
		URL url = new URL("https://www.google.com");
		crawler.crawl(url, 0);
		assertTrue(crawler.getVisitedUrls().contains(url), "The starting URL should have been visited");
		assertEquals(1, crawler.getVisitedUrls().size(), "Only the starting URL should have been visited");
	}


	private void assertTrue(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}

	private void assertFalse(boolean condition, String message) {
		if (condition) {
			throw new AssertionError(message);
		}
	}

	private void assertEquals(int expected, int actual, String message) {
		if (expected != actual) {
			throw new AssertionError(message + " ==> Expected: " + expected + ", Actual: " + actual);
		}
	}
}



