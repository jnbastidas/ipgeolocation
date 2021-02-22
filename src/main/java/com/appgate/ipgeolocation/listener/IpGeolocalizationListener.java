package com.appgate.ipgeolocation.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class IpGeolocalizationListener extends JobExecutionListenerSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void afterJob(final JobExecution jobExecution) {
		if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
			log.info("IpGeolocalizationJob with id {} has finished", jobExecution.getId());
		}
	}
}
