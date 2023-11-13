package spring.Pro_P_F.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Getter @Setter
public class Job {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "j_seq")
    private Long seq;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enddate;

    private int person;
    private String content;

    // 모집직무
    @Enumerated(EnumType.STRING)
    private WorkType work;


    // 채용형태
    @Enumerated(EnumType.STRING)
    private EmployType employ;

    // 지역
    @Enumerated(EnumType.STRING)
    private AreaType area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cy_id")
    private Company company;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scraps = new ArrayList<>();
}
