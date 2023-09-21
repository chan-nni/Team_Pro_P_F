package spring.Pro_P_F.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cy_seq;

    private String cyId;
    private String cy_pwd;
    private String company_name;
    private String manager_name;
    private String manager_email;
    private String manager_phone;
    private String intro;
    private String link;
//    private String img;
    private String location;

}
