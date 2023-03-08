package Backend_TruckSnack.TruckSnack.function;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;

public class SellerFunction {
    public int seller_register_check(String id, String password , String bussiness_name ,String conetnt,int category , String phone_number , String location){
        //logging 용 ..

        AtomicInteger check_flag = new AtomicInteger();
        int check_value;

        List<Integer> functions = Arrays.asList(
                seller_id_check(id),
                seller_password_check(password),
                seller_businessName_check(bussiness_name),
                seller_phoneNumber_check(phone_number),
                seller_location_check(location),
                seller_content_check(conetnt),
                seller_category_check(category)
        );

        functions.forEach(x-> check_flag.addAndGet(x));
        System.out.println(check_flag);

        check_value = check_flag.get();
        if(check_value == 0){
            //성공한경우
            return 0;
        }
        else{
            //실패한 경우
            return 1;
        }
    }
    /**
     * seller 레지스터 에서 처리할 것들
     * 아아디 : 공백확인 , 특수문자확인 , 길이 확인 (5~15), (한예외처리)
     * 비지니스네임 : 최대 10글자 , 특수문자 제외 처리
     * 패스워드 : 공백확인 ,길이확인(8~20) , 문자가 영 + 숫 (한예외처리)
     * 컨텐트 : 50글자 이내
     * 카테고리 : 숫자 별로 처리됨
     * 핸드폰 번호 : 공백확인 ,11 글자인지 , 숫자로만 이루어져 있는지
     * 지역 : 공백확인 ,특수문자 비허용 ,50글자 이내인지 확인
     */
    /**
     * 카테고리 정리
     * 1. 붕어빵
     * 2. 스테이크
     * 3. 분식
     * 추가해야함
     */
    public int null_check(String seller_data){
        if(seller_data == null ||"".equals(seller_data)){
            System.out.println("null check");
            return 1;
        }
        return 0;
    }

    public int korean_check(String seller_data){
        if(seller_data.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
            System.out.println("korean check");
            return 1;
        }
        else{
            return 0;
        }
    }

    // seller- id check start
    public int seller_id_check(String seller_id){
        int check_flag = 0; //0 성공 , 1 실패

        //공백 경우
        check_flag = null_check(seller_id);

        if(check_flag == 0){
            check_flag = korean_check(seller_id);
            if(check_flag == 0){
                //5~15글자가 아닌경우
                if(seller_id.length()>15 || seller_id.length()<5){
                    check_flag = 1;
                }
                //특수문자  확인
                else{
                    seller_id  = seller_id .replaceAll("\\p{Z}", "");
                    if (!seller_id.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
                        check_flag = 1;
                        System.out.println("seller_id check");
                    }
                    else{
                        check_flag = 0;
                    }
                }
            }
            else{
                // 아이디에 한글이 포함된경우
                return 1;
            }
        }
        else{
            //공백이 있는경우
            check_flag = 1;
        }


        return check_flag;
    }
    // seller- id check end

    // seller - businessName check Start
    public  int seller_businessName_check(String businessName) {
        int check_flag = 0; //0 성공 , 1 실패
        //공백 경우
        check_flag = null_check(businessName);
        if (check_flag == 0) {
            if (businessName.length() > 10) {
                check_flag = 1;
            } else {
                businessName = businessName.replaceAll("\\p{Z}", "");
                if (!businessName.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
                    check_flag = 1;
                    System.out.println("bussiness 특문 check");
                } else {
                    check_flag = 0;
                }
            }
        }
        else{
            //공백이 있는경우
            check_flag = 1;
        }


        return check_flag;
    }
    // seller - businessName check end

    // seller - password check Start
    public int seller_password_check (String seller_password){
        int check_flag = 0; //0 성공 , 1 실패
        //공백 경우
        check_flag = null_check(seller_password);

        if(check_flag == 0){
            check_flag = korean_check(seller_password);
            if(check_flag == 0){
                //8~20글자가 아닌경우
                if(seller_password.length()>20 ||seller_password.length()<8){
                    check_flag = 1;
                    System.out.println("seller_password check");
                }
                else {
                    check_flag =0 ;
                }
            }
            else{
                // 아이디에 한글이 포함된경우
                return 1;
            }
        }
        else{
            //공백이 있는경우
            check_flag = 1;
        }


        return check_flag;
    }
    // seller - password check End

    // seller - content check start
    public int seller_content_check(String seller_content){
        int check_flag = 0; //0 성공 , 1 실패
        if(seller_content.length() >50 ){
            check_flag =1;
        }
        else{
            check_flag=0;
        }
        return check_flag;
    }
    // seller - content check end

    // seller - category check start
    public int seller_category_check(int seller_category){
        //허용 숫자 설정 1~3
        int check_flag = 0;
        int min_category = 1;
        int max_category = 3;
        if(seller_category< min_category || seller_category> max_category){
            check_flag = 1;
        }
        else{
            check_flag =0;
        }

        return check_flag;
    }
    // seller - category check end

    // seller - phoneNumber check Start
    public int seller_phoneNumber_check(String seller_phone_number){
        int check_flag = 0; //0 성공 , 1 실패
        //공백 경우
        check_flag = null_check(seller_phone_number);
        int test_phone_number = Integer.parseInt(seller_phone_number);
        boolean isNumeric = isNumeric(test_phone_number); // 넘버릭을 통해 숫자만 들어와있는지  숫자만 true 아니면 false

        if(check_flag == 0){
            //숫자만 허용
            if(isNumeric == false){
                System.out.println("폰넘버 숫자 체크");
                return 1; //숫자이외에 다른 값이 들어옴
            }
            //11 자리가 아닌 경우
            else if (seller_phone_number.length() !=11){
                System.out.println("폰넘버 길이 체크");
                return 1; //11글자가 이닌 숫자가 들어옴
            }
            else{
                //정상처리
                return 0;
            }
        }
        else{
            //공백이 있는경우
            check_flag = 1;
        }


        return check_flag;
    }
    // seller - phoneNumber check End
    // seller - location check start
    public int seller_location_check(String seller_location){
        int check_flag = 0; //0 성공 , 1 실패
        //공백 경우
        check_flag = null_check(seller_location);

        if(check_flag == 0){
            //특수문자 비허용
            seller_location  = seller_location.replaceAll("\\p{Z}", "");
            if (!seller_location.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
                System.out.println("로케이션 체크");
                //특수문자가 들어았는 경우
                check_flag = 1;
            }
            //정보가 50글자가 넘는경우
            else if (seller_location.length()>50){
                System.out.println("로케이션 체크");
                return 1; //50글자 이상의 내용
            }
            else{
                return 0;
            }
        }
        else{
            //공백이 있는경우
            check_flag = 1;
        }

        //location check End
        return check_flag;
    }
    // seller - location check end



}
