package sid.springbatch.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import sid.springbatch.entity.Football;

@Component
public class FootballItemProcessor implements ItemProcessor<Football,Football> {

    @Override
    public Football process(Football football) throws Exception {
        return football;
    }
}
