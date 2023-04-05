package zerobase.stock;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import zerobase.stock.model.Company;
import zerobase.stock.model.ScrapedResult;
import zerobase.stock.scraper.YahooFinanceScraper;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling // 스케쥴링을 사용하기 위함
@EnableCaching // 캐시 (redis)
public class StockApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }
}
