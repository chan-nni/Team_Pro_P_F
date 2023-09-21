package spring.Pro_P_F.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.Pro_P_F.domain.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    // 추가적인 쿼리 메서드 정의 가능

    // 시리즈 이름으로 시리즈 엔티티를 찾는 메서드
    Series findByName(String s_name);
}

