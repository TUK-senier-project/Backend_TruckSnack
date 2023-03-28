package Backend_TruckSnack.TruckSnack.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CategoryUtil {
    private static int MAX_CATEGORY_NUMBER = 3;

    private static String CATEGORY_NAME_1 = "붕어삥";
    private static String CATEGORY_NAME_2 = "스테이크";
    private static String CATEGORY_NAME_3 = "분식";


    public int max_category_number(){
        return MAX_CATEGORY_NUMBER;
    }

    public List<String> category_name_list(){
        List<String> category_name_list = null;
        category_name_list.add(CATEGORY_NAME_1);
        category_name_list.add(CATEGORY_NAME_2);
        category_name_list.add(CATEGORY_NAME_3);

        return category_name_list;
    }


    public boolean check_this_category_number(int category_number){
        int max= max_category_number();
        // 실패 : 값이 조건에 안맞음

        if(category_number < 1 || category_number>max){
            return false;
        }
        else{
            return true;
        }
    }

}
