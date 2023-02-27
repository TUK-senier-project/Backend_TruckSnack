package Backend_TruckSnack.TruckSnack.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "customer" ,
        schema = "SnackTest",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id_unique",
                        columnNames = "id"
                )
        }
)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "ID",nullable = false ,length = 35)
    private String id;

    @Column(name = "NAME",nullable = false ,length = 10)
    private String name;
    @Column(name = "PASSWORD",nullable = false ,length = 20)
    private String password;
    @Column(name = "CUSTOMER_IMG_S3_URL",nullable = true ,length = 100)
    private String customer_img_s3_url;
    @Column(name = "PHONE_NUMBER",nullable = true ,length = 11)
    private String phone_number;
    @Column(name = "LOCATION",nullable = true ,length = 50)
    private String location;
    @Column(name = "IS_CREATED",nullable = false)
    private LocalDateTime is_created;
    @Column(name = "IS_UPDATED",nullable = false)
    private LocalDateTime is_updated;
    @Column(name = "IS_DELETED",nullable = false)
    private boolean is_deleted;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomer_img_s3_url() {
        return customer_img_s3_url;
    }

    public void setCustomer_img_s3_url(String customer_img_s3_url) {
        this.customer_img_s3_url = customer_img_s3_url;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
}
