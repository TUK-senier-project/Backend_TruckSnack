package Backend_TruckSnack.TruckSnack.function;

public class CustomerFunction {
    /**
     * 컨트롤러에서 처리할 것들
     * 아이디 : 공백 확인 , 특수문자 확인 , 길이 확인(5~15)
     * 패스워드 : 공백확인,길이확인(20) , 문자가 영+숫 , (한예외처리)
     * 이름 : 공백확인 , 길이 확인(10글자 이내) ,
     * 핸드폰 번호 : 11 글자인지 , 숫자로만 이루어져 있는지
     * 지역 : 50글자 이내인지 확인
     */

    //id check Start
    public int customer_id_check(String customer_id){
        int check_flag = 0; //0 성공 , 1 실패

        //공백 경우
        if(customer_id == null || "".equals(customer_id)){
            check_flag = 1;
        }
        //5~15글자가 아닌경우
        else if(customer_id.length()>15 || customer_id.length()<5){
            check_flag = 1;
        }
        //특수문자  확인
        else{
            customer_id  = customer_id .replaceAll("\\p{Z}", "");
            if (!customer_id.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
                check_flag = 1;
            }
            else{
                check_flag = 0;
            }
        }


        return check_flag;
    }
    //id check End

    //password check Start
    public int customer_password_check(String customer_password){
        int check_flag = 0; //0 성공 , 1 실패
        return check_flag;
    }
    //password check End

    //name check Start
    public int customer_name_check(){
        int check_flag = 0; //0 성공 , 1 실패
        return check_flag;
    }
    //name check End

    //phone_number check Start
    public int customer_phone_number_check(){
        int check_flag = 0; //0 성공 , 1 실패
        return check_flag;
    }
    //phone_number check End

    //location check Start
    public int customer_location_check(){
        int check_flag = 0; //0 성공 , 1 실패
        return check_flag;
    }
    //location check End

}
