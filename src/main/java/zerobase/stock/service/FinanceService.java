package zerobase.stock.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import zerobase.stock.model.Company;
import zerobase.stock.model.Dividend;
import zerobase.stock.model.ScrapedResult;
import zerobase.stock.model.constants.CacheKey;
import zerobase.stock.persist.entity.CompanyEntity;
import zerobase.stock.persist.entity.DividendEntity;
import zerobase.stock.persist.repository.CompanyRepository;
import zerobase.stock.persist.repository.DividendRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    // 요청이 잦은지?
    // 자주 변경되는 데이터인지?

    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName) {
        log.info("search company -> " + companyName);
        // 1. 회사명 기준으로 회사 정보 조회하기
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명 입니다."));
        // 2. 조회된 회사 ID 로 배당금 정보를 조회하기
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());
        // 3. 결과 조합 후 리턴하기
        List<Dividend> dividends = dividendEntities.stream()
                                                    .map(e -> new Dividend(e.getDate(), e.getDividend()))
                                                    .collect(toList());

        return new ScrapedResult(new Company(company.getTicker(), company.getName()),
                                dividends);
    }
}
