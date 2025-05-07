package com.hasher.journal.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WhetherResponse {

    private Current current;

    @Getter
    @Setter
    public class Current{
        public String observation_time;
        public int temperature;
        public int weather_code;
        public List<String> weather_icons;
        public int wind_speed;
        public int wind_degree;
        public String wind_dir;
        public int pressure;
        public int precip;
        public int humidity;
        public int cloudcover;
        public int feelslike;
        public int uv_index;
        public int visibility;
        public String is_day;
    }


}