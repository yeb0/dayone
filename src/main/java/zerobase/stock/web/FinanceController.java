package zerobase.stock.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.stock.model.ScrapedResult;
import zerobase.stock.service.FinanceService;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor
public class FinanceController {

    private final FinanceService financeService;
    @GetMapping("/dividend/{companyName}") // @PathVariable ?  -> String companyName 파라미터로 받는 값인듯. int 형이라면 뭐 1로 들어온다던지
    public ResponseEntity<?> searchFinance(@PathVariable String companyName) {
        ScrapedResult result = this.financeService.getDividendByCompanyName(companyName);
        return ResponseEntity.ok(result);
    }
}
