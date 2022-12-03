package sid.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sid.springbatch.entity.Football;

public interface FootballRepository extends JpaRepository<Football,Integer> {
}
