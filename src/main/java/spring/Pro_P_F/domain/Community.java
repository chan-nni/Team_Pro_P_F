package spring.Pro_P_F.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Community {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private Member member;

    @Column(name = "c_category")
    private String category;

    @Column(name = "c_title")
    private String title;

    @Column(name = "c_content")
    private String content;

    private int c_like;


    @LastModifiedDate
    private LocalDate c_date;


}
