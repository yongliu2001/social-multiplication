package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by yongliu on 3/6/18.
 */
public class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

        //With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService,
                attemptRepository, userRepository);
    }

    @Test
    public void createRandomMultiplicationTest() throws Exception {

        // given (our mocked Random Generator service will return first 50, then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        //when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

        //assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest() throws Exception {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("Mr Right");
        MultiplicationResultAttempt attempt =
                new MultiplicationResultAttempt(user, multiplication, 3000, false);

        MultiplicationResultAttempt verifiedAttempt =
                new MultiplicationResultAttempt(user, multiplication, 3000, true);
        given(userRepository.findByAlias("Mr Right")).willReturn(Optional.empty());

        //when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

        //assert
        assertThat(attemptResult).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
    }

    @Test
    public void checkWrongAttemptTest() throws Exception {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("Mr Wrong");
        MultiplicationResultAttempt attempt =
                new MultiplicationResultAttempt(user, multiplication, 3010, false);

        given(userRepository.findByAlias("Mr Wrong")).willReturn(Optional.empty());

        //when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

        //assert
        assertThat(attemptResult).isFalse();
        verify(attemptRepository).save(attempt);

    }

    @Test
    public void retrieveStatsTest() throws Exception {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(
                user, multiplication, 3010, false);

        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(
                user, multiplication, 3051, false);

        List<MultiplicationResultAttempt> latestAttempts =
                Lists.newArrayList(attempt1, attempt2);

        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe"))
                .willReturn(latestAttempts);

        // when
        List<MultiplicationResultAttempt> latestAttemptsResult =
                multiplicationServiceImpl.getStatsForUser("john_doe");

        // then
        assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
    }
}






























