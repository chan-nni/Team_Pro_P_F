package spring.Pro_P_F.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class CommunityLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_seq")
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "c_seq")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "mid")
    private Member member;

}
