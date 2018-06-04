package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by yongliu on 4/6/18.
 */
public interface MultiplicationResultAttemptRepository
        extends CrudRepository<MultiplicationResultAttempt, Long> {

    /**
     *  @return the latest 5 attempts for a given user, identified by their alias.
     */
    List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);

}
