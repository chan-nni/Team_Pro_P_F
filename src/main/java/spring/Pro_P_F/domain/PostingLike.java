package spring.Pro_P_F.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostingLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "l_seq")
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "p_seq")
    private Posting posting;

    @ManyToOne
    @JoinColumn(name = "mid")
    private Member member;


}
