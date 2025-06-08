package com.kaora.domain.board.service;

import com.kaora.domain.board.dto.BoardDTO;
import com.kaora.domain.board.entity.Board;
import com.kaora.domain.board.repository.BoardRepository;
import com.kaora.global.constant.BoardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    // 게시판 추가 메소드
    public void createAndSaveBoards() {
        List<Board> board = new ArrayList<>();

        Board noticeBoard = new Board();
        noticeBoard.setTitle("공지사항");
        noticeBoard.setContent("내용");
        noticeBoard.setBoardType(BoardType.NOTICE.toString());
        board.add(noticeBoard);
        // 생성된 게시판들을 저장
        boardRepository.saveAll(board);
    }

    // 조회
    @Override
    public BoardDTO findById(Long id) {
        Optional<Board> result = boardRepository.findById(id);
        BoardDTO boardDTO = modelMapper.map(result.get(), BoardDTO.class);
        return boardDTO;
    }

    @Override
    public List<BoardDTO> findAll(BoardDTO boardDTO) {
        List<Board> boardList = new ArrayList<>();
        List<BoardDTO> boardDTOList = boardList.stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        return boardDTOList;
    }

    @Override
    public List<Board> list() {
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    @Override
    public void register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        board.setBoardType(board.getBoardType() != null ? board.getBoardType() : BoardType.NOTICE.toString());
        board.setFlag(0);
        board.setPinned(boardDTO.isPinned());
        board.setDeleteType(false);
        boardRepository.save(board);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getId());
        Board board = result.get();
        board.change(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.isPinned(), boardDTO.isPrivated());
        boardRepository.save(board);
    }

    // 게시글 삭제 -> 논리적 삭제
    @Override
    public void remove(Long id) {
        Optional<Board> result = boardRepository.findById(id);
        Board board = result.get();
        board.setDeleteType(true);
        boardRepository.save(board);
    }

    @Override
    public BoardDTO getBoard(Long id) {
        Optional<Board> result = boardRepository.findById(id);
        Board board = result.get();
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public List<BoardDTO> findByBoardType(String boardType) {
        List<Board> boardList = boardRepository.findByBoardType(boardType);
        List<BoardDTO> boardDTOList = boardList.stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        return boardDTOList;
    }

    public Page<BoardDTO> findByBoardTypeWithPaging(String boardType, Pageable pageable) {
        Page<Board> boards = boardRepository.findByBoardType(boardType, pageable);
        return boards.map(board -> modelMapper.map(board, BoardDTO.class));
    }
}
