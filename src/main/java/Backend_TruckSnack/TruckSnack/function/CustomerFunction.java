package Backend_TruckSnack.TruckSnack.function;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;

public class CustomerFunction {
    // customer_register_check Start
    public int customer_register_check(String id, String password , String name , String phone_number , String location){
        //logging 용 ..

        AtomicInteger check_flag = new AtomicInteger();
        int check_value;

        List<Integer> functions = Arrays.asList(
                customer_id_check(id),
                customer_password_check(password),
                customer_name_check(name),
                customer_phone_number_check(phone_number),
                customer_location_check(location)
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
     * 컨트롤러에서 처리할 것들
     * 아이디 : 공백 확인 , 특수문자 확인 , 길이 확인(5~15) ,(한예외 처리)
     * 패스워드 : 공백확인,길이확인(8~20) , 문자가 영+숫 , (한예외처리)
     * 이름 : 공백확인 , 길이 확인(10글자 이내) , (한글이름만 허용)
     * 핸드폰 번호 : 공백확인 ,11 글자인지 , 숫자로만 이루어져 있는지
     * 지역 : 공백확인 ,특수문자 비허용 ,50글자 이내인지 확인
     */
    // 공백확인 함수 Start
    public int null_check(String customer_data){
        if(customer_data == null || "".equals(customer_data)){
            System.out.println("nullcheck");
            return 1;
        }
        return 0;
    }
    public int korean_check(String customer_data){
        if(customer_data.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
            System.out.println("korean check");
            return 1;
        }
        else{
            return 0;
        }
    }
    // 공배확인 함수 End
    //id check Start
    public int customer_id_check(String customer_id){
        int check_flag = 0; //0 성공 , 1 실패

        //공백 경우
        check_flag = null_check(customer_id);

        if(check_flag == 0){
            check_flag = korean_check(customer_id);
            if(check_flag == 0){
                //5~15글자가 아닌경우
                if(customer_id.length()>15 || customer_id.length()<5){
                    check_flag = 1;
                }
                //특수문자  확인
                else{
                    customer_id  = customer_id .replaceAll("\\p{Z}", "");
                    if (!customer_id.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
                        check_flag = 1;
                        System.out.println("customeridcheck");
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
    //id check End

    //password check Start
    public int customer_password_check(String customer_password){
        int check_flag = 0; //0 성공 , 1 실패
        //공백 경우
        check_flag = null_check(customer_password);

        if(check_flag == 0){
            check_flag = korean_check(customer_password);
            if(check_flag == 0){
                //8~20글자가 아닌경우
                if(customer_password.length()>20 ||customer_password.length()<8){
                    check_flag = 1;
                    System.out.println("customerPasswordcheck");
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
    //password check End

    //name check Start
    public int customer_name_check(String customer_name){
        int check_flag = 0; //0 성공 , 1 실패
        //공백 경우
        check_flag = null_check(customer_name);

        if(check_flag == 0){
            //한글로만 이루어진 경우 허용
            if(customer_name.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
                System.out.println("name_check");
                return 1; //한글이외에 다른 문자가 들어옴
            }
            //이름이 10글자 이내
            else if (customer_name.length()>10){
                return 1; //10글자 이상의 이름
            }
            else{
                return 0;
            }
        }
        else{
            //공백이 있는경우
            check_flag = 1;
        }


        return check_flag;
    }
    //name check End

    //phone_number check Start
    public int customer_phone_number_check(String customer_phone_number){
        int check_flag = 0; //0 성공 , 1 실패
        //공백 경우
        check_flag = null_check(customer_phone_number);
        int test_phone_number = Integer.parseInt(customer_phone_number);
        boolean isNumeric = isNumeric(test_phone_number); // 넘버릭을 통해 숫자만 들어와있는지  숫자만 true 아니면 false

        if(check_flag == 0){
            //숫자만 허용
            if(isNumeric == false){
                System.out.println("폰넘버 숫자 체크");
                return 1; //숫자이외에 다른 값이 들어옴
            }
            //11 자리가 아닌 경우
            else if (customer_phone_number.length() !=11){
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
    //phone_number check End

    //location check Start
    public int customer_location_check(String customer_location){
        int check_flag = 0; //0 성공 , 1 실패
        //공백 경우
        check_flag = null_check(customer_location);

        if(check_flag == 0){
            //특수문자 비허용
            customer_location  = customer_location.replaceAll("\\p{Z}", "");
            if (!customer_location.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
                System.out.println("로케이션 체크");
                //특수문자가 들어았는 경우
                check_flag = 1;
            }
            //정보가 50글자가 넘는경우
            else if (customer_location.length()>50){
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
    // customer_register_check End

    public int customer_login_check(String id , String password){
        int check_flag=0;




        return check_flag;
    }

}
