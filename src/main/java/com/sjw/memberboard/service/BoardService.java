package com.sjw.memberboard.service;

import com.sjw.memberboard.dto.BoardDetailDTO;
import com.sjw.memberboard.dto.BoardSaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface BoardService {
    void save(BoardSaveDTO boardSaveDTO) throws IOException;

    Page<BoardDetailDTO> findAll(Pageable pageable);

    BoardDetailDTO findById(Long boardId);

    void update(BoardDetailDTO boardDetailDTO) throws IOException;

    Page<BoardDetailDTO> searchList(Pageable pageable, int searchType, String keyword);

    void delete(Long boardId);
}
