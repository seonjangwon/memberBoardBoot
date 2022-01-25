package com.sjw.memberboard.entity;

import com.sjw.memberboard.dto.BoardDetailDTO;
import com.sjw.memberboard.dto.BoardSaveDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "board_table")
    private Long id;

    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private int boardHits;
    private String boardFilename;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CommentEntity> commentList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


    public static BoardEntity toSaveBoard(BoardSaveDTO boardSaveDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(memberEntity.getMemberEmail());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
        boardEntity.setBoardFilename(boardSaveDTO.getBoardFilename());
        boardEntity.setBoardHits(0);
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }
    public static BoardEntity toDetailBoard(BoardDetailDTO boardDetailDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDetailDTO.getId());
        boardEntity.setBoardWriter(memberEntity.getMemberEmail());
        boardEntity.setBoardTitle(boardDetailDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDetailDTO.getBoardContents());
        boardEntity.setBoardFilename(boardDetailDTO.getBoardFilename());
        //boardEntity.setBoardHits(0);
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }
}
