package com.appgate.ipgeolocation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ipgeolocalization")
public class IpGeolocalizationController {

	@Autowired
	private JobLauncher ipGeolocalizationsJobLauncher;

	@Autowired
	private Job importIpGeolocalizationsJob;

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
}