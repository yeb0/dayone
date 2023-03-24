package zerobase.stock.scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import zerobase.stock.model.Company;
import zerobase.stock.model.Dividend;
import zerobase.stock.model.ScrapedResult;
import zerobase.stock.model.constants.Month;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Java 10부터 지역 변수에 대한 Type Inference가 가능한 **var** 키워드를 제공해줍니다.
 * 1. 가독성 향상
 * 2. 불필요한 중복을 제거하는 기능
 *
 * 우변의 타입으로 좌변(var) 타입 추측가능..
 */

@Component
public class YahooFinanceScraper implements Scraper{

    // 상수값은 대문자로 하는 것이 국룰 -> URL
    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";

    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s"; // 회사명 가져오는 경로

    private static final long START_TIME = 86400; // 60초 * 60분 * 24시간


    @Override
    public ScrapedResult scrap(Company company) {
        var scrapResult = new ScrapedResult(); // var ???????? -> var 가 아마도 ScrapedResult 의 타입 대체된 거로 추측..
        scrapResult.setCompany(company);
        try { // https://jsoup.org/apidocs/   <- Jsoup 확인해보기~
            long now = System.currentTimeMillis() / 1000;

            String url = String.format(STATISTICS_URL, company.getTicker(), START_TIME, now);

            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableEle = parsingDivs.get(0);

            Element tbody = tableEle.children().get(1);

            List<Dividend> dividends = new ArrayList<>();

            for (Element e : tbody.children()) {
                String txt = e.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }

                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.valueOf(splits[1].replace(",", ""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];

                if (month < 0) {
                    throw new RuntimeException("Unexpected Month enum value -> " + splits[0]);
                }

                dividends.add(Dividend.builder()
                                .date(LocalDateTime.of(year, month, day, 0, 0))
                                .dividend(dividend)
                                .build());

//                System.out.println(year + "/" + month + "/" + day + " --> " + dividend);

            }
            scrapResult.setDividends(dividends);


        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }

        return scrapResult;
    }

    @Override
    public Company scrapCompanyByTicker(String ticker) {
        String url = String.format(SUMMARY_URL, ticker, ticker);

        try {
            Document document = Jsoup.connect(url).get();
            Element titleEle = document.getElementsByTag("h1").get(0);
            String title = titleEle.text().split(" - ")[1].trim(); // 무조건 이렇게 해라! 는 아님. 회사의 데이터 특성 상 다 다를 수도 있음 일단은 강의에선 이렇게 해서 데이터를 추출함

            return Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
