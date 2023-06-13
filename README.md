<h1>증강현실 기반 푸드트럭 관리 시스템</h1>

 ![image](https://github.com/TUK-senier-project/Backend_TruckSnack/assets/76008226/d97f802e-ee6b-4cd9-9f5c-531446d19008)

푸드트럭과 손님들을 위한 푸드트럭 길 찾기 및 정보 제공 시스템
- 손님들에게 푸드트럭 위치 관련 및 AR 네비게이션과 주문 서비스를 제공
- 판매자는 손님의 위치 조회 및 홍보, 관리 서비스를 제공

<h2>System Architecture</h2>
<img src="https://github.com/TUK-senier-project/Backend_TruckSnack/assets/76008226/073d1a99-4e8b-42f0-b897-8b0812eb9ea9  width="200" height="400"/>

<h2>Pages</h2>


<h2><사용자 Page></h2>

![사용자](https://github.com/TUK-senier-project/Backend_TruckSnack/assets/76008226/1b21b2ae-c57d-40ae-9644-9e1b9eaa3422)

<h2><판매자 page></h2>

![판매자](https://github.com/TUK-senier-project/Backend_TruckSnack/assets/76008226/327feeb8-033a-434f-91b5-009ea50578d7)


<h2>ERD</h2>
 
![image](https://github.com/TUK-senier-project/Backend_TruckSnack/assets/76008226/bd579c86-80c9-4efe-8970-1a671fc9a045)
                                                                                                                                                 
<h2>Developer</h2>
<table><colgroup><col><col><col></colgroup><tbody><tr><td><p>이름</p></td><td><p>개발분야</p></td><td><p>개인 Github</p></td></tr>
<tr><td><p>양세진</p></td><td><p>Back-end , front-end</p></td><td><p>https://github.com/Sejin-999</p></td></tr>
<tr><td><p>박민혁</p></td><td><p>Android</p></td><td><p>https://github.com/min-hyeok-Park</p></td></tr>
<tr><td><p>유정민</p></td><td><p>Android</p></td><td><p>https://github.com/YooJungMin222</p></td></tr>
</tbody></table>
    

<h2>Server API</h2>
    
<p>&lt;Customer 회원가입&gt;</p>

<table><colgroup><col><col><col><col><col><col></colgroup><tbody><tr><td><p>API NAME</p></td><td><p>URI</p></td><td><p>기능</p></td><td><p>요구사항</p></td><td><p>세부 사항</p></td><td><p>REST API</p></td></tr>
<tr><td><p>회원가입</p></td><td><p>/customer/register</p></td><td><p>회원가입</p></td><td><p>id={}, password={} , name={} , phoneNumber={} , location={}</p></td><td><p>값 검증에 대한 세부사항</p></td><td><p>POST</p></td></tr>
<tr><td><p>회원가입-ID확인</p></td><td><p>/customer/register-check</p></td><td><p>회원가입중 id 중복체크</p></td><td><p>id={}</p></td><td><p>아아디 중복체크</p></td><td><p>POST</p></td></tr>
<tr><td><p>로그인</p></td><td><p>/customer/login</p></td><td><p>로그인</p></td><td><p>id={}, password={}</p></td><td><p>로그인</p></td><td><p>POST</p></td></tr>
<tr><td><p>아이디 파인더</p></td><td><p>/customer/idFind</p></td><td><p>아아디 파인드</p></td><td><p>name={} , phoneNumber={}</p></td><td><p>이름 과 핸드폰 번호</p></td><td><p>POST</p></td></tr>
<tr><td><p>Profile Img Upload</p></td><td><p>/customer/imgUpload/{customerId}/</p></td><td><p>프로파일 이미지 업로드</p></td><td><p>("images") MultipartFile multipartFile , customerId</p></td><td><p>이미지 업로드</p></td><td><p>POST</p></td></tr>
</tbody></table>

<p>&lt;Seller 회원가입&gt;</p>

<table><colgroup><col><col><col><col><col><col></colgroup><tbody><tr><td><p>API NAME</p></td><td><p>URI</p></td><td><p>기능</p></td><td><p>요구사항</p></td><td><p>세부 사항</p></td><td><p>REST API</p></td></tr>
<tr><td><p>회원가입</p></td><td><p>/seller/register</p></td><td><p>회원가입</p></td><td><p>id = {} , businessName = {} , password={} , content = {} , category= {} , deadline={}," + "phoneNumber ={} , location= {}</p></td><td><p>값 검증에 대한 세부사항</p></td><td><p>POST</p></td></tr>
<tr><td><p>회원가입-중복체크</p></td><td><p>/seller/register-check</p></td><td><p>회원가입중 id 중복체크</p></td><td><p>id={}</p></td><td><p>아아디 중복체크</p></td><td><p>POST</p></td></tr>
<tr><td><p>로그인</p></td><td><p>/seller/login</p></td><td><p>로그인</p></td><td><p>id={}, password={}</p></td><td><p>로그인</p></td><td><p>POST</p></td></tr>
<tr><td><p>ID 찾기</p></td><td><p>/seller/idfind</p></td><td><p>아아디 파인드</p></td><td><p>businessName={} , phoneNumber={}</p></td><td><p>이름 과 핸드폰 번호</p></td><td><p>POST</p></td></tr>
<tr><td><p>Profile Img Upload</p></td><td><p>/seller/imgUpload/셀러아이디/</p></td><td><p>프로파일 이미지 업로드</p></td><td><p>("images") MultipartFile multipartFile , customerId</p></td><td><p>이미지 업로드</p></td><td><p>POST</p></td></tr>
<tr><td><p>review_seller</p></td><td><p>/seller/review/{seller_id}</p></td><td><p>셀러가 가진 리뷰 데이터를 json list로 반환</p></td><td><p>seller_id</p></td><td><p>셀러아이디</p></td><td><p>GET</p></td></tr>
</tbody></table>
<p>&lt;Food-list&gt;</p>

<table><colgroup><col><col><col><col><col><col></colgroup><tbody><tr><td><p>API NAME</p></td><td><p>URI</p></td><td><p>기능</p></td><td><p>요구사항</p></td><td><p>세부 사항</p></td><td><p>REST API</p></td></tr>
<tr><td><p>푸드 리스트 조회</p></td><td><p>/food-list/{categoryNumber}</p></td><td><p>카테고리 별 푸드리스트 조회</p></td><td><p>카테고리 번호</p></td><td><p>위 카테고리 별로 보내면 된다.</p></td><td><p>GET</p></td></tr>
<tr><td><p>푸드 디테일 조회</p></td><td><p>/food-list/detail/{sellerId}</p></td><td><p>sellerId 별로 디테일 정보 조회하기</p></td><td><p>셀러아이디</p></td><td><p>sellerid를 보내주면 그 seller가 가진 푸드리스트를 반환한다</p></td><td><p>GET</p></td></tr>
<tr><td><p>category_rank</p></td><td><p>/rank/category/{type}&amp;{categoryNumber}/</p></td><td><p>타입 과 카테고리 넘버를 통한 정렬 리스트 제공</p></td><td><p>type &amp; category_number</p></td><td><p>아래 사항 참고</p></td><td><p>GET</p></td></tr>
</tbody></table>

<p>&lt;Food&gt;</p>

<table><colgroup><col><col><col><col><col><col></colgroup><tbody><tr><td><p>API NAME</p></td><td><p>URI</p></td><td><p>기능</p></td><td><p>요구사항</p></td><td><p>세부 사항</p></td><td><p>REST API</p></td></tr>
<tr><td><p>푸드 등록</p></td><td><p>/food/{sellerId}/upload-food</p></td><td><p>푸드 등록</p></td><td><p>sellerId={}, foodName={} , price={}</p></td><td><p>sellerId는 로그인 되어있는 정보여야 한다. 서버에서 있는지 없는지 체크한다.</p></td><td><p>POST</p></td></tr>
</tbody></table>

<p>&lt;OrderPayment&gt;</p>

<table><colgroup><col><col><col><col><col><col></colgroup><tbody><tr><td><p>API NAME</p></td><td><p>URI</p></td><td><p>기능</p></td><td><p>요구사항</p></td><td><p>세부 사항</p></td><td><p>REST API</p></td></tr>
<tr><td><p>오더 등록</p></td><td><p>/orderPayment/{c}ustomer_id/</p></td><td><p>오더 등록</p></td><td><p>"foodSeq": , "quantity":</p></td><td><p>json body로 푸드시퀀스와 양을 보내고 url로 customer_id를 보내면 된다.</p></td><td><p>POST</p></td></tr>
<tr><td><p>오더 리스트 반환(seller)</p></td><td><p>/orderPayment/order-list</p></td><td><p>셀러아이디에 주문된 목록을 반환한다.</p></td><td><p>seller_id</p></td><td><p>seller 아이디를 바디에 실어서 보내면 해당 샐러가 가진 오더리스트를 반환해준다.</p></td><td><p>POST</p></td></tr>
<tr><td><p>디테일 오더</p></td><td><p>/orderPayment/detail-order-list/</p></td><td><p>오더 번호를 주고 그것에 대한 자세한 주문 목록을 반환한다.</p></td><td><p>{"seq" : 5}</p></td><td><p>위에 오더리스트 반환이 존재하는데 여기서 SEQ 를 준다. 이걸로 json 바디에 실어서 요청하면 해당 주문에 대한 디테일 정보를 돌려준다.</p></td><td><p>POST</p></td></tr>
<tr><td><p>order_check</p></td><td><p>/orderPayment/order_check</p></td><td><p>주문이 들어온것을 사장이 받았다는 것을 알릴 수 있는 메소드</p></td><td><p>{"seq" : 5}</p></td><td><p>주문 번호를 주면 이에 대해 조회 하여 주문 정보를 오더 체크 상태로 변경해준다.</p></td><td><p>POST</p></td></tr>
<tr><td><p>order_cancel</p></td><td><p>/orderPayment/order_cancel</p></td><td><p>주문을 취소하는 기능인데 이거는 사용자도 사용할 수 있다.</p></td><td><p>{"seq" : 5}</p></td><td><p>주문 번호를 주면 이에대해 조회 하여 주문 정보를 오더 캔슬 상태로 변경해준다.</p></td><td><p>POST</p></td></tr>
<tr><td><p>order_complete</p></td><td><p>/orderPayment/order_complete</p></td><td><p>주문하고 이를 사용자가 받았다는 것을 확인하는 것이다.</p></td><td><p>{"seq" : 5}</p></td><td><p>주문 번호를 받아서 컴플리트 상태로 변경하는데 이 상태가 되면 리뷰 기능을 활성화된다.</p></td><td><p>GET</p></td></tr>
</tbody></table>

<table><colgroup><col><col><col><col><col><col></colgroup><tbody><tr><td><p>API NAME</p></td><td><p>URI</p></td><td><p>기능</p></td><td><p>요구사항</p></td><td><p>세부 사항</p></td><td><p>REST API</p></td></tr>
<tr><td><p>찜하기</p></td><td><p>/like/like_seller/</p></td><td><p>찜하기 기능</p></td><td><p>{"sellerId" : "sellerTest", "customerId" : "testab"}</p></td><td><p>찜하기 기능인데 중복될 경우 거부됨 똑같은 사용자가 똑같은 가계를 2개이상 지정못하게 막아둠</p></td><td><p>POST</p></td></tr>
<tr><td><p>MY찜 리스트</p></td><td><p>/like/my_like/</p></td><td><p>내가 찜한 목록 보기</p></td><td><p>{"customerId" : "testab"}</p></td><td><p>사용자 아이디를 보내주면 그 사용자가 가진 리스트를 반환한다.</p></td><td><p>POST</p></td></tr>
<tr><td><p>찜 취소v</p></td><td><p>/like/like_cancel_seller</p></td><td><p>찜하기 취소</p></td><td><p>{"sellerId" : "sellerTest", "customerId" : "testab"}</p></td><td><p>찜하기를 한 경우 이를 취소하는 기능</p></td><td><p>POST</p></td></tr>
</tbody></table>

<p>&lt;Communication&gt;</p>

<table><colgroup><col><col><col><col><col><col></colgroup><tbody><tr><td><p>API NAME</p></td><td><p>URI</p></td><td><p>기능</p></td><td><p>요구사항</p></td><td><p>세부 사항</p></td><td><p>REST API</p></td></tr>
<tr><td><p>review_grade</p></td><td><p>/communication/review&amp;grade/</p></td><td><p>리뷰 작성 및 평점 계산후 반영</p></td><td><p>아래 작성 확인</p></td><td><p>기존 Json방식은 최대한 간단하게 구현하려고 했는데 이번것은 약간 데이터가 많이 필요 해서 어려울 수 있다.</p></td><td><p>POST</p></td></tr>
</tbody></table>

<p>&lt;review_grade&gt;</p>

<table><colgroup><col><col><col><col><col><col></colgroup><tbody><tr><td><p>API NAME</p></td><td><p>URI</p></td><td><p>기능</p></td><td><p>요구사항</p></td><td><p>세부 사항</p></td><td><p>REST API</p></td></tr>
<tr><td><p>last_order_recommend</p></td><td><p>/recommend/orderData/</p></td><td><p>라스트 오더를 통한 추천 리스트반환</p></td><td><p>customerId: {} , selectService : {}</p></td><td><p>아래정보 확인</p></td><td><p>POST</p></td></tr>
</tbody></table>

<p>&lt;last_order_payment&gt;</p>


<table><colgroup><col><col><col><col><col><col></colgroup><tbody><tr><td><p>API NAME</p></td><td><p>URI</p></td><td><p>기능</p></td><td><p>요구사항</p></td><td><p>세부 사항</p></td><td><p>REST API</p></td></tr>
<tr><td><p>uploadFileTest</p></td><td><p>/upload-Test</p></td><td><p>이미지 업로드가 제대로 되고 있는지 확인해볼 수 있다.</p></td><td><p>@RequestParam("images") MultipartFile multipartFile</p></td><td><p>아래정보 확인</p></td><td><p>POST</p></td></tr>
<tr><td><p>getImgTest</p></td><td><p>/getImg-Test</p></td><td><p>seller 아이디를 통해 테스트를 해볼 수 있다.</p></td><td><p>id</p></td><td><p>아래정보 확인</p></td><td><p>POST</p></td></tr>
</tbody></table>

