package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="COUNTRY")
public class Country {

    @Id
    @SequenceGenerator(name = "SequenceCountryGenerator", sequenceName = "countrySeq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceCountryGenerator")
    private Integer id;

    @Column
    private String name;
}
