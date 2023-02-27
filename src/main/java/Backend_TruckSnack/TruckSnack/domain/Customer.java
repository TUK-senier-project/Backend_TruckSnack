package Backend_TruckSnack.TruckSnack.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer" , schema = "SnackTest")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false ,length = 50)
    private String name;
    private LocalDateTime is_created;
    private LocalDateTime is_updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getIs_created() {
        return is_created;
    }

    public void setIs_created(LocalDateTime is_created) {
        this.is_created = is_created;
    }

    public LocalDateTime getIs_updated() {
        return is_updated;
    }

    public void setIs_updated(LocalDateTime is_updated) {
        this.is_updated = is_updated;
    }
}
