package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by yongliu on 4/6/18.
 */
public interface UserRepository
        extends CrudRepository<User, Long> {

    Optional<User> findByAlias(final String alias);
}
