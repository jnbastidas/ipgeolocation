package com.appgate.ipgeolocation.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.appgate.ipgeolocation.listener.IpGeolocalizationListener;
import com.appgate.ipgeolocation.model.IpGeolocalizationCSVRow;
import com.appgate.ipgeolocation.model.IpRange;
import com.appgate.ipgeolocation.processor.IpGeolocalizationProcessor;
import com.appgate.ipgeolocation.writer.IpGeolocalizationWriter;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@EnableBatchProcessing
public class IpGoeBatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobRepository jobRepository;

	@Bean
	public FlatFileItemReader<IpGeolocalizationCSVRow> reader() {
		return new FlatFileItemReaderBuilder<IpGeolocalizationCSVRow>()
				.name("ipGeolocalizationCSVRowReader")
				.resource(new ClassPathResource("ipgeo.csv"))
				.delimited()
				.names(new String[] { "ipFrom", "ipTo", "countryCode", "countryName",
						"region", "city", "latitude", "longitude", "timeZone"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
					setTargetType(IpGeolocalizationCSVRow.class);
				}})
				.build();
	}

	@Bean
	public IpGeolocalizationProcessor processor() {
		return new IpGeolocalizationProcessor();
	}

	@Bean
	public IpGeolocalizationWriter writer() {
		return new IpGeolocalizationWriter();
	}

	@Bean
	public Step stepOne() {
		return this.stepBuilderFactory.get("stepOne")
				.<IpGeolocalizationCSVRow, IpRange> chunk(10000) // process each 10 rows
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	@Bean
	public Job importIpGeolocalizationsJob(final IpGeolocalizationListener listener, final Step stepOne) {
		return this.jobBuilderFactory.get("importIpGeolocalizationsJob")
				.listener(listener)
				.flow(stepOne)
				.end()
				.build();
	}

	@Bean
	public JobLauncher ipGeolocalizationsJobLauncher() throws Exception {
		final SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(this.jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
}
