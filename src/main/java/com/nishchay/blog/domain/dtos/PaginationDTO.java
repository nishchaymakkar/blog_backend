package com.nishchay.blog.domain.dtos;

import lombok.Data;

@Data
public class PaginationDTO {
    private int page;        // The current page (starting from 0)
    private int size;

    public PaginationDTO(int page, int size) {
        this.page = page;
        this.size = size;
    }
}