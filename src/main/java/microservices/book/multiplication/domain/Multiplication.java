package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This represents a Multiplication (a * b)
 * Created by yongliu on 31/5/18.
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class Multiplication {

    @Id
    @GeneratedValue
    @Column(name = "MULTIPLICATION_ID")
    private  Long id;

    //Both factors
    private final int factorA;
    private final int factorB;

    //Empty constructor for JSON (de)serialization
    public Multiplication() {
        this(0,0);
    }

}
