package com.epam.esm.pagination;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResult<E> extends RepresentationModel<PaginationResult<E>> {

    private Page page;

    private List<E> records;


    @Override
    public String toString() {
        return "PaginationResult{" +
                "page=" + page +
                ", records=" + records +
                '}';
    }
}

