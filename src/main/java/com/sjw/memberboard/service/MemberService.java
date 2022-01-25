package com.sjw.memberboard.service;

import com.sjw.memberboard.dto.MemberDetailDTO;
import com.sjw.memberboard.dto.MemberSaveDTO;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public interface MemberService {
    Long save(MemberSaveDTO memberSaveDTO) throws IOException;

    boolean emailCh(String memberEmail);

    void login(MemberDetailDTO memberDetailDTO);

    MemberDetailDTO findByMemberEmail(String memberEmail);

    void update(MemberDetailDTO memberDetailDTO) throws IOException;

    List<MemberDetailDTO> findAll();

    void delete(Long memberId);
}
