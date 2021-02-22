package com.appgate.ipgeolocation.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appgate.ipgeolocation.model.IpRange;
import com.appgate.ipgeolocation.model.Localization;
import com.appgate.ipgeolocation.repository.IpRangeRepository;
import com.appgate.ipgeolocation.service.LocalizationService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class LocalizationServiceImp implements LocalizationService {

	private static final String IP_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

	private static final Integer FACTOR_SEG1 = 16777216;
	private static final Integer FACTOR_SEG2 = 65536;
	private static final Integer FACTOR_SEG3 = 256;

	@Autowired
	private IpRangeRepository ipRangeRepository;

	@Override
	public Set<Localization> getLocationsByIp(final String ip) {
		if (ip == null) {
			throw new IllegalArgumentException("Ip can no be null");
		}

		if (!ip.matches(IP_REGEX)) {
			throw new IllegalArgumentException("The ip doesn't have a correct format");
		}

		final String[] ipSegments = ip.split("\\.");
		final Long decimalIp = Long.parseLong(ipSegments[0])*FACTOR_SEG1
				+ Long.parseLong(ipSegments[1])*FACTOR_SEG2
				+ Long.parseLong(ipSegments[2])*FACTOR_SEG3
				+ Long.parseLong(ipSegments[3]);

		final Set<IpRange> ipRanges = this.ipRangeRepository.findContainIp(decimalIp);

		final Set<Localization> localizations = new HashSet<>();
		ipRanges.forEach(ir -> {
			localizations.add(ir.getLocalization());
		});

		return localizations;
	}
}