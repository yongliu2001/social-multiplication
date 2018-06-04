package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yongliu on 4/6/18.
 */
public interface MultiplicationRepository
        extends CrudRepository<Multiplication, Long> {
}
