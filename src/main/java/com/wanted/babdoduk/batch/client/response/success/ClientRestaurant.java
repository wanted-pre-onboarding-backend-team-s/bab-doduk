package com.wanted.babdoduk.batch.client.response.success;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ClientRestaurant {

    @JsonProperty("SIGUN_CD")
    private String sigunCode;

    @JsonProperty("REFINE_WGS84_LAT")
    private String lat;

    @JsonProperty("MANAGE_NO")
    private String manageNo;

    @JsonProperty("LICENSG_DE")
    private String licensgDe;

    @JsonProperty("LICENSG_CANCL_DE")
    private Object licensgCanclDe;

    @JsonProperty("BSN_STATE_DIV_CD")
    private String bsnStateDivCD;

    @JsonProperty("UNITY_BSN_STATE_DIV_CD")
    private String unityBsnStateDivCD;

    @JsonProperty("UNITY_BSN_STATE_NM")
    private String unityBsnStateNm;

    @JsonProperty("BSN_STATE_NM")
    private String bsnStateName;

    @JsonProperty("CLSBIZ_DE")
    private String clsbizDe;

    @JsonProperty("SUSPNBIZ_BEGIN_DE")
    private Object suspnbizBeginDe;

    @JsonProperty("SUSPNBIZ_END_DE")
    private Object suspnbizEndDe;

    @JsonProperty("REOPENBIZ_DE")
    private Object reopenbizDe;

    @JsonProperty("LOCPLC_FACLT_TELNO")
    private String locplcFacltTelno;

    @JsonProperty("LOCPLC_AR_INFO")
    private String locplcArInfo;

    @JsonProperty("BIZPLC_NM")
    private String name;

    @JsonProperty("BIZCOND_DIV_NM_INFO")
    private String bizcondDivNmInfo;

    @JsonProperty("X_CRDNT_VL")
    private String xCrdntVl;

    @JsonProperty("Y_CRDNT_VL")
    private String yCrdntVl;

    @JsonProperty("SANITTN_BIZCOND_NM")
    private String cuisineType;

    @JsonProperty("MALE_ENFLPSN_CNT")
    private Long maleEnflpsnCnt;

    @JsonProperty("FEMALE_ENFLPSN_CNT")
    private Long femaleEnflpsnCnt;

    @JsonProperty("BSNSITE_CIRCUMFR_DIV_NM")
    private String bsnsiteCircumfrDivNm;

    @JsonProperty("GRAD_DIV_NM")
    private Object gradDivNm;

    @JsonProperty("GRAD_FACLT_DIV_NM")
    private String gradFacltDivNm;

    @JsonProperty("TOT_EMPLY_CNT")
    private Long totEmplyCnt;

    @JsonProperty("HEADOFC_EMPLY_CNT")
    private Long headofcEmplyCnt;

    @JsonProperty("FACTRY_OFCRK_DUT_EMPLY_CNT")
    private Long factryOfcrkDutEmplyCnt;

    @JsonProperty("FACTRY_SALE_DUT_EMPLY_CNT")
    private Long factrySaleDutEmplyCnt;

    @JsonProperty("FACTRY_PRODCTN_DUT_EMPLY_CNT")
    private Long factryProdctnDutEmplyCnt;

    @JsonProperty("BULDNG_POSESN_DIV_NM")
    private String buldngPosesnDivNm;

    @JsonProperty("ASSURNC_AMT")
    private Long assurncAmt;

    @JsonProperty("MTRENT_AMT")
    private Long mtrentAmt;

    @JsonProperty("MULTI_USE_BIZESTBL_YN")
    private String multiUseBizestblYn;

    @JsonProperty("FACLT_TOT_SCALE_INFO")
    private String facltTotScaleInfo;

    @JsonProperty("TRADITN_BIZESTBL_APPONT_NO")
    private String traditnBizestblAppontNo;

    @JsonProperty("TRADITN_BIZESTBL_CHIEF_FOOD_NM")
    private String traditnBizestblChiefFoodNm;

    @JsonProperty("HMPG_URL")
    private String hmpgURL;

    @JsonProperty("REFINE_LOTNO_ADDR")
    private String jibunAddr;

    @JsonProperty("REFINE_ROADNM_ADDR")
    private String roadAddr;

    @JsonProperty("REFINE_ZIP_CD")
    private String refineZipCD;

    @JsonProperty("REFINE_WGS84_LOGT")
    private String lon;

    @JsonProperty("SIGUN_NM")
    private String sigunNm;
}
