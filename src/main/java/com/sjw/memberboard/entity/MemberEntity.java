package com.sjw.memberboard.entity;

import com.sjw.memberboard.dto.MemberDetailDTO;
import com.sjw.memberboard.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member_table")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private String memberFilename;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardList= new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentList = new ArrayList<>();

    public static MemberEntity toSaveMember(MemberSaveDTO memberSaveDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        memberEntity.setMemberPhone(memberSaveDTO.getMemberPhone());
        memberEntity.setMemberFilename(memberSaveDTO.getMemberFilename());
        return memberEntity;
    }

    public static MemberEntity toDetailMember(MemberDetailDTO memberDetailDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDetailDTO.getId());
        memberEntity.setMemberEmail(memberDetailDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDetailDTO.getMemberPassword());
        memberEntity.setMemberName(memberDetailDTO.getMemberName());
        memberEntity.setMemberPhone(memberDetailDTO.getMemberPhone());
        memberEntity.setMemberFilename(memberDetailDTO.getMemberFilename());
        return memberEntity;
    }

}
