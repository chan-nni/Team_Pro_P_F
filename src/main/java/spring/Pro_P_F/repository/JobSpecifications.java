package spring.Pro_P_F.repository;

import org.springframework.data.jpa.domain.Specification;
import spring.Pro_P_F.domain.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class JobSpecifications {
    public static Specification<Job> filterBy(WorkType work, EmployType employ, AreaType area, String keyword, JobStatus jobStatus) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (work != null) {
                predicates.add(criteriaBuilder.equal(root.get("work"), work));
            }

            if (employ != null) {
                predicates.add(criteriaBuilder.equal(root.get("employ"), employ));
            }

            if (area != null) {
                predicates.add(criteriaBuilder.equal(root.get("area"), area));
            }

            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + keyword + "%"));
            }

            if (jobStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), jobStatus));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

