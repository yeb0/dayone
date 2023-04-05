package zerobase.stock.persist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.stock.persist.entity.CompanyEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByTicker(String ticker); // 존재여부

    Optional<CompanyEntity> findByName(String name); // Optional 효과 .. NullPointerException 방지, 값이 없는 경우 처리도 코드적으로 조금 더 깔끔하게 정리 가능

    Page<CompanyEntity> findByNameStartingWithIgnoreCase(String s, Pageable limit); // Like 연산자 전용
}
