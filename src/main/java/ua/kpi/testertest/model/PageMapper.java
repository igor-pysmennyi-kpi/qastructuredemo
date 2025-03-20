package ua.kpi.testertest.model;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ua.kpi.testertest.model.dto.PagedResource;

@Service
public class PageMapper {
    public  <T> PagedResource<T> toPagedResource(Page<T> source) {
        return PagedResource
                .<T>builder()
                .content(source.getContent())
                .numberOfElements(source.getNumberOfElements())
                .size(source.getSize())
                .totalElements(source.getTotalElements())
                .totalPages(source.getTotalPages())
                .number(source.getNumber())
                .build();
    }
}