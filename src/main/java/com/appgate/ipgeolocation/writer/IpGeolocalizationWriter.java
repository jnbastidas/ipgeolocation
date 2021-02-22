package com.appgate.ipgeolocation.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appgate.ipgeolocation.model.IpRange;
import com.appgate.ipgeolocation.repository.IpRangeRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class IpGeolocalizationWriter implements ItemWriter<IpRange> {

	@Autowired
	private IpRangeRepository ipRangeRepository;

	@Override
	public void write(final List<? extends IpRange> items) throws Exception {
		log.info("Saving the ip range and localizations into the data base, total rows: {}", items.size());
		items.forEach(i -> {
			this.ipRangeRepository.save(i);
		});
	}
}
