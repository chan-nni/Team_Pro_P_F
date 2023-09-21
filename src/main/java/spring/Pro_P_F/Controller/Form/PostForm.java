package spring.Pro_P_F.Controller.Form;

import lombok.Getter;
import lombok.Setter;
import spring.Pro_P_F.domain.Series;


import java.time.LocalDate;

@Getter @Setter
public class PostForm {

    private String title;
    private String content;
    private LocalDate date;
    private Series series;
}
