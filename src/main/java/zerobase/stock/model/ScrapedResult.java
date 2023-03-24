package zerobase.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ScrapedResult {

    private Company company; // 어떤 회사인지 담은 company

    private List<Dividend> dividends; //

    public ScrapedResult() {
        this.dividends = new ArrayList<>();
    }
}
