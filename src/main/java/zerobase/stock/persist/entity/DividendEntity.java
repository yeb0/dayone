package zerobase.stock.persist.entity;

import lombok.*;
import zerobase.stock.model.Dividend;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "DIVIDEND")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"companyId", "date"}
                )
        }
)
public class DividendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long companyId;

    private LocalDateTime date;

    private String dividend;


    public DividendEntity(Long companyId, Dividend dividend) {
        this.companyId = companyId;
        this.dividend = dividend.getDividend();
        this.date = dividend.getDate();
    }
}
