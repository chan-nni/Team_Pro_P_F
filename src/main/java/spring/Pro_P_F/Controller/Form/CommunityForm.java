package spring.Pro_P_F.Controller.Form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CommunityForm {

    // id는 자동
    private String category;
    private String title;
    private String content;
    private LocalDate date;

}
