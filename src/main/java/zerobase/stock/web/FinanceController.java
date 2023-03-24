package zerobase.stock.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
public class FinanceController {

    @GetMapping("/dividend/{companyName}") // @PathVariable ?  -> String companyName 파라미터로 받는 값인듯. int 형이라면 뭐 1로 들어온다던지
    public ResponseEntity<?> searchFinance(@PathVariable String companyName) {
        return null;
    }
}
