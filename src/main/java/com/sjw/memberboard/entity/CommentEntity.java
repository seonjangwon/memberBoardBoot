package com.sjw.memberboard.entity;

import com.sjw.memberboard.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String commentWriter;
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;


    public static CommentEntity toSaveComment(CommentSaveDTO commentSaveDTO, BoardEntity boardEntity, MemberEntity memberEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setCommentWriter(memberEntity.getMemberEmail());
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        commentEntity.setMemberEntity(memberEntity);
        return commentEntity;
    }
}
