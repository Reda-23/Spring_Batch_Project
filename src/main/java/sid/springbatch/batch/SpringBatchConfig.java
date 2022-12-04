package sid.springbatch.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.core.io.Resource;
import sid.springbatch.entity.Football;
import sid.springbatch.repository.FootballRepository;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilder;
    @Autowired
    private StepBuilderFactory stepBuilder;
    @Autowired
    private ItemWriter<Football> footballItemWriter;
    @Autowired
    private ItemProcessor<Football,Football> footballItemProcessor;
    @Autowired
    private ItemReader<Football> footballItemReader;





    @Bean
    public Job footJob(){
        Step step1 = stepBuilder.get("ETL-Load-File")
                .<Football,Football>chunk(100)
                .reader(footballItemReader)
                .processor(footballItemProcessor)
                .writer(footballItemWriter)
                .build();

        return jobBuilder.get("ETL-read").start(step1).build();
    }

    @Bean
    FootballItemProcessor itemProcessor(){
        return new FootballItemProcessor();
    }

    @Bean
    public FlatFileItemReader<Football> reader(@Value("${inputFile}") Resource inputFile){
        FlatFileItemReader<Football> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("Football");
        flatFileItemReader.setResource(inputFile);
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Football> lineMapper() {
        DefaultLineMapper<Football> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("team","city","stadium","capacity","latitude","longitude","country");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<Football> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Football.class);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return lineMapper;
    }



}
