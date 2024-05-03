package deepdive.backend.csv;

import deepdive.backend.dto.diveshop.DiveShopCsvData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsvReader {

	@Value("${file.input}")
	private String diveShopCsv;

	@Bean
	public FlatFileItemReader<DiveShopCsvData> csvScheduleReader() {
		// 파일 경로 지정 및 인코딩
		FlatFileItemReader<DiveShopCsvData> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new ClassPathResource(diveShopCsv));
		flatFileItemReader.setEncoding("UTF-8");
		flatFileItemReader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());

		// 읽어온 파일을 한 줄씩 읽기
		DefaultLineMapper<DiveShopCsvData> defaultLineMapper = new DefaultLineMapper<>();
		// 따로 설정하지 않으면 기본값은 ","
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames(DiveShopCsvData.getFieldNames().toArray(String[]::new));
		delimitedLineTokenizer.setStrict(false);
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

		// 매칭할 class 타입 지정(필드 지정)
		BeanWrapperFieldSetMapper<DiveShopCsvData> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(DiveShopCsvData.class);

		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		flatFileItemReader.setLineMapper(defaultLineMapper);

		return flatFileItemReader;
	}
}
