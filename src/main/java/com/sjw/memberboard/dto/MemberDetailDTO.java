package com.sjw.memberboard.dto;

import com.sjw.memberboard.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailDTO {

    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private MultipartFile memberFile;
    private String memberFilename;
    private LocalDateTime memberTime;

    private String URL;

    public static MemberDetailDTO toDetailMember(MemberEntity memberEntity) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setId(memberEntity.getId());
        memberDetailDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDetailDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDetailDTO.setMemberName(memberEntity.getMemberName());
        memberDetailDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDetailDTO.setMemberFilename(memberEntity.getMemberFilename());
        if(memberEntity.getUpdateTime()==null){
            memberDetailDTO.setMemberTime(memberEntity.getCreateTime());
        } else{
            memberDetailDTO.setMemberTime(memberEntity.getUpdateTime());
        }
        return memberDetailDTO;
    }
}
