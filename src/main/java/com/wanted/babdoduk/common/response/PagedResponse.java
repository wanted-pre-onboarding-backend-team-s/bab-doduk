package com.wanted.babdoduk.common.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class PagedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalCount;
    private int start;
    private int end;
    private boolean prev;
    private boolean next;

    public static <T> PagedResponse<T> of(Page<T> pageInfo) {
        Pageable pageable = pageInfo.getPageable();
        int totalPage = pageInfo.getTotalPages();

        int page = pageable.getPageNumber() + 1;
        int size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / (double) size)) * size;

        int start = tempEnd - (size - 1);
        int end = Math.min(totalPage, tempEnd);
        boolean prev = start > 1;
        boolean next = totalPage > tempEnd;

        return PagedResponse.<T>builder()
                              .content(pageInfo.getContent())
                              .page(page)
                              .size(size)
                              .totalCount(pageInfo.getTotalElements())
                              .start(start)
                              .end(end)
                              .prev(prev)
                              .next(next)
                              .build();
    }

}
