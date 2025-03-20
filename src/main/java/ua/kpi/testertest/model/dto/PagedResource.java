package ua.kpi.testertest.model.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PagedResource<T> {

    List<T> content;

    int numberOfElements;
    long totalElements;
    int size;
    int totalPages;
    int number;
}