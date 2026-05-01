package com.helix.api.dto.general.mapper;

import com.helix.zibrary.data.enumeration.EnumTimezone;

import java.util.ArrayList;
import java.util.List;

public class GeneralMapper {

    public static List<String> getAvailableTimezone(){
        List<String> availableTimezone = new ArrayList<>();
        availableTimezone.add(EnumTimezone.WIB.getShortTimezone());
        availableTimezone.add(EnumTimezone.WITA.getShortTimezone());
        availableTimezone.add(EnumTimezone.WIT.getShortTimezone());

        return availableTimezone;
    }
}
