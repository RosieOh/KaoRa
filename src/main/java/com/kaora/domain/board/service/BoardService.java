package com.kaora.domain.board.service;

import com.kaora.domain.board.dto.BoardDTO;
import com.kaora.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    public BoardDTO findById(Long id);

    public List<BoardDTO> findAll(BoardDTO bd);

    public List<Board> list();

    public void register(BoardDTO bd);

    public void modify(BoardDTO bd);

    public void remove(Long id);

    public BoardDTO getBoard(Long id);

    public List<BoardDTO> findByBoardType(String boardType);

    void createAndSaveBoards();

    Page<BoardDTO> findByBoardTypeWithPaging(String boardType, Pageable pageable);
}
