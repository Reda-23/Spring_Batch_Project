package sid.springbatch.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FOOTBALL_DB")
@Data @AllArgsConstructor @NoArgsConstructor
public class Football {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TEAM")
    private String team;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STADIUM_NAME")
    private String stadium;
    @Column(name = "CAPACITY")
    private String capacity;
    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;
    @Column(name = "COUNTRY")
    private String country;


}
