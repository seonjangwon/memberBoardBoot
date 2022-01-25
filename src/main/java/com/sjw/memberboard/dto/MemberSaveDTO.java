package com.sjw.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO {

    @NotBlank(message = "필수 항목입니다")
    @Length(min = 2, max = 20, message = "2~20자로 적어주세요")
    private String memberEmail;
    @NotBlank(message = "필수 항목입니다")
    @Length(min = 2, max = 20, message = "2~20자로 적어주세요")
    private String memberPassword;
    @NotBlank(message = "필수 항목입니다")
    @Length(min = 2, max = 10, message = "2~10자로 적어주세요")
    private String memberName;
    @NotBlank(message = "필수 항목입니다")
    private String memberPhone;
    private MultipartFile memberFile;
    private String memberFilename;
}
