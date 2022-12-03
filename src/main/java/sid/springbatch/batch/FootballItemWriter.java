package sid.springbatch.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import sid.springbatch.entity.Football;
import sid.springbatch.repository.FootballRepository;

import java.util.List;

public class FootballItemWriter implements ItemWriter<Football> {

    @Autowired
    private FootballRepository footballRepository;
    @Override
    public void write(List<? extends Football> list) throws Exception {
            footballRepository.saveAll(list);
    }
}
