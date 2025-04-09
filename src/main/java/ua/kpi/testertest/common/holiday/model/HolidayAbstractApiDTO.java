package ua.kpi.testertest.common.holiday.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class HolidayAbstractApiDTO {
    String name;

    @JsonProperty("name_local")
    String nameLocal;

    String language;
    String description;
    String country;
    String location;
    String type;
    String date;

    @JsonProperty("date_year")
    String dateYear;

    @JsonProperty("date_month")
    String dateMonth;

    @JsonProperty("date_day")
    String dateDay;

    @JsonProperty("week_day")
    String weekDay;
}
