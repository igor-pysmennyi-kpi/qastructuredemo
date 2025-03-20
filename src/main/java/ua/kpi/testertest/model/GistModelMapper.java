package ua.kpi.testertest.model;

import org.springframework.stereotype.Service;
import ua.kpi.testertest.model.dto.GistDTO;
import ua.kpi.testertest.model.entity.Gist;
import ua.kpi.testertest.model.entity.Type;

@Service
public class GistModelMapper {
    public Gist fromDto(GistDTO source) {
        Gist gist = new Gist();
        gist.setId(source.getUuid());
        gist.setAuthor(source.getAuthor());
        gist.setType(Type.valueOf(source.getType().name()));//todo:attention - bug here
        gist.setContent(source.getContent());
        gist.setValidUntil(source.getValidUntil());
        //are there any more bugs?
        return gist;
    }

    public GistDTO toDto(Gist source) {
        return GistDTO.builder()
                .uuid(source.getId())
                .author(source.getAuthor())
                .type(GistDTO.Type.valueOf(source.getType().name()))
                .content(source.getContent())
                .validUntil(source.getValidUntil())
                .build();
    }
}
