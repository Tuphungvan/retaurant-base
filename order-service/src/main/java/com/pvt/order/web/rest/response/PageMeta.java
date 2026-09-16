package com.pvt.order.web.rest.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PageMeta {
    private int page;
    private int totalPages;
    private long totalElements;
    private int size;
}
