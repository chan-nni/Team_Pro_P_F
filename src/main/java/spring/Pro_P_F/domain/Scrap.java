package spring.Pro_P_F.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Scrap {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrapSeq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "j_seq")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    private Member member;
}
