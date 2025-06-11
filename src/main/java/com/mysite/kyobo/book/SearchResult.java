package com.mysite.kyobo.book;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResult {
    private List<BookDto> results;
    private int totalPages;
}