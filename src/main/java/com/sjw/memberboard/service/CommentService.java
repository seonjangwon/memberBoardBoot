package com.sjw.memberboard.service;

import com.sjw.memberboard.dto.CommentDetailDTO;
import com.sjw.memberboard.dto.CommentSaveDTO;

import java.util.List;

public interface CommentService {
    List<CommentDetailDTO> findById(Long boardId);

    void save(CommentSaveDTO commentSaveDTO);

    void delete(Long commentId);
}
