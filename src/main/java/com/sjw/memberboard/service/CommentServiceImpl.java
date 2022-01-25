package com.sjw.memberboard.service;

import com.sjw.memberboard.dto.CommentDetailDTO;
import com.sjw.memberboard.dto.CommentSaveDTO;
import com.sjw.memberboard.entity.BoardEntity;
import com.sjw.memberboard.entity.CommentEntity;
import com.sjw.memberboard.entity.MemberEntity;
import com.sjw.memberboard.repository.BoardRepository;
import com.sjw.memberboard.repository.CommentRepository;
import com.sjw.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final BoardRepository br;
    private final MemberRepository mr;
    private final CommentRepository cr;
    @Override
    public List<CommentDetailDTO> findById(Long boardId) {
        List<CommentEntity> commentList = br.findById(boardId).get().getCommentList();
        List<CommentDetailDTO> commentDetailDTOList = new ArrayList<>();
        for (CommentEntity c: commentList){
            commentDetailDTOList.add(CommentDetailDTO.toDetailDTO(c));
        }
        return commentDetailDTOList;
    }

    @Override
    public void save(CommentSaveDTO commentSaveDTO) {
        MemberEntity memberEntity = mr.findByMemberEmail(commentSaveDTO.getMemberEmail()).get();
        BoardEntity boardEntity = br.findById(commentSaveDTO.getBoardId()).get();
        cr.save(CommentEntity.toSaveComment(commentSaveDTO,boardEntity,memberEntity));
    }

    @Override
    public void delete(Long commentId) {
        cr.deleteById(commentId);
    }
}
