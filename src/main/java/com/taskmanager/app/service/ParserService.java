package com.taskmanager.app.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class ParserService {

    public String getRandomQuote() {
        try {
            // Added timeout to prevent infinite hanging
            Document doc = Jsoup.connect("http://quotes.toscrape.com/random")
                    .timeout(3000)
                    .get();
            Element quoteElement = doc.select(".quote .text").first();
            if (quoteElement != null) {
                return quoteElement.text();
            }
        } catch (Exception e) {
            // Log the error but don't crash the application
            System.err.println("Error fetching quote: " + e.getMessage());
        }
        return "You can do anything, but not everything. (Fallback Quote)";
    }
}
