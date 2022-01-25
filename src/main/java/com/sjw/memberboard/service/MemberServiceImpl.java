package com.sjw.memberboard.service;

import com.sjw.memberboard.dto.MemberDetailDTO;
import com.sjw.memberboard.dto.MemberSaveDTO;
import com.sjw.memberboard.entity.MemberEntity;
import com.sjw.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository mr;
    private final HttpSession session;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) throws IOException {
            MultipartFile file = memberSaveDTO.getMemberFile();

            String filename = file.getOriginalFilename();

            filename = System.currentTimeMillis() + filename;

            String savePath = "C:\\development\\source\\SpringBoot\\MemberBoard\\src\\main\\resources\\static\\upload\\" + filename;

            if (!file.isEmpty()) {
                file.transferTo(new File(savePath));
            }

            memberSaveDTO.setMemberFilename(filename);

        return mr.save(new MemberEntity().toSaveMember(memberSaveDTO)).getId();

    }

    @Override
    public boolean emailCh(String memberEmail) {

        Optional<MemberEntity> memberEntity = mr.findByMemberEmail(memberEmail);

        if (memberEntity.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void login(MemberDetailDTO memberDetailDTO) {
        Optional<MemberEntity> memberEntity = mr.findByMemberEmail(memberDetailDTO.getMemberEmail());

        if (memberEntity.isPresent()) { // 있으면
            if (!memberEntity.get().getMemberPassword().equals(memberDetailDTO.getMemberPassword())) {
                throw new IllegalStateException("이메일 또는 비밀번호가 다릅니다");
            } else {
                session.setAttribute("loginEmail", memberDetailDTO.getMemberEmail());
            }
        } else { // 없으면
            throw new IllegalStateException("이메일 또는 비밀번호가 다릅니다");
        }
    }

    @Override
    public MemberDetailDTO findByMemberEmail(String memberEmail) {
        return MemberDetailDTO.toDetailMember(mr.findByMemberEmail(memberEmail).get());
    }

    @Override
    public void update(MemberDetailDTO memberDetailDTO) throws IOException {
        MemberEntity memberEntity = mr.findById(memberDetailDTO.getId()).get();

        if (memberEntity.getMemberPassword().equals(memberDetailDTO.getMemberPassword())) {
            if (memberDetailDTO.getMemberFile() != null) {

                MultipartFile file = memberDetailDTO.getMemberFile();

                File deleteFile = new File("C:\\development\\source\\SpringBoot\\MemberBoard\\src\\main\\resources\\static\\upload\\" + memberEntity.getMemberFilename());

                String filename = file.getOriginalFilename();

                filename = System.currentTimeMillis() + filename;

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
                memberDetailDTO.setMemberFilename(filename);
            } else {
                memberDetailDTO.setMemberFilename(memberEntity.getMemberFilename());
            }
            mr.save(MemberEntity.toDetailMember(memberDetailDTO));
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다");
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberDetailDTO> memberList = new ArrayList<>();
        List<MemberEntity> memberEntityList = mr.findAll();
        for (MemberEntity m : memberEntityList) {
            memberList.add(MemberDetailDTO.toDetailMember(m));
        }
        return memberList;
    }

    @Override
    public void delete(Long memberId) {
        mr.deleteById(memberId);
    }
}
