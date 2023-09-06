package Backend_TruckSnack.TruckSnack.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "admin" ,
        schema = "truck_snack_db",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id_unique",
                        columnNames = "id"
                )
        }
)
@Data
public class Admin {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "nickName")
    private String nickName;
}
