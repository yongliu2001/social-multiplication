package microservices.book.multiplication.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by yongliu on 3/6/18.
 */
public class RandomGeneratorServiceImplTest {

    private RandomGeneratorServiceImpl randomGeneratorServiceImpl;

    @Before
    public void setUp() throws Exception {
        randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
    }

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits() throws Exception {
        //when a good sample of randomly generated factor is generated
        List<Integer> randomFacotors = IntStream.range(0, 1000)
                .map(i -> randomGeneratorServiceImpl.generateRandomFactor())
                .boxed()
                .collect(Collectors.toList());
        //then all of them should be between 11 and 100
        //because we want a middle-complexity calculation
        assertThat(randomFacotors).containsOnlyElementsOf(
                IntStream.range(11, 100).boxed().collect(Collectors.toList()));
    }

}