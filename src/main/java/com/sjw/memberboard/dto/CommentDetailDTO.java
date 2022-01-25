package com.sjw.memberboard.dto;

import com.sjw.memberboard.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetailDTO {
    private Long id;
    private Long boardId;
    private String memberEmail;
    private String commentContents;
    private LocalDateTime commentTime;

    public static CommentDetailDTO toDetailDTO (CommentEntity commentEntity){
        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
        commentDetailDTO.setId(commentEntity.getId());
        commentDetailDTO.setBoardId(commentEntity.getBoardEntity().getId());
        commentDetailDTO.setMemberEmail(commentEntity.getCommentWriter());
        commentDetailDTO.setCommentContents(commentEntity.getCommentContents());
        if (commentEntity.getUpdateTime()==null){
            commentDetailDTO.setCommentTime(commentEntity.getCreateTime());
        }else {
            commentDetailDTO.setCommentTime(commentEntity.getUpdateTime());
        }
        return commentDetailDTO;
    }
}
