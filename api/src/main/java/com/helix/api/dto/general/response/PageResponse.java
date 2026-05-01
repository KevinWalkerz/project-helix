package com.helix.api.dto.general.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }

    public PageResponse(List<T> content, Page<?> pageMeta) {
        this.content = content;
        this.pageNumber = pageMeta.getNumber();
        this.pageSize = pageMeta.getSize();
        this.totalElements = pageMeta.getTotalElements();
        this.totalPages = pageMeta.getTotalPages();
        this.last = pageMeta.isLast();
    }
}