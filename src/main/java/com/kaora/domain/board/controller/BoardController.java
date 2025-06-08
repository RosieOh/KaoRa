package com.kaora.domain.board.controller;

import com.kaora.domain.board.dto.BoardDTO;
import com.kaora.domain.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<?> createBoard(@Valid @RequestBody BoardDTO boardDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors in createBoard: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            boardService.register(boardDTO);
            log.info("Created board with title: {}", boardDTO.getTitle());
            return ResponseEntity.status(HttpStatus.CREATED).body("Board created successfully");
        } catch (Exception e) {
            log.error("Error creating board: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id) {
        try {
            BoardDTO boardDTO = boardService.getBoard(id);
            log.info("Retrieved board with id: {}", id);
            return ResponseEntity.ok(boardDTO);
        } catch (RuntimeException e) {
            log.warn("Board with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> boards = boardService.findAll(new BoardDTO());
        log.info("Retrieved {} boards", boards.size());
        return ResponseEntity.ok(boards);
    }

    // 게시글 타입별 조회 (페이징 포함)
    @GetMapping("/type/{boardType}")
    public ResponseEntity<Page<BoardDTO>> getBoardsByType(
            @PathVariable String boardType,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardDTO> boards = boardService.findByBoardTypeWithPaging(boardType, pageable);
        log.info("Retrieved {} boards of type {}", boards.getTotalElements(), boardType);
        return ResponseEntity.ok(boards);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(
            @PathVariable Long id,
            @Valid @RequestBody BoardDTO boardDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors in updateBoard: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            boardDTO.setId(id); // Ensure ID matches path variable
            boardService.modify(boardDTO);
            log.info("Updated board with id: {}", id);
            return ResponseEntity.ok("Board updated successfully");
        } catch (RuntimeException e) {
            log.warn("Board with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 게시글 삭제 (논리적 삭제)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        try {
            boardService.remove(id);
            log.info("Deleted board with id: {}", id);
            return ResponseEntity.ok("Board deleted successfully");
        } catch (RuntimeException e) {
            log.warn("Board with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 초기 공지사항 생성 (예: 서버 시작 시 호출)
    @PostMapping("/initialize")
    public ResponseEntity<?> initializeBoards() {
        try {
            boardService.createAndSaveBoards();
            log.info("Initialized default boards");
            return ResponseEntity.status(HttpStatus.CREATED).body("Default boards created successfully");
        } catch (Exception e) {
            log.error("Error initializing boards: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}