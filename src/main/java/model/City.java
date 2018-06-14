package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="CITY")
public class City {

    @Id
    @SequenceGenerator(name = "SequenceCityGenerator", sequenceName = "citySeq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceCityGenerator")
    private Integer id;

    @Column
    private String name;

    @Column(name = "avg_population")
    private Integer avgPopulation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

}
