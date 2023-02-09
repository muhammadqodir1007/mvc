package com.epam.esm.pagination;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Page {

    private int currentPageNumber;
    private int lastPageNumber;
    private int pageSize;
    private long totalRecords;




    @Override
    public String toString() {
        return "Page{" +
                "currentPageNumber=" + currentPageNumber +
                ", lastPageNumber=" + lastPageNumber +
                ", pageSize=" + pageSize +
                ", totalRecords=" + totalRecords +
                '}';
    }
}
