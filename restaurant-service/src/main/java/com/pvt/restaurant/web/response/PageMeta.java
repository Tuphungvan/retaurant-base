package com.pvt.restaurant.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageMeta {
    private int page;
    private int totalPages;
    private int size;
    private long totalElements;
}
