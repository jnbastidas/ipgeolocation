package com.appgate.ipgeolocation.processor;

import org.springframework.batch.item.ItemProcessor;

import com.appgate.ipgeolocation.model.IpGeolocalizationCSVRow;
import com.appgate.ipgeolocation.model.IpRange;
import com.appgate.ipgeolocation.model.Localization;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class IpGeolocalizationProcessor implements ItemProcessor<IpGeolocalizationCSVRow, IpRange> {

	@Override
	public IpRange process(final IpGeolocalizationCSVRow item) throws Exception {
		log.info("Process CSV row: ipFrom {} - ipTo {}", item.getIpFrom(), item.getIpTo());

		final Localization localization = Localization.builder()
				.countryCode(item.getCountryCode()).countryName(item.getCountryName())
				.city(item.getCity()).region(item.getRegion())
				.latitude(item.getLatitude()).longitude(item.getLongitude())
				.timeZone(item.getTimeZone()).build();

		return IpRange.builder().ipFrom(Long.valueOf(item.getIpFrom()))
				.ipTo(Long.valueOf(item.getIpTo()))
				.localization(localization).build();
	}
}
