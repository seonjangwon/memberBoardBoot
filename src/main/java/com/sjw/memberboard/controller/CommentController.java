package com.sjw.memberboard.controller;

import com.sjw.memberboard.dto.CommentDetailDTO;
import com.sjw.memberboard.dto.CommentSaveDTO;
import com.sjw.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService cs;


    @PostMapping
    @ResponseBody
    public List<CommentDetailDTO> save(@ModelAttribute CommentSaveDTO commentSaveDTO, Model model){
        System.out.println("commentSaveDTO = " + commentSaveDTO );
        cs.save(commentSaveDTO);
        List<CommentDetailDTO> commentDetailDTOList = cs.findById(commentSaveDTO.getBoardId());
        System.out.println("commentDetailDTOList = " + commentDetailDTOList);
        return commentDetailDTOList;
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity delete(@RequestParam("commentId") Long commentId) {
        cs.delete(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
