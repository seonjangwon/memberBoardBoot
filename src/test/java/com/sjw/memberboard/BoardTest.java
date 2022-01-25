package com.sjw.memberboard;

import com.sjw.memberboard.dto.BoardDetailDTO;
import com.sjw.memberboard.dto.BoardSaveDTO;
import com.sjw.memberboard.entity.BoardEntity;
import com.sjw.memberboard.repository.BoardRepository;
import com.sjw.memberboard.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardService bs;
    @Autowired
    private BoardRepository br;

    @Test
    @DisplayName("게시글 생성용")
    public void saveTest() {
        IntStream.rangeClosed(1,40).forEach(i->{
            BoardSaveDTO boardSaveDTO = new BoardSaveDTO();
            boardSaveDTO.setBoardTitle("testTitle"+i);
            boardSaveDTO.setBoardWriter("123");
            boardSaveDTO.setBoardContents("testContents"+i);
            try {
                bs.save(boardSaveDTO);
            } catch (IOException e) {
            }
        });
    }

    @Test
    @DisplayName("검색 테스트")
    public void searchTest() {
        List<BoardEntity> list = br.findByBoardContentsContainingOrBoardTitleContaining("1", "2");
        List<BoardDetailDTO> detailDTOList = new ArrayList<>();
        for(BoardEntity b : list) {
            detailDTOList.add(BoardDetailDTO.toDetailDTO(b));
        }
        for (BoardDetailDTO b: detailDTOList){
            System.out.println("b = " + b);
        }
    }
}
