package com.winxuan.ec.util;

import java.util.HashMap;
import java.util.Map;

/**
 * MC分类对应的 商品分类
 * @author heshuai
 *
 */
public class McCodeCategory {
    
	
	public static final String ADUCATION_AUDIOVISUAL = "教育音像";
	public static final String MUSIC = "音乐";
	public static final String TV = "影视";
	public static final String GAME = "游戏";
	
    private static Map<String,String> map = new HashMap<String,String>();
	
    /**
     * 
     * @param mcCode
     * @return
     */
	public static String getCategoryName(String mcCode){
           McCodeCategory.setMap();
           McCodeCategory.setMap1();
           return map.get(mcCode);
	}
	
	private  static void  setMap1(){
           map.put("MC310107",ADUCATION_AUDIOVISUAL);
           map.put("MC310108",ADUCATION_AUDIOVISUAL);
           map.put("MC310109",ADUCATION_AUDIOVISUAL);
           map.put("MC310110",ADUCATION_AUDIOVISUAL);
           map.put("MC310111",ADUCATION_AUDIOVISUAL);
           map.put("MC310112",ADUCATION_AUDIOVISUAL);
           map.put("MC310113",ADUCATION_AUDIOVISUAL);
           map.put("MC310114",ADUCATION_AUDIOVISUAL);
           map.put("MC310115",ADUCATION_AUDIOVISUAL);
           map.put("MC310116",ADUCATION_AUDIOVISUAL);
           map.put("MC310117",ADUCATION_AUDIOVISUAL);
           map.put("MC310118",ADUCATION_AUDIOVISUAL);
           map.put("MC310119",ADUCATION_AUDIOVISUAL);
           map.put("MC310120",ADUCATION_AUDIOVISUAL);
           map.put("MC310121",ADUCATION_AUDIOVISUAL);
           map.put("MC310122",ADUCATION_AUDIOVISUAL);
           map.put("MC310123",ADUCATION_AUDIOVISUAL);
           map.put("MC310124",ADUCATION_AUDIOVISUAL);
           map.put("MC310125",ADUCATION_AUDIOVISUAL);
           map.put("MC310126",ADUCATION_AUDIOVISUAL);
           map.put("MC310201",ADUCATION_AUDIOVISUAL);
           map.put("MC310202",ADUCATION_AUDIOVISUAL);
           map.put("MC310203",ADUCATION_AUDIOVISUAL);
           map.put("MC310204",ADUCATION_AUDIOVISUAL);
           map.put("MC310205",ADUCATION_AUDIOVISUAL);
           map.put("MC310206",ADUCATION_AUDIOVISUAL);
           map.put("MC310207",ADUCATION_AUDIOVISUAL);
           map.put("MC310208",ADUCATION_AUDIOVISUAL);
           map.put("MC310209",ADUCATION_AUDIOVISUAL);
           map.put("MC310210",ADUCATION_AUDIOVISUAL);
           map.put("MC310211",ADUCATION_AUDIOVISUAL);
           map.put("MC310212",ADUCATION_AUDIOVISUAL);
           map.put("MC310213",ADUCATION_AUDIOVISUAL);
           map.put("MC310214",ADUCATION_AUDIOVISUAL);
           map.put("MC310215",ADUCATION_AUDIOVISUAL);
           map.put("MC310216",ADUCATION_AUDIOVISUAL);
           map.put("MC310217",ADUCATION_AUDIOVISUAL);
           map.put("MC310218",ADUCATION_AUDIOVISUAL);
           map.put("MC310219",ADUCATION_AUDIOVISUAL);
           map.put("MC310220",ADUCATION_AUDIOVISUAL);
           map.put("MC310221",ADUCATION_AUDIOVISUAL);
           map.put("MC310222",ADUCATION_AUDIOVISUAL);
           map.put("MC310223",ADUCATION_AUDIOVISUAL);
           map.put("MC310224",ADUCATION_AUDIOVISUAL);
           map.put("MC310301",ADUCATION_AUDIOVISUAL);
           map.put("MC310302",ADUCATION_AUDIOVISUAL);
           map.put("MC310303",ADUCATION_AUDIOVISUAL);
           map.put("MC310304",ADUCATION_AUDIOVISUAL);
           map.put("MC310305",ADUCATION_AUDIOVISUAL);
           map.put("MC310306",ADUCATION_AUDIOVISUAL);
           map.put("MC310307",ADUCATION_AUDIOVISUAL);
           map.put("MC310308",ADUCATION_AUDIOVISUAL);
           map.put("MC310309",ADUCATION_AUDIOVISUAL);
           map.put("MC310310",ADUCATION_AUDIOVISUAL);
           map.put("MC310311",ADUCATION_AUDIOVISUAL);
           map.put("MC310312",ADUCATION_AUDIOVISUAL);
           map.put("MC310313",ADUCATION_AUDIOVISUAL);
           map.put("MC310314",ADUCATION_AUDIOVISUAL);
           map.put("MC310315",ADUCATION_AUDIOVISUAL);
           map.put("MC310316",ADUCATION_AUDIOVISUAL);
           map.put("MC310317",ADUCATION_AUDIOVISUAL);
           map.put("MC310318",ADUCATION_AUDIOVISUAL);
           map.put("MC310319",ADUCATION_AUDIOVISUAL);
           map.put("MC310320",ADUCATION_AUDIOVISUAL);
           map.put("MC310321",ADUCATION_AUDIOVISUAL);
           map.put("MC310322",ADUCATION_AUDIOVISUAL);
           map.put("MC310323",ADUCATION_AUDIOVISUAL);
           map.put("MC310324",ADUCATION_AUDIOVISUAL);
           map.put("MC310325",ADUCATION_AUDIOVISUAL);
           map.put("MC310326",ADUCATION_AUDIOVISUAL);
           map.put("MC310327",ADUCATION_AUDIOVISUAL);
           map.put("MC310401",ADUCATION_AUDIOVISUAL);
           map.put("MC310402",ADUCATION_AUDIOVISUAL);
           map.put("MC310403",ADUCATION_AUDIOVISUAL);
           map.put("MC310404",ADUCATION_AUDIOVISUAL);
           map.put("MC310405",ADUCATION_AUDIOVISUAL);
           map.put("MC310406",ADUCATION_AUDIOVISUAL);
           map.put("MC310407",ADUCATION_AUDIOVISUAL);
           map.put("MC310408",ADUCATION_AUDIOVISUAL);
           map.put("MC310409",ADUCATION_AUDIOVISUAL);
           map.put("MC310410",ADUCATION_AUDIOVISUAL);
           map.put("MC310411",ADUCATION_AUDIOVISUAL);
           map.put("MC310412",ADUCATION_AUDIOVISUAL);
           map.put("MC310413",ADUCATION_AUDIOVISUAL);
           map.put("MC310414",ADUCATION_AUDIOVISUAL);
           map.put("MC310415",ADUCATION_AUDIOVISUAL);
           map.put("MC310416",ADUCATION_AUDIOVISUAL);
           map.put("MC310417",ADUCATION_AUDIOVISUAL);
           map.put("MC310418",ADUCATION_AUDIOVISUAL);
           map.put("MC310419",ADUCATION_AUDIOVISUAL);
           map.put("MC310420",ADUCATION_AUDIOVISUAL);
           map.put("MC310421",ADUCATION_AUDIOVISUAL);
           map.put("MC310422",ADUCATION_AUDIOVISUAL);
           map.put("MC310423",ADUCATION_AUDIOVISUAL);
           map.put("MC310424",ADUCATION_AUDIOVISUAL);
           map.put("MC310425",ADUCATION_AUDIOVISUAL);
           map.put("MC310426",ADUCATION_AUDIOVISUAL);
	}
	
	private  static void  setMap(){
          map.put("MC320101",MUSIC);
          map.put("MC320102",MUSIC);
          map.put("MC320103",MUSIC);
          map.put("MC320104",MUSIC);
          map.put("MC320105",MUSIC);
          map.put("MC320106",MUSIC);
          map.put("MC320107",MUSIC);
          map.put("MC320108",MUSIC);
          map.put("MC320109",MUSIC);
          map.put("MC320110",MUSIC);
          map.put("MC320111",MUSIC);
          map.put("MC320112",MUSIC);
          map.put("MC320113",MUSIC);
          map.put("MC320114",MUSIC);
          map.put("MC320115",MUSIC);
          map.put("MC320116",MUSIC);
          map.put("MC320117",MUSIC);
          map.put("MC320118",MUSIC);
          map.put("MC320119",MUSIC);
          map.put("MC320201",MUSIC);
          map.put("MC320202",MUSIC);
          map.put("MC320203",MUSIC);
          map.put("MC320204",MUSIC);
          map.put("MC320205",MUSIC);
          map.put("MC320206",MUSIC);
          map.put("MC320207",MUSIC);
          map.put("MC320208",MUSIC);
          map.put("MC320209",MUSIC);
          map.put("MC320210",MUSIC);
          map.put("MC320211",MUSIC);
          map.put("MC320212",MUSIC);
          map.put("MC320213",MUSIC);
          map.put("MC320214",MUSIC);
          map.put("MC320215",MUSIC);
          map.put("MC320216",MUSIC);
          map.put("MC320217",MUSIC);
          map.put("MC320218",MUSIC);
          map.put("MC320219",MUSIC);
          map.put("MC320220",MUSIC);
          map.put("MC320221",MUSIC);
          map.put("MC320222",MUSIC);
          map.put("MC320223",MUSIC);
          map.put("MC320224",MUSIC);
          map.put("MC320301",MUSIC);
          map.put("MC320302",MUSIC);
          map.put("MC320303",MUSIC);
          map.put("MC320304",TV);
          map.put("MC320305",TV);
          map.put("MC320306", ADUCATION_AUDIOVISUAL);
          map.put("MC320307",TV);
          map.put("MC320308",TV);
          map.put("MC320309",TV);
          map.put("MC320310",MUSIC);
          map.put("MC320311",MUSIC);
          map.put("MC320312",TV);
          map.put("MC320313",ADUCATION_AUDIOVISUAL);
          map.put("MC320314",MUSIC);
          map.put("MC320315",TV);
          map.put("MC320401",TV);
          map.put("MC320402",TV);
          map.put("MC320403",TV);
          map.put("MC320404",TV);
          map.put("MC320405",TV);
          map.put("MC320406",ADUCATION_AUDIOVISUAL);
          map.put("MC320407",TV);
          map.put("MC320408",TV);
          map.put("MC320409",TV);
          map.put("MC320410",MUSIC);
          map.put("MC320411",MUSIC);
          map.put("MC320412",TV);
          map.put("MC320413",TV);
          map.put("MC320414",MUSIC);
          map.put("MC320415",TV);
          map.put("MC330101",GAME);
          map.put("MC330102",GAME);
          map.put("MC330103",GAME);
          map.put("MC330104",GAME);
          map.put("MC330105",GAME);
          map.put("MC330106",GAME);
          map.put("MC330107",GAME);
          map.put("MC330108",GAME);
          map.put("MC330109",GAME);
          map.put("MC330110",GAME);
          map.put("MC330111",GAME);
          map.put("MC330112",GAME);
          map.put("MC330113",GAME);
          map.put("MC330114",GAME);
          map.put("MC310101",ADUCATION_AUDIOVISUAL);
          map.put("MC310102",ADUCATION_AUDIOVISUAL);
          map.put("MC310103",ADUCATION_AUDIOVISUAL);
          map.put("MC310104",ADUCATION_AUDIOVISUAL);
          map.put("MC310105",ADUCATION_AUDIOVISUAL);
          map.put("MC310106",ADUCATION_AUDIOVISUAL);
	}
	
}
