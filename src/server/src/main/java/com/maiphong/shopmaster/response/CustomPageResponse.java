package com.maiphong.shopmaster.response;

import java.util.Collection;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageResponse<T> {
    private Collection<T> data;

    private Links links;

    private PagedModel.PageMetadata page;
}
