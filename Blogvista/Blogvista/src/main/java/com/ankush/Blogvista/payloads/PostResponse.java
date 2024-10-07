package com.ankush.Blogvista.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

    List<PostDto> content;
    private int pageSize;
    private int pageNumber;
    private long  totalElements;
    private int totalPages;
    private Boolean isLastPage;



}
