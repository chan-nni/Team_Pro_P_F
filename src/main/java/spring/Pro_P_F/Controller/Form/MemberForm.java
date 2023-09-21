package spring.Pro_P_F.Controller.Form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "id를 입력해주세요")
    private String m_id;

    private String m_pwd;

    private String m_name;

    private String m_phone;

    private String m_email;

    private String m_git;

}
