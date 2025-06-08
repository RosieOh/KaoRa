package com.kaora.domain.board.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    @NotEmpty
    @Size(max = 500, message = "Title cannot exceed 500 characters")
    private String title;

    @NotEmpty
    @Size(max = 2000, message = "Content cannot exceed 2000 characters")
    private String content;

    @NotEmpty
    @Size(max = 50, message = "Board type cannot exceed 50 characters")
    private String boardType;

    private int flag;

    @NotEmpty
    @Size(max = 50, message = "Writer cannot exceed 50 characters")
    private String writer;

    private Long fileId; // Changed from @NotNull to allow null, matching Board entity

    private boolean pinned;

    private boolean privated;

    private boolean deleteType;

    private LocalDateTime regDate; // From BaseEntity
    private LocalDateTime modDate; // From BaseEntity

    private List<String> fileNames; // For multiple file names
}