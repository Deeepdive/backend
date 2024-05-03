//package deepdive.backend.csv;
//
//import deepdive.backend.dto.diveshop.DiveShopCsvData;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class SimpleJobConfiguration {
//
//	private final CsvReader csvReader;
//	private final CsvScheduleWriter csvScheduleWriter;
////	private final CsvFileDeleteListener csvFileDeleteListener;
//
//	@Bean
//	public Job diveShopDataLoadJob(JobRepository jobRepository, Step diveShopDataLoadStep) {
//		return new JobBuilder("simpleJob", jobRepository)
//			.start(diveShopDataLoadStep)
//			.build();
//	}
//
//	@Bean
//	public Step diveShopDataLoadStep(
//		JobRepository jobRepository,
//		PlatformTransactionManager platformTransactionManager) {
//		return new StepBuilder("diveShopDataLoadStep", jobRepository)
//			.<DiveShopCsvData, DiveShopCsvData>chunk(50, platformTransactionManager)
//			.reader(csvReader.csvScheduleReader())
//			.writer(csvScheduleWriter)
////			.listener(csvFileDeleteListener)
//			.build();
//	}
//
//}
