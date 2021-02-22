package com.appgate.ipgeolocation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.appgate.ipgeolocation.dto.LocationDTO;
import com.appgate.ipgeolocation.model.Localization;
import com.appgate.ipgeolocation.service.LocalizationService;

@SpringBootTest
public class IpGeolocalizationControllerTest {

	@Autowired
	private IpGeolocalizationController ipGeolocalizationController;

	@MockBean
	private LocalizationService localizationService;

	@Test
	void getLocalizationsEmpty() {
		Mockito.when(this.localizationService.getLocationsByIp(Mockito.anyString())).thenReturn(Collections.emptySet());
		final ResponseEntity<?> responseEntity = this.ipGeolocalizationController.getLocalizations("127.0.0.1");

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("There isn't information for the given IP.", responseEntity.getBody());
	}

	@Test
	void getLocalizationsBadIp() {
		Mockito.when(this.localizationService.getLocationsByIp(Mockito.anyString())).thenThrow(new IllegalArgumentException("Desc error"));
		final ResponseEntity<?> responseEntity = this.ipGeolocalizationController.getLocalizations("anyValue");

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Desc error", responseEntity.getBody());
	}

	@Test
	void getLocalizationsOk() {
		final Set<Localization> localizationSet = new HashSet<>();
		final Localization localization = Localization.builder()
				.countryCode("COL")
				.city("Medellin")
				.region("Antioqia")
				.timeZone("Bogota-Lima").build();
		localizationSet.add(localization);


		Mockito.when(this.localizationService.getLocationsByIp(Mockito.anyString())).thenReturn(localizationSet);
		final ResponseEntity<?> responseEntity = this.ipGeolocalizationController.getLocalizations("127.0.0.1");

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, ((Set<LocationDTO>) responseEntity.getBody()).size());
	}
}
