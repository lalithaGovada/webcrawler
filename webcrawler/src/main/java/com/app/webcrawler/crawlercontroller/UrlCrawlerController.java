package com.app.webcrawler.crawlercontroller;

import com.app.webcrawler.util.CrawlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
This is the UrlCrawlerController class
It is annotated with @RestController, indicating that it will handle incoming HTTP requests and return HTTP responses.
*/
@RestController
public class UrlCrawlerController  {
    @Autowired
    CrawlerUtil crawlerUtil;
    @GetMapping ("/crawlUrls")
    /*The getVisitedUrls method is annotated with @GetMapping("/crawlUrls"), which maps the method to the /crawlUrls endpoint of the web service. It takes a single query parameter url and uses it to start crawling.*/
    public void getVisitedUrls (@RequestParam(name = "url")  String url) throws Exception{
        if(url == null || url.isBlank())
            throw new Exception("url is not valid.");
         crawlerUtil.startCrawling(url); //Contains the core logic for crawling URLs.
    }
   }
