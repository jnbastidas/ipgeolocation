package com.appgate.ipgeolocation.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.appgate.ipgeolocation.model.IpRange;
import com.appgate.ipgeolocation.model.Localization;
import com.appgate.ipgeolocation.repository.IpRangeRepository;


@SpringBootTest
public class LocalizationServiceTest {

	@Autowired
	private LocalizationService localizationService;

	@MockBean
	private IpRangeRepository ipRangeRepository;

	@Test
	public void getLocationsByIp_nullIp() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.localizationService.getLocationsByIp(null);
		});
	}

	@Test
	public void getLocationsByIp_badIp() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.localizationService.getLocationsByIp("10_10_10_10");
		});
	}

	@Test
	public void getLocationsByIp_ok() {
		final Localization localization = new Localization();
		final IpRange ipRange = new IpRange();
		ipRange.setLocalization(localization);
		final Set<IpRange> ipRangeSet = new HashSet<>();
		ipRangeSet.add(ipRange);

		Mockito.when(this.ipRangeRepository.findContainIp(Mockito.anyLong())).thenReturn(ipRangeSet);

		final Set<Localization> rdo = this.localizationService.getLocationsByIp("10.10.10.10");

		assertEquals(1, rdo.size());
	}
}
