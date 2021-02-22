package com.appgate.ipgeolocation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
	@JsonProperty("Country_code")
	private String countryCode;

	@JsonProperty("Region")
	private String region;

	@JsonProperty("City")
	private String city;

	@JsonProperty("Timezone")
	private String timezone;
}
