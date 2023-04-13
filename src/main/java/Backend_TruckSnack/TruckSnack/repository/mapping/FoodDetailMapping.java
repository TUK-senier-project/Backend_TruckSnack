package Backend_TruckSnack.TruckSnack.repository.mapping;

public interface FoodDetailMapping {
    Long getSeq();
    String getFoodName();
    int getPrice();
    String getFoodImgS3URL();
}
