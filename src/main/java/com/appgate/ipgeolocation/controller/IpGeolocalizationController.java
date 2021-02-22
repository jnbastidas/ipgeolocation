package com.appgate.ipgeolocation.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.appgate.ipgeolocation.dto.LocationDTO;
import com.appgate.ipgeolocation.model.Localization;
import com.appgate.ipgeolocation.service.LocalizationService;


@RestController
@RequestMapping("/ipgeolocalization")
public class IpGeolocalizationController {

	@Autowired
	private JobLauncher ipGeolocalizationsJobLauncher;

	@Autowired
	private Job importIpGeolocalizationsJob;

	@Autowired
	private LocalizationService localizationService;

	@PostMapping(value = "/process-file")
	public Map<String, Long> processFile() throws Exception {
		final JobExecution jobExecution = this.ipGeolocalizationsJobLauncher
				.run(this.importIpGeolocalizationsJob, new JobParametersBuilder()
						.addLong("startId", System.nanoTime())
						.toJobParameters());

		final Map<String, Long> rdo = new HashMap<>(1);
		rdo.put("idProcess", jobExecution.getId());
		return rdo;
	}

	@ResponseBody
	@GetMapping(value = "/locations/{ip}")
	public ResponseEntity<?> getLocalizations(@PathVariable final String ip) {
		try {
			final Set<Localization> rdo = this.localizationService.getLocationsByIp(ip);

			if (rdo == null || rdo.isEmpty()) {
				return ResponseEntity.ok("There isn't information for the given IP.");
			}

			return ResponseEntity.ok(
					rdo.stream().map(s -> LocationDTO.builder().countryCode(s.getCountryCode()).region(s.getRegion())
							.city(s.getCity()).timezone(s.getTimeZone()).build()).collect(Collectors.toSet())
			);
		} catch (final  IllegalArgumentException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}