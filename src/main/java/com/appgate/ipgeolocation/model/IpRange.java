package com.appgate.ipgeolocation.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IP_RANGE")
public class IpRange implements Serializable {
	public static final String LOCALIZATION_FIELD = "localization";

	@Id
	@GeneratedValue
	private Long id;
	private Long ipFrom;
	private Long ipTo;

	@ManyToOne(
		fetch = FetchType.LAZY, cascade = CascadeType.ALL
	)
	@JoinColumn(name = LOCALIZATION_FIELD + "_id")
	private Localization localization;
}
