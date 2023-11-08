package com.wanted.babdoduk.batch;


import com.wanted.babdoduk.batch.client.response.success.ClientRestaurant;
import com.wanted.babdoduk.common.domain.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "raw_restaurant", indexes = @Index(name = "idx__manage_no", columnList = "manage_no"))
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Getter
public class RawRestaurant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------------------------------- 실제 API 에서 사용하는 데이터 [시작] --------------------------------------------------

    // 시군 코드
    @Column(name = "sigun_cd")
    private String sigunCd;

    // 위치 (위도)
    @Column(name = "refine_wgs84_lat")
    private String lat;

    // 관리 번호
    @Column(name = "manage_no", unique = true)
    private String manageNo;

    // 식당 이름
    @Column(name = "bizplc_nm")
    private String name;

    // 영업 상태(ex, 영업/정상, 폐업)
    @Column(name = "bsn_state_nm")
    private String bsnStateNm;

    // 식당 종류(ex, 한식, 식육(숯불구이), 일식 etc)
    @Column(name = "bizcond_div_nm_info")
    private String cuisineType;

    // 위치 (경도)
    @Column(name = "refine_wgs84_logt")
    private String lon;

    // 시군 이름(ex, 안산시)
    @Column(name = "sigun_nm")
    private String sigunNm;

    // 지번 주소
    @Column(name = "refine_lotno_addr")
    private String jibunAddr;

    // 도로명 주소
    @Column(name = "refine_roadnm_addr")
    private String roadAddr;

    // ---------------------------------- 실제 API 에서 사용하는 데이터 [끝] --------------------------------------------------

    // 식당 영업 상태(bsnStateNm 과 같습니다, OpenAPI 에서는 두 개를 내려주고 있음)
    @Column(name = "unity_bsn_state_nm")
    private String unityBsnStateNm;

    // 인허가일자(ex, 20011207)
    @Column(name = "licensg_de")
    private String licensgDe;

    // 인허가 취소일자(ex, 20011207)
    @Column(name = "licensg_cancl_de")
    private String licensgCanclDe;

    // 인허가 취소 일자(ex, 20011207)
    @Column(name = "bsn_state_div_cd")
    private String bsnStateDivCd;

    // 영업상태구분코드(ex, 01, 02)
    @Column(name = "unity_bsn_state_div_cd")
    private String unityBsnStateDivCd;

    // 폐업일자(ex, 19981202)
    @Column(name = "clsbiz_de")
    private String clsbizDe;

    // 휴업시작일자
    @Column(name = "suspnbiz_begin_de")
    private String suspnbizBeginDe;

    // 휴업종료일자
    @Column(name = "suspnbiz_end_de")
    private String suspnbizEndDe;

    // 재개업일자
    @Column(name = "reopenbiz_de")
    private String reopenbizDe;

    // 소재지시설전화번호(ex, 031 767 7599)
    @Column(name = "locplc_faclt_telno")
    private String locplcFacltTelno;

    // 소재지면적정보(ex, 84.11)
    @Column(name = "locplc_ar_info")
    private String locplcArInfo;

    // X 좌표값
    @Column(name = "x_crdnt_vl")
    private String xCrdntVl;

    // Y 좌표값
    @Column(name = "y_crdnt_vl")
    private String yCrdntVl;

    // 위생 업태명(ex, bizcond_div_nm_info 과 같습니다.)
    @Column(name = "sanittn_bizcond_nm")
    private String sanittnBizcondNm;

    // 남성종사자수
    @Column(name = "male_enflpsn_cnt")
    private Long maleEnflpsnCnt;

    // 여성종사자수(ex 1, 2)
    @Column(name = "female_enflpsn_cnt")
    private Long femaleEnflpsnCnt;

    // 영업장주변구분명 (주택가주변, 아파트지역, 유흥업소밀집지역, 결혼예식장주변, 학교정화(상대), 학교정화(절대), 기타)
    @Column(name = "bsnsite_circumfr_div_nm")
    private String bsnsiteCircumfrDivNm;

    // 등급구분명(자율, 우수, 지도, 관리, 을, 갑)
    @Column(name = "grad_div_nm")
    private String gradDivNm;

    // 급수시설구분명(상수도전용, 지하수전용, 전용상수도(특정시설의 자가용 수도), 간이상수도)
    @Column(name = "grad_faclt_div_nm")
    private String gradFacltDivNm;

    // 총종업원수
    @Column(name = "tot_emply_cnt")
    private Long totEmplyCnt;

    // 본사종업원수
    @Column(name = "headofc_emply_cnt")
    private Long headofcEmplyCnt;

    // 공장사무직종업원수
    @Column(name = "factry_ofcrk_dut_emply_cnt")
    private Long factryOfcrkDutEmplyCnt;

    // 공장판매직종업원수
    @Column(name = "factry_sale_dut_emply_cnt")
    private Long factrySaleDutEmplyCnt;

    // 공장생산직종업원수
    @Column(name = "factry_prodctn_dut_emply_cnt")
    private Long factryProdctnDutEmplyCnt;

    // 건물소유구분명(자가)
    @Column(name = "buldng_posesn_div_nm")
    private String buldngPosesnDivNm;

    // 보증액
    @Column(name = "assurnc_amt")
    private Long assurncAmt;

    // 월세액
    @Column(name = "mtrent_amt")
    private Long mtrentAmt;

    // 다중이용업소여부(Y,N)
    @Column(name = "multi_use_bizestbl_yn")
    private String multiUseBizestblYn;

    // 시설총규모정보
    @Column(name = "faclt_tot_scale_info")
    private String facltTotScaleInfo;

    // 전통업소지정번호
    @Column(name = "traditn_bizestbl_appont_no")
    private String traditnBizestblAppontNo;

    // 전통업소주된음식
    @Column(name = "traditn_bizestbl_chief_food_nm")
    private String traditnBizestblChiefFoodNm;

    // 홈페이지 URL
    @Column(name = "hmpg_url")
    private String hmpgUrl;

    // 소재지우편번호
    @Column(name = "refine_zip_cd")
    private String refineZipCd;

    public static RawRestaurant of(ClientRestaurant clientRestaurant) {
        return RawRestaurant.builder()
                // [시작] 반드시 들어가야할 데이터(API 에서 사용함)
                .lon(clientRestaurant.getLon())
                .lat(clientRestaurant.getLat())
                .sigunNm(clientRestaurant.getSigunNm())
                .name(clientRestaurant.getName())
                .jibunAddr(clientRestaurant.getJibunAddr())
                .roadAddr(clientRestaurant.getRoadAddr())
                .sigunCd(clientRestaurant.getSigunCode())
                .manageNo(clientRestaurant.getManageNo())
                .bsnStateNm(clientRestaurant.getUnityBsnStateNm())
                .cuisineType(clientRestaurant.getCuisineType())
                // [끝] 반드시 들어가야할 데이터(API 에서 사용함)

                .licensgDe(clientRestaurant.getLicensgDe())
                .licensgCanclDe(clientRestaurant.getLicensgCanclDe())
                .bsnStateDivCd(clientRestaurant.getBsnStateDivCD())
                .unityBsnStateDivCd(clientRestaurant.getUnityBsnStateDivCD())
                .clsbizDe(clientRestaurant.getClsbizDe())
                .suspnbizBeginDe(clientRestaurant.getSuspnbizBeginDe())
                .suspnbizEndDe(clientRestaurant.getSuspnbizEndDe())
                .reopenbizDe(clientRestaurant.getReopenbizDe())
                .locplcFacltTelno(clientRestaurant.getLocplcFacltTelno())
                .locplcArInfo(clientRestaurant.getLocplcArInfo())
                .xCrdntVl(clientRestaurant.getXCrdntVl())
                .yCrdntVl(clientRestaurant.getYCrdntVl())
                .sanittnBizcondNm(clientRestaurant.getCuisineType())
                .maleEnflpsnCnt(clientRestaurant.getMaleEnflpsnCnt())
                .femaleEnflpsnCnt(clientRestaurant.getFemaleEnflpsnCnt())
                .bsnsiteCircumfrDivNm(clientRestaurant.getBsnsiteCircumfrDivNm())
                .gradDivNm(clientRestaurant.getGradDivNm())
                .gradFacltDivNm(clientRestaurant.getGradFacltDivNm())
                .totEmplyCnt(clientRestaurant.getTotEmplyCnt())
                .headofcEmplyCnt(clientRestaurant.getHeadofcEmplyCnt())
                .factryOfcrkDutEmplyCnt(clientRestaurant.getFactryOfcrkDutEmplyCnt())
                .factrySaleDutEmplyCnt(clientRestaurant.getFactrySaleDutEmplyCnt())
                .factryProdctnDutEmplyCnt(clientRestaurant.getFactryProdctnDutEmplyCnt())
                .buldngPosesnDivNm(clientRestaurant.getBuldngPosesnDivNm())
                .assurncAmt(clientRestaurant.getAssurncAmt())
                .mtrentAmt(clientRestaurant.getMtrentAmt())
                .multiUseBizestblYn(clientRestaurant.getMultiUseBizestblYn())
                .facltTotScaleInfo(clientRestaurant.getFacltTotScaleInfo())
                .traditnBizestblAppontNo(clientRestaurant.getTraditnBizestblAppontNo())
                .traditnBizestblChiefFoodNm(clientRestaurant.getTraditnBizestblChiefFoodNm())
                .hmpgUrl(clientRestaurant.getHmpgURL())
                .refineZipCd(clientRestaurant.getRefineZipCD())
                .build();
    }

    public void update(ClientRestaurant restaurant) {
        // [시작] 반드시 들어가야할 데이터(API 에서 사용함)
        this.manageNo = restaurant.getManageNo();
        this.sigunCd = restaurant.getSigunCode();
        this.sigunNm = restaurant.getSigunNm();
        this.name = restaurant.getName();
        this.jibunAddr = restaurant.getJibunAddr();
        this.roadAddr = restaurant.getRoadAddr();
        this.bsnStateNm = restaurant.getBsnStateName();
        this.cuisineType = restaurant.getCuisineType();
        this.lon = restaurant.getLon();
        this.lat = restaurant.getLat();
        // [끝] 반드시 들어가야할 데이터(API 에서 사용함)

        this.licensgDe = restaurant.getLicensgDe();
        this.licensgCanclDe = restaurant.getLicensgCanclDe();
        this.bsnStateDivCd = restaurant.getBsnStateDivCD();
        this.unityBsnStateDivCd = restaurant.getUnityBsnStateDivCD();
        this.unityBsnStateNm = restaurant.getUnityBsnStateNm();
        this.clsbizDe = restaurant.getClsbizDe();
        this.suspnbizBeginDe = restaurant.getSuspnbizBeginDe();
        this.suspnbizEndDe = restaurant.getSuspnbizEndDe();
        this.reopenbizDe = restaurant.getReopenbizDe();
        this.locplcFacltTelno = restaurant.getLocplcFacltTelno();
        this.locplcArInfo = restaurant.getLocplcArInfo();
        this.xCrdntVl = restaurant.getXCrdntVl();
        this.yCrdntVl = restaurant.getYCrdntVl();
        this.sanittnBizcondNm = restaurant.getCuisineType();
        this.maleEnflpsnCnt = restaurant.getMaleEnflpsnCnt();
        this.femaleEnflpsnCnt = restaurant.getFemaleEnflpsnCnt();
        this.bsnsiteCircumfrDivNm = restaurant.getBsnsiteCircumfrDivNm();
        this.gradDivNm = restaurant.getGradDivNm();
        this.gradFacltDivNm = restaurant.getGradFacltDivNm();
        this.totEmplyCnt = restaurant.getTotEmplyCnt();
        this.headofcEmplyCnt = restaurant.getHeadofcEmplyCnt();
        this.factryOfcrkDutEmplyCnt = restaurant.getFactryOfcrkDutEmplyCnt();
        this.factrySaleDutEmplyCnt = restaurant.getFactrySaleDutEmplyCnt();
        this.factryProdctnDutEmplyCnt = restaurant.getFactryProdctnDutEmplyCnt();
        this.buldngPosesnDivNm = restaurant.getBuldngPosesnDivNm();
        this.assurncAmt = restaurant.getAssurncAmt();
        this.mtrentAmt = restaurant.getMtrentAmt();
        this.multiUseBizestblYn = restaurant.getMultiUseBizestblYn();
        this.facltTotScaleInfo = restaurant.getFacltTotScaleInfo();
        this.traditnBizestblAppontNo = restaurant.getTraditnBizestblAppontNo();
        this.traditnBizestblChiefFoodNm = restaurant.getTraditnBizestblChiefFoodNm();
        this.hmpgUrl = restaurant.getHmpgURL();
        this.refineZipCd = restaurant.getRefineZipCD();
    }
}
