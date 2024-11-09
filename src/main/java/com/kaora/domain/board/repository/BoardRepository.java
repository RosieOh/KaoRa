package com.kaora.domain.board.repository;

import com.kaora.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // id로 게시뭎 조회
    @Query("SELECT b FROM Board b WHERE b.id = :id")
    Optional<Board> findById(@Param("id") Long id);

    @Query("select b from Board b where b.boardType = :boardType")
    List<Board> findByBoardType(@Param("boardType") String boardType);

    @Query("select b from Board b where b.writer = :writer")
    Optional<Board> findByName(@Param("writer") String writer);

    Page<Board> findByBoardType(String boardType, Pageable pageable);
}
