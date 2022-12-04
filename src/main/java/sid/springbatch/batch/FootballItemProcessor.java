package sid.springbatch.batch;

import org.springframework.batch.item.ItemProcessor;

import sid.springbatch.entity.Football;

public class FootballItemProcessor implements ItemProcessor<Football,Football> {

    @Override
    public Football process(Football football) throws Exception {
        return football;
    }
}
