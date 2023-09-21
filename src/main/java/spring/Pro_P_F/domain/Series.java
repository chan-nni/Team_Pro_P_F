package spring.Pro_P_F.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Series {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long s_seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private Member member;

    @Column(name = "s_name")
    private String name;

    @LastModifiedDate
    private LocalDate s_date;

}
