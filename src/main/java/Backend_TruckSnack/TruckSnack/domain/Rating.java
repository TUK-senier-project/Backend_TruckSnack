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
        name = "Rating" ,
        schema = "SnackTest"
)
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    /**
     * rating_name - category name
     * total_vote - 총 투표
     * service_grade - 해당 카테고리의 전체 평점
     */
    @Column(name = "RATING_NAME",nullable = false)
    private String ratingName;
    @Column(name = "TOTAL_VOTE",nullable = false)
    private int totalVote;
    @Column(name = "SERVICE_GRADE",nullable = false)
    private double ServiceGrade;
}
