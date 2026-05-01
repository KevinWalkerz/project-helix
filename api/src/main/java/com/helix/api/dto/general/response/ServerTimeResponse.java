package com.helix.api.dto.general.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerTimeResponse {

    private String zoneID;
    private String serverDateTime;
    private ZonedDateTime serverDateTimeWithZone;
    private String indonesiaTimeZone;

}
