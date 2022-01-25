package com.sjw.memberboard.controller;

import com.sjw.memberboard.dto.BoardDetailDTO;
import com.sjw.memberboard.dto.BoardSaveDTO;
import com.sjw.memberboard.dto.CommentDetailDTO;
import com.sjw.memberboard.dto.CommentSaveDTO;
import com.sjw.memberboard.service.BoardService;
import com.sjw.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService bs;
    private final CommentService cs;
    private final int BLOCK_LIMIT=3;

    @GetMapping("/save")
    public String saveForm(Model model){
        model.addAttribute("board",new BoardSaveDTO());
        return "/board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("board") BoardSaveDTO boardSaveDTO) throws IOException {
        bs.save(boardSaveDTO);

        return "redirect:/board";
    }


    @GetMapping
    public String findAll(@PageableDefault(page = 0,size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                          Model model){
        System.out.println("BoardController.findAll");
        System.out.println("pageable = " + pageable);
        Page<BoardDetailDTO> pageList = bs.findAll(pageable);
        int startPage =(((int)(Math.ceil((double) (pageable.getPageNumber()+1)/BLOCK_LIMIT)))-1)
                *BLOCK_LIMIT+1;
        int endPage = ((startPage+BLOCK_LIMIT-1)<(pageList.getTotalPages()+1)) ?
                startPage+BLOCK_LIMIT-1 :pageList.getTotalPages();
        model.addAttribute("boardList",pageList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("pageLimit",pageable.getPageSize());
        return "/board/findAll";
    }

    @GetMapping("/{boardId}")
    public String findById(@PathVariable("boardId") Long boardId,Model model){
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        List<CommentDetailDTO> commentDetailDTOList = cs.findById(boardId);
        model.addAttribute("board",boardDetailDTO);
        model.addAttribute("comment",commentDetailDTOList);
        model.addAttribute("commentSave",new CommentSaveDTO());
        return "/board/findById";
    }

    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable("boardId") Long boardId, Model model){
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        model.addAttribute("board",boardDetailDTO);
        return "/board/update";
    }

    @PostMapping
    public String update(@ModelAttribute("board") BoardDetailDTO boardDetailDTO) throws IOException {
        bs.update(boardDetailDTO);

        return "redirect:/board/"+boardDetailDTO.getId();
    }

    @PutMapping
    @ResponseBody
    public String updateAjax(@RequestParam(value = "boardFile", required = false) MultipartFile boardFile,
                             MultipartHttpServletRequest multipartHttpServletRequest,
                             @RequestParam("id") Long boardId,
                             @RequestParam("boardTitle") String boardTitle,
                             @RequestParam("boardWriter") String boardWriter,
                             @RequestParam("boardContents") String boardContents
                             ) throws IOException {
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        boardDetailDTO.setId(boardId);
        boardDetailDTO.setBoardTitle(boardTitle);
        boardDetailDTO.setBoardWriter(boardWriter);
        boardDetailDTO.setBoardContents(boardContents);
        boardDetailDTO.setBoardFile(boardFile);
        System.out.println("boardDetailDTO = " + boardDetailDTO);
        bs.update(boardDetailDTO);

        return "/board/"+boardDetailDTO.getId();
    }

    @GetMapping("/search")
    public String search(@PageableDefault(page = 0,size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam("searchType") int searchType,
                          @RequestParam("keyword") String keyword,
                          Model model){
        System.out.println("BoardController.search");
        System.out.println("pageable = " + pageable);
        Page<BoardDetailDTO> pageList = bs.searchList(pageable,searchType,keyword);
        int startPage =(((int)(Math.ceil((double) (pageable.getPageNumber()+1)/BLOCK_LIMIT)))-1)
                *BLOCK_LIMIT+1;
        int endPage = ((startPage+BLOCK_LIMIT-1)<(pageList.getTotalPages()+1)) ?
                startPage+BLOCK_LIMIT-1 :pageList.getTotalPages();
        model.addAttribute("boardList",pageList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("pageLimit",pageable.getPageSize());
        model.addAttribute("searchType",searchType);
        model.addAttribute("keyword",keyword);
        return "/board/search";
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity delete(@RequestParam("boardId") Long boardId){
        bs.delete(boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
