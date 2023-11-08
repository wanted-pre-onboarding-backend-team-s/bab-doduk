package com.wanted.babdoduk.batch.client.response.success;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ClientRestaurant {

    // -------------------------------<시작> API 실 사용 ---------------------------------------
    // 시군 코드
    @JsonProperty("SIGUN_CD")
    private String sigunCode;

    // 시군명
    @JsonProperty("SIGUN_NM")
    private String sigunNm;

    // 영업상태
    @JsonProperty(value = "BSN_STATE_NM")
    private String bsnStateName;

    // 위도
    @JsonProperty("REFINE_WGS84_LAT")
    private String lat;

    // 경도
    @JsonProperty("REFINE_WGS84_LOGT")
    private String lon;

    // 관리번호
    @JsonProperty("MANAGE_NO")
    private String manageNo;

    // 가게명
    @JsonProperty("BIZPLC_NM")
    private String name;

    // 위생 업태명(ex, 중국식, 일식)
    @JsonProperty("SANITTN_BIZCOND_NM")
    private String cuisineType;

    // 지번 주소
    @JsonProperty("REFINE_LOTNO_ADDR")
    private String jibunAddr;

    // 도로명 주소
    @JsonProperty("REFINE_ROADNM_ADDR")
    private String roadAddr;

    // ------------------------------- <끝> API 실 사용 ---------------------------------------

    // 인허가일자
    @JsonProperty("LICENSG_DE")
    private String licensgDe;

    // 인허가 취소일자
    @JsonProperty("LICENSG_CANCL_DE")
    private String licensgCanclDe;

    // 영업상태구분코드
    @JsonProperty("BSN_STATE_DIV_CD")
    private String bsnStateDivCD;

    // 인허가취소구분코드
    @JsonProperty("UNITY_BSN_STATE_DIV_CD")
    private String unityBsnStateDivCD;

    // 통합영업상태명
    @JsonProperty("UNITY_BSN_STATE_NM")
    private String unityBsnStateNm;

    // 폐업일자
    @JsonProperty("CLSBIZ_DE")
    private String clsbizDe;

    // 휴업시작일자
    @JsonProperty("SUSPNBIZ_BEGIN_DE")
    private String suspnbizBeginDe;

    // 휴업종료일자
    @JsonProperty("SUSPNBIZ_END_DE")
    private String suspnbizEndDe;

    // 재개업일자
    @JsonProperty("REOPENBIZ_DE")
    private String reopenbizDe;

    // 소재지시설전화번호
    @JsonProperty("LOCPLC_FACLT_TELNO")
    private String locplcFacltTelno;

    // 소재지면적정보
    @JsonProperty("LOCPLC_AR_INFO")
    private String locplcArInfo;

    // 위도 X 좌표값
    @JsonProperty("X_CRDNT_VL")
    private String xCrdntVl;

    // 경도 Y 좌표값
    @JsonProperty("Y_CRDNT_VL")
    private String yCrdntVl;

    // 남성종사자수
    @JsonProperty("MALE_ENFLPSN_CNT")
    private Long maleEnflpsnCnt;

    // 여성종사자수
    @JsonProperty("FEMALE_ENFLPSN_CNT")
    private Long femaleEnflpsnCnt;

    // 영업장주변구분명
    @JsonProperty("BSNSITE_CIRCUMFR_DIV_NM")
    private String bsnsiteCircumfrDivNm;

    // 등급구분명
    @JsonProperty("GRAD_DIV_NM")
    private String gradDivNm;

    // 급수시설구분명
    @JsonProperty("GRAD_FACLT_DIV_NM")
    private String gradFacltDivNm;

    // 총종업원수
    @JsonProperty("TOT_EMPLY_CNT")
    private Long totEmplyCnt;

    // 본사종업원수
    @JsonProperty("HEADOFC_EMPLY_CNT")
    private Long headofcEmplyCnt;

    // 공장사무직종업원수
    @JsonProperty("FACTRY_OFCRK_DUT_EMPLY_CNT")
    private Long factryOfcrkDutEmplyCnt;

    // 공장판매직종업원수
    @JsonProperty("FACTRY_SALE_DUT_EMPLY_CNT")
    private Long factrySaleDutEmplyCnt;

    // 공장생산직종업원수
    @JsonProperty("FACTRY_PRODCTN_DUT_EMPLY_CNT")
    private Long factryProdctnDutEmplyCnt;

    // 건물소유구분명
    @JsonProperty("BULDNG_POSESN_DIV_NM")
    private String buldngPosesnDivNm;

    // 보증액
    @JsonProperty("ASSURNC_AMT")
    private Long assurncAmt;

    // 월세액
    @JsonProperty("MTRENT_AMT")
    private Long mtrentAmt;

    // 다중이용업소여부
    @JsonProperty("MULTI_USE_BIZESTBL_YN")
    private String multiUseBizestblYn;

    // 시설총규모정보
    @JsonProperty("FACLT_TOT_SCALE_INFO")
    private String facltTotScaleInfo;

    // 전통업소지정번호
    @JsonProperty("TRADITN_BIZESTBL_APPONT_NO")
    private String traditnBizestblAppontNo;

    // 전통업소주된음식
    @JsonProperty("TRADITN_BIZESTBL_CHIEF_FOOD_NM")
    private String traditnBizestblChiefFoodNm;

    // 홈페이지 URL
    @JsonProperty("HMPG_URL")
    private String hmpgURL;

    // 소재지우편번호
    @JsonProperty("REFINE_ZIP_CD")
    private String refineZipCD;
}
