package ua.kpi.testertest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.testertest.common.holiday.restclient.HolidayRestClient;
import ua.kpi.testertest.model.dto.GistDTO;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class HolidayDtoEnricher implements GistDtoEnricher {
    private final HolidayRestClient holidayRestClient;

    @Override
    public GistDTO enrich(GistDTO gistDto) {
        if(isNull(gistDto.getValidFrom())) {
            return gistDto;
        }

        return gistDto.toBuilder()
                .createdOnHoliday(holidayRestClient.getHolidays(gistDto.getValidFrom().toLocalDate()).isEmpty())
                .build();
    }
}
