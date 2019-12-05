package ua.testtester.testertest.model.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Value
@Builder
public class PagedResource<T> {

    private List<T> content;

    private int numberOfElements;
    private long totalElements;
    private int size;
    private int totalPages;
    private int number;
}