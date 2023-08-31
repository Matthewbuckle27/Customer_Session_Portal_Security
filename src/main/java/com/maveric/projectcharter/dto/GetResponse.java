package com.maveric.projectcharter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maveric.projectcharter.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetResponse<T> {

    @JsonProperty(Constants.PROPERTY_ELEMENTS)
    private long totalElements;

    @JsonProperty(Constants.PROPERTY_PAGES)
    private int totalPages;

    @JsonProperty(Constants.PROPERTY_SESSION)
    private List<T> session;
}


