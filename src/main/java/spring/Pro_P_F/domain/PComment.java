package spring.Pro_P_F.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_seq")
    private Long seq;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
