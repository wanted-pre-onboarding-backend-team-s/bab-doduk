package com.wanted.babdoduk.batch;


import com.wanted.babdoduk.batch.client.response.success.ClientRestaurant;
import com.wanted.babdoduk.common.domain.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "raw_restaurant")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class RawRestaurant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sigun_cd")
    private String sigunCd;

    @Column(name = "refine_wgs84_lat")
    private String lat;

    @Column(name = "manage_no")
    private String manageNo;

    @Column(name = "bsn_state_nm")
    private String bsnStateNm;

    @Column(name = "licensg_de")
    private String licensgDe;

    @Column(name = "licensg_cancl_de")
    private String licensgCanclDe;

    @Column(name = "bsn_state_div_cd")
    private String bsnStateDivCd;

    @Column(name = "unity_bsn_state_div_cd")
    private String unityBsnStateDivCd;

    @Column(name = "unity_bsn_state_nm")
    private String unityBsnStateNm;

    @Column(name = "clsbiz_de")
    private String clsbizDe;

    @Column(name = "suspnbiz_begin_de")
    private String suspnbizBeginDe;

    @Column(name = "suspnbiz_end_de")
    private String suspnbizEndDe;

    @Column(name = "reopenbiz_de")
    private String reopenbizDe;

    @Column(name = "locplc_faclt_telno")
    private String locplcFacltTelno;

    @Column(name = "locplc_ar_info")
    private String locplcArInfo;

    @Column(name = "bizplc_nm")
    private String bizplcNm;

    @Column(name = "bizcond_div_nm_info")
    private String bizcondDivNmInfo;

    @Column(name = "x_crdnt_vl")
    private String xCrdntVl;

    @Column(name = "y_crdnt_vl")
    private String yCrdntVl;

    @Column(name = "sanittn_bizcond_nm")
    private String sanittnBizcondNm;

    @Column(name = "male_enflpsn_cnt")
    private Long maleEnflpsnCnt;

    @Column(name = "female_enflpsn_cnt")
    private Long femaleEnflpsnCnt;

    @Column(name = "bsnsite_circumfr_div_nm")
    private String bsnsiteCircumfrDivNm;

    @Column(name = "grad_div_nm")
    private String gradDivNm;

    @Column(name = "grad_faclt_div_nm")
    private String gradFacltDivNm;

    @Column(name = "tot_emply_cnt")
    private Long totEmplyCnt;

    @Column(name = "headofc_emply_cnt")
    private Long headofcEmplyCnt;

    @Column(name = "factry_ofcrk_dut_emply_cnt")
    private Long factryOfcrkDutEmplyCnt;

    @Column(name = "factry_sale_dut_emply_cnt")
    private Long factrySaleDutEmplyCnt;

    @Column(name = "factry_prodctn_dut_emply_cnt")
    private Long factryProdctnDutEmplyCnt;

    @Column(name = "buldng_posesn_div_nm")
    private String buldngPosesnDivNm;

    @Column(name = "assurnc_amt")
    private Long assurncAmt;

    @Column(name = "mtrent_amt")
    private Long mtrentAmt;

    @Column(name = "multi_use_bizestbl_yn")
    private String multiUseBizestblYn;

    @Column(name = "faclt_tot_scale_info")
    private String facltTotScaleInfo;

    @Column(name = "traditn_bizestbl_appont_no")
    private String traditnBizestblAppontNo;

    @Column(name = "traditn_bizestbl_chief_food_nm")
    private String traditnBizestblChiefFoodNm;

    @Column(name = "hmpg_url")
    private String hmpgUrl;

    @Column(name = "refine_lotno_addr")
    private String refineLotnoAddr;

    @Column(name = "refine_roadnm_addr")
    private String refineRoadnmAddr;

    @Column(name = "refine_zip_cd")
    private String refineZipCd;

    @Column(name = "refine_wgs84_logt")
    private String refineWgs84Logt;

    @Column(name = "sigun_nm")
    private String sigunNm;

    public static RawRestaurant of(ClientRestaurant clientRestaurant) {
        return RawRestaurant.builder()
                .sigunCd(clientRestaurant.getSigunCode())
                .lat(clientRestaurant.getLat())
                .manageNo(clientRestaurant.getManageNo())
                .licensgDe(clientRestaurant.getLicensgDe())
                .licensgCanclDe(clientRestaurant.getLicensgCanclDe())
                .bsnStateDivCd(clientRestaurant.getBsnStateDivCD())
                .unityBsnStateDivCd(clientRestaurant.getUnityBsnStateDivCD())
                .unityBsnStateNm(clientRestaurant.getUnityBsnStateNm())
                .clsbizDe(clientRestaurant.getClsbizDe())
                .suspnbizBeginDe(clientRestaurant.getSuspnbizBeginDe())
                .suspnbizEndDe(clientRestaurant.getSuspnbizEndDe())
                .reopenbizDe(clientRestaurant.getReopenbizDe())
                .locplcFacltTelno(clientRestaurant.getLocplcFacltTelno())
                .locplcArInfo(clientRestaurant.getLocplcArInfo())
                .bizplcNm(clientRestaurant.getName())
                .bizcondDivNmInfo(clientRestaurant.getBizcondDivNmInfo())
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
                .refineLotnoAddr(clientRestaurant.getJibunAddr())
                .refineRoadnmAddr(clientRestaurant.getRoadAddr())
                .refineZipCd(clientRestaurant.getRefineZipCD())
                .refineWgs84Logt(clientRestaurant.getLon())
                .sigunNm(clientRestaurant.getSigunNm())
                .build();
    }

    public void update(ClientRestaurant restaurant) {
        this.sigunCd = restaurant.getSigunCode();
        this.lat = restaurant.getLat();
        this.manageNo = restaurant.getManageNo();
        this.licensgDe = restaurant.getLicensgDe();
        this.licensgCanclDe = restaurant.getLicensgCanclDe();
        this.bsnStateDivCd = restaurant.getBsnStateDivCD();
        this.unityBsnStateDivCd = restaurant.getUnityBsnStateDivCD();
        this.unityBsnStateNm = restaurant.getUnityBsnStateNm();
        this.bsnStateNm = restaurant.getBsnStateName();
        this.clsbizDe = restaurant.getClsbizDe();
        this.suspnbizBeginDe = restaurant.getSuspnbizBeginDe();
        this.suspnbizEndDe = restaurant.getSuspnbizEndDe();
        this.reopenbizDe = restaurant.getReopenbizDe();
        this.locplcFacltTelno = restaurant.getLocplcFacltTelno();
        this.locplcArInfo = restaurant.getLocplcArInfo();
        this.bizplcNm = restaurant.getName();
        this.bizcondDivNmInfo = restaurant.getBizcondDivNmInfo();
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
        this.refineLotnoAddr = restaurant.getJibunAddr();
        this.refineRoadnmAddr = restaurant.getRoadAddr();
        this.refineZipCd = restaurant.getRefineZipCD();
        this.refineWgs84Logt = restaurant.getLon();
        this.sigunNm = restaurant.getSigunNm();
    }
}
