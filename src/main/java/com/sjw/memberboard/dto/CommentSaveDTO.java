package com.sjw.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSaveDTO {

    private Long boardId;
    private String memberEmail;

    @NotBlank(message = "필수 항목 입니다")
    @Length(min = 2, max = 20,message = "2~20자로 작성해주세요")
    private String commentContents;
}
