package com.sjw.memberboard.service;

import com.sjw.memberboard.dto.BoardDetailDTO;
import com.sjw.memberboard.dto.BoardSaveDTO;
import com.sjw.memberboard.entity.BoardEntity;
import com.sjw.memberboard.entity.MemberEntity;
import com.sjw.memberboard.repository.BoardRepository;
import com.sjw.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository br;
    private final MemberRepository mr;

    @Override
    public void save(BoardSaveDTO boardSaveDTO) throws IOException {
        if (boardSaveDTO.getBoardFile() != null) {
            MultipartFile file = boardSaveDTO.getBoardFile();
            String filename = file.getOriginalFilename();
            filename = System.currentTimeMillis() + filename;

            String savePath = "C:\\development\\source\\SpringBoot\\MemberBoard\\src\\main\\resources\\static\\upload\\" + filename;

            if (!file.isEmpty()) {
                file.transferTo(new File(savePath));
            }

            boardSaveDTO.setBoardFilename(filename);
        }

        MemberEntity memberEntity = mr.findByMemberEmail(boardSaveDTO.getBoardWriter()).get();
        br.save(BoardEntity.toSaveBoard(boardSaveDTO, memberEntity));
    }

    @Override
    public Page<BoardDetailDTO> findAll(Pageable pageable) {
        System.out.println("BoardServiceImpl.findAll");
        System.out.println("pageable = " + pageable);
        Page<BoardEntity> pageEntityList = br.findAll(pageable);
        System.out.println("pageEntityList.getContent() = " + pageEntityList.getContent());
        Page<BoardDetailDTO> boardList = pageEntityList.map(
                board -> new BoardDetailDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardContents(),
                        board.getBoardHits(),
                        board.getBoardFilename(),
                        board.getCreateTime())
        );
        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청한 페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글 갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청 페이지 (jpa 기준)
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보이는 글 갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 처음 페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막 페이지 인지


        return boardList;
    }

    @Override
    public BoardDetailDTO findById(Long boardId) {
        br.hits(boardId);
        return BoardDetailDTO.toDetailDTO(br.findById(boardId).get());
    }

    @Override
    public void update(BoardDetailDTO boardDetailDTO) throws IOException {
        MemberEntity memberEntity = mr.findByMemberEmail(boardDetailDTO.getBoardWriter()).get();
        BoardEntity boardEntity = br.findById(boardDetailDTO.getId()).get();
        if (boardDetailDTO.getBoardFile() != null) {
            MultipartFile file = boardDetailDTO.getBoardFile();
            String filename = file.getOriginalFilename();
            filename = System.currentTimeMillis() + filename;

            File deleteFile = new File("C:\\development\\source\\SpringBoot\\MemberBoard\\src\\main\\resources\\static\\upload\\" + boardEntity.getBoardFilename());

            String savePath = "C:\\development\\source\\SpringBoot\\MemberBoard\\src\\main\\resources\\static\\upload\\" + filename;

            if (!file.isEmpty()) {
                file.transferTo(new File(savePath));
            }
            if (deleteFile.exists()) {
                deleteFile.delete();
                System.out.println("파일을 삭제합니다.");
            } else {
                System.out.println("파일이 존재하지 않습니다.");
            }

            boardDetailDTO.setBoardFilename(filename);
        } else {
            boardDetailDTO.setBoardFilename(boardEntity.getBoardFilename());
        }
        br.save(BoardEntity.toDetailBoard(boardDetailDTO, memberEntity));
    }

    @Override
    public Page<BoardDetailDTO> searchList(Pageable pageable, int searchType, String keyword) {
        System.out.println("BoardServiceImpl.searchList");
        Page<BoardEntity> Containing = null;
        System.out.println("pageable = " + pageable + ", searchType = " + searchType + ", keyword = " + keyword);

        if (searchType == 1) {
            System.out.println("t");
            Containing = br.findByBoardTitleContaining(keyword, pageable);

        } else if (searchType == 2) {
            System.out.println("w");
            Containing = br.findByBoardWriterContaining(keyword, pageable);
        } else {
            System.out.println("c");
            Containing = br.findByBoardContentsContaining(keyword, pageable);
        }
        Page<BoardDetailDTO> detailDTOS = Containing.map(
                board -> new BoardDetailDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardContents(),
                        board.getBoardHits(),
                        board.getBoardFilename(),
                        board.getCreateTime())
        );
        System.out.println("boardList.getContent() = " + detailDTOS.getContent()); // 요청한 페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + detailDTOS.getTotalElements()); // 전체 글 갯수
        System.out.println("boardList.getNumber() = " + detailDTOS.getNumber()); // 요청 페이지 (jpa 기준)
        System.out.println("boardList.getTotalPages() = " + detailDTOS.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + detailDTOS.getSize()); // 한페이지에 보이는 글 갯수
        System.out.println("boardList.hasPrevious() = " + detailDTOS.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardList.isFirst() = " + detailDTOS.isFirst()); // 처음 페이지인지 여부
        System.out.println("boardList.isLast() = " + detailDTOS.isLast()); // 마지막 페이지 인지
        return detailDTOS;
    }

    @Override
    public void delete(Long boardId) {
        br.deleteById(boardId);
    }
}
