package sid.springbatch.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import sid.springbatch.entity.Football;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilder;
    private StepBuilderFactory stepBuilder;
    private ItemWriter<Football> itemWriter;
    private ItemProcessor<Football,Football> itemProcessor;
    private ItemReader<Football> itemReader;


    public Job job(){
        Step step1 = stepBuilder.get("ETL-Load-File")
                .<Football,Football>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilder.get("ETL-read").start(step1).build();
    }

    public FlatFileItemReader<Football> reader(){
        FlatFileItemReader<Football> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("Football");
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/stadium.csv"));
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;
    }

    private LineMapper<Football> lineMapper() {
        DefaultLineMapper<Football> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("team","city","stadium","capacity","latitude","longitude","country");

        BeanWrapperFieldSetMapper<Football> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Football.class);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return lineMapper;
    }


}
