package com.wanted.babdoduk.batch;

public class MockResponseTest {

    protected final String HEAD_RESULT_JSON = """
                        {
                            "RESULT": {
                                "CODE": "ERROR-333",
                                "MESSAGE": "요청위치 값의 타입이 유효하지 않습니다.요청위치 값은 정수를 입력하세요."
                            }
                        }""";

    protected final String SUCCESS_RESPONSE_JSON = """
            {
                "GENRESTRT": [
                    {
                        "head": [
                            {
                                "list_total_count": 442768
                            },
                            {
                                "RESULT": {
                                    "CODE": "INFO-000",
                                    "MESSAGE": "정상 처리되었습니다."
                                }
                            },
                            {
                                "api_version": "1.0"
                            }
                        ]
                    },
                    {
                        "row": [
                            {
                                "SIGUN_CD": "41480",
                                "REFINE_WGS84_LAT": "37.7319285386",
                                "MANAGE_NO": "4060000-101-2014-00404",
                                "LICENSG_DE": "20140911",
                                "LICENSG_CANCL_DE": null,
                                "BSN_STATE_DIV_CD": "02",
                                "UNITY_BSN_STATE_DIV_CD": "03",
                                "UNITY_BSN_STATE_NM": "폐업",
                                "BSN_STATE_NM": "폐업",
                                "CLSBIZ_DE": "20211209",
                                "SUSPNBIZ_BEGIN_DE": null,
                                "SUSPNBIZ_END_DE": null,
                                "REOPENBIZ_DE": null,
                                "LOCPLC_FACLT_TELNO": "031  9429942",
                                "LOCPLC_AR_INFO": "40.80",
                                "BIZPLC_NM": "김가네김밥(가람마을점)",
                                "BIZCOND_DIV_NM_INFO": "김밥(도시락)",
                                "X_CRDNT_VL": "178052.175264077    ",
                                "Y_CRDNT_VL": "469970.843050146    ",
                                "SANITTN_BIZCOND_NM": "김밥(도시락)",
                                "MALE_ENFLPSN_CNT": 0,
                                "FEMALE_ENFLPSN_CNT": 0,
                                "BSNSITE_CIRCUMFR_DIV_NM": null,
                                "GRAD_DIV_NM": null,
                                "GRAD_FACLT_DIV_NM": "상수도전용",
                                "TOT_EMPLY_CNT": 0,
                                "HEADOFC_EMPLY_CNT": 0,
                                "FACTRY_OFCRK_DUT_EMPLY_CNT": 0,
                                "FACTRY_SALE_DUT_EMPLY_CNT": 0,
                                "FACTRY_PRODCTN_DUT_EMPLY_CNT": 0,
                                "BULDNG_POSESN_DIV_NM": null,
                                "ASSURNC_AMT": 0,
                                "MTRENT_AMT": 0,
                                "MULTI_USE_BIZESTBL_YN": "N",
                                "FACLT_TOT_SCALE_INFO": "40.8",
                                "TRADITN_BIZESTBL_APPONT_NO": null,
                                "TRADITN_BIZESTBL_CHIEF_FOOD_NM": null,
                                "HMPG_URL": null,
                                "REFINE_LOTNO_ADDR": "경기도 파주시 와동동 1302-4 아이프라자 104호",
                                "REFINE_ROADNM_ADDR": "경기도 파주시 미래로602번길 31, 1층 104호 (와동동, 아이프라자)",
                                "REFINE_ZIP_CD": "10895",
                                "REFINE_WGS84_LOGT": "126.7518549307",
                                "SIGUN_NM": "파주시"
                            }
                        ]
                    }
                ]
            }
            """;
}
