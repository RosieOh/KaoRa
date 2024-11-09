package com.kaora.domain.board.service;

import com.kaora.domain.board.dto.BoardDTO;
import com.kaora.domain.board.entity.Board;
import com.kaora.domain.board.repository.BoardRepository;
import com.kaora.global.constant.BoardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final ModelMapper mm;
    private final BoardRepository br;

    // 게시판 추가 메소드
    public void createAndSaveBoards() {
        List<Board> board = new ArrayList<>();

        Board noticeBoard = new Board();
        noticeBoard.setTitle("공지사항");
        noticeBoard.setContent("내용");
        noticeBoard.setBoardType(BoardType.NOTICE.toString());
        board.add(noticeBoard);
        // 생성된 게시판들을 저장
        br.saveAll(board);
    }


    // 조회
    @Override
    public BoardDTO findById(Long id) {
        Optional<Board> result = br.findById(id);
        BoardDTO bd = mm.map(result, BoardDTO.class);
        return null;
    }

    @Override
    public List<BoardDTO> findAll(BoardDTO bd) {
        return null;
    }

    @Override
    public List<Board> list() {
        return null;
    }

    @Override
    public void register(BoardDTO bd) {

    }

    @Override
    public void modify(BoardDTO bd) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public BoardDTO getBoard(Long id) {
        return null;
    }

    @Override
    public List<BoardDTO> findByBoardType(String boardType) {
        return null;
    }
}
