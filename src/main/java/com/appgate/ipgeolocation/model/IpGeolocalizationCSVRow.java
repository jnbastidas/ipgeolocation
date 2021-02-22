package com.appgate.ipgeolocation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpGeolocalizationCSVRow {
	private String ipFrom;
	private String ipTo;
	private String countryCode;
	private String countryName;
	private String region;
	private String city;
	private String latitude;
	private String longitude;
	private String timeZone;
}
