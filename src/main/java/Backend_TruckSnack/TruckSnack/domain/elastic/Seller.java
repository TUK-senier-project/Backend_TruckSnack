package Backend_TruckSnack.TruckSnack.domain.elastic;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(indexName = "seller")
public class Seller {
    @Id
    private Long seq;
    @Field(type = FieldType.Text)
    private String id;
    @Field(type = FieldType.Text)
    private String password;
    @Field(type = FieldType.Keyword)
    private String businessName;
    @Field(type = FieldType.Keyword)
    private String content;
    @Field(type = FieldType.Integer)
    private  int category;
    @Field(type = FieldType.Integer)
    private int deadline;
    @Field(type = FieldType.Text)
    private String phoneNumber;
    @Field(type = FieldType.Double)
    private double grade;
    @Field(type = FieldType.Text)
    private String sellerImgS3Url;
    @Field(type = FieldType.Text)
    private String location;
    @Field(type = FieldType.Date)
    private LocalDateTime isCreated;
    @Field(type = FieldType.Date)
    private LocalDateTime isUpdated;
    @Field(type = FieldType.Boolean)
    private boolean isDeleted;

}
