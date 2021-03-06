package com.appgate.ipgeolocation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LOCALIZATION")
public class Localization implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	private String countryCode;
	private String countryName;
	private String region;
	private String city;
	private String latitude;
	private String longitude;
	private String timeZone;

	@OneToMany(
			mappedBy = IpRange.LOCALIZATION_FIELD,
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	@JsonIgnoreProperties(IpRange.LOCALIZATION_FIELD)
	private List<IpRange> ipRanges = new ArrayList<>();
}
