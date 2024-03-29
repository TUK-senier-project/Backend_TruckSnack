package Backend_TruckSnack.TruckSnack.repository.mapping;

public interface FoodListMapping {
    Long getSeq();
    String getId();
    String getBusinessName();
    int getDeadline();
    String getPhoneNumber();
    Double getGrade();
    String getSellerImgS3Url();

    String getLocation();
}
