package spring.Pro_P_F.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Pro_P_F.domain.CComment;

public interface CCommentRepository extends JpaRepository<CComment, Long> {
}
