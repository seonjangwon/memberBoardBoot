package com.sjw.memberboard.controller;

import com.sjw.memberboard.dto.MemberDetailDTO;
import com.sjw.memberboard.dto.MemberSaveDTO;
import com.sjw.memberboard.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/*")
public class MemberController {
    private final MemberService ms;

    @GetMapping("save")
    public String saveForm(Model model){
        model.addAttribute("member",new MemberSaveDTO());
        return "/member/save";
    }

    @PostMapping("save")
    public String save(@Validated @ModelAttribute("member") MemberSaveDTO memberSaveDTO, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "/member/save";
        }

        ms.save(memberSaveDTO);


        return "index";
    }

    @PostMapping("emailch")
    @ResponseBody
    public String emailCh(@RequestParam("memberEmail") String memberEmail){

        if (ms.emailCh(memberEmail)) {
            return "ok";
        } else {
            return "no";
        }
    }

    @GetMapping("login")
    public String loginForm(@RequestParam(value = "URL", defaultValue = "/") String URL, Model model){
        model.addAttribute("member",new MemberDetailDTO());
        model.addAttribute("URL",URL);
        return "/member/login";
    }

    @PostMapping("login")
    public String login(@Validated @ModelAttribute("member") MemberDetailDTO memberDetailDTO, BindingResult bindingResult) {
        try {
            ms.login(memberDetailDTO);
        } catch (IllegalStateException e) {
            bindingResult.reject("loginFalse",e.getMessage());
            return "/member/login";
        }

        return "redirect:"+memberDetailDTO.getURL();
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

    @GetMapping("{memberEmail}")
    public String mypage(@PathVariable("memberEmail") String memberEmail,Model model){
        MemberDetailDTO memberDetailDTO = ms.findByMemberEmail(memberEmail);
        model.addAttribute("member",memberDetailDTO);
        return "/member/mypage";
    }

    @GetMapping("update")
    public String updateForm(Model model,HttpSession session){
        MemberDetailDTO memberDetailDTO = ms.findByMemberEmail((String) (session.getAttribute("loginEmail")));
        model.addAttribute("member",memberDetailDTO);
        return "/member/update";
    }

    @PostMapping
    public String update(@Validated @ModelAttribute("member") MemberDetailDTO memberDetailDTO,
                         BindingResult bindingResult,Model model) throws IOException {
        try {
            ms.update(memberDetailDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("member",memberDetailDTO);
            bindingResult.reject("passwordErr",e.getMessage());
            return "/member/update";
        }
        return "/member/mypage";
    }

    @GetMapping("admin")
    public String admin(){

        return "/member/admin";
    }

    @GetMapping
    public String findAll(Model model){
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList",memberList);
        return "/member/findAll";
    }


    @GetMapping("delete/{memberId}")
    public String delete(@PathVariable("memberId") Long memberId){
        ms.delete(memberId);

        return "redirect:/member/";
    }

    @DeleteMapping("{memberId}")
    public ResponseEntity deleteAjax(@PathVariable("memberId") Long memberId) {
        ms.delete(memberId);
        return new ResponseEntity(HttpStatus.OK);
    }


}
