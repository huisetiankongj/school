package com.it313.elm.school.back.utils;

import com.it313.big.common.utils.CacheUtils;
import com.it313.big.common.utils.SpringContextHolder;
import com.it313.elm.school.back.entity.StandardSetting;
import com.it313.elm.school.back.service.StandardSettingService;

import java.util.List;

public class StandardSettingUtils {

    private static StandardSettingService standardSettingService = SpringContextHolder.getBean(StandardSettingService.class);
    public static final String STANDARD_SETTING_CACHE = "standard_setting_cache";
    public static final String STANDARD_SETTING_ID = "id_";
    public static final String STANDARD_SETTING_SUBJECT_CACHE = "standard_setting_subject_cache";
    public static final String STANDARD_SETTING_SUBJECT_ID = "id_";

    public static List<StandardSetting> getStandardSettings(String orgId){
        List<StandardSetting> standardSettings = (List<StandardSetting>)CacheUtils.get(STANDARD_SETTING_CACHE, STANDARD_SETTING_CACHE+orgId);
        if (standardSettings ==  null){
            standardSettings = standardSettingService.getByOrgId(orgId);
            if (standardSettings == null){
                return null;
            }
            // 插入缓存
            CacheUtils.put(STANDARD_SETTING_CACHE, STANDARD_SETTING_ID+orgId, standardSettings);
        }
        return standardSettings;
    }

    public static StandardSetting getStandardSetting(String orgId,String subjectId){
        StandardSetting standardSetting = (StandardSetting)CacheUtils.get(STANDARD_SETTING_SUBJECT_CACHE, STANDARD_SETTING_SUBJECT_ID+orgId+subjectId);
        if (standardSetting ==  null){
            List<StandardSetting> standardSettings = getStandardSettings(orgId);
            for(int i=0,len=standardSettings.size();i<len;i++){
                StandardSetting s = standardSettings.get(i);
                if(subjectId.equals(s.getSubjectId())){
                    standardSetting  = s;
                }
            }
            if (standardSetting == null){
                return null;
            }
            // 插入缓存
            CacheUtils.put(STANDARD_SETTING_SUBJECT_CACHE, STANDARD_SETTING_SUBJECT_ID+orgId+subjectId, standardSetting);
        }
        return standardSetting;
    }


    public static Object getSubjectAlias(String orgId,String subjectId,Object score){
        StandardSetting  setting = getStandardSetting(orgId,subjectId);
        try {
            String[] levelContrasts = setting.getLevelContrasts();
            double scoreD = Double.parseDouble(score+"");
            if(levelContrasts.length>0){
                for(int i=0,len=levelContrasts.length;i<len;i++){
                    String[] level = levelContrasts[i].split(":");
                    double d = Double.parseDouble(level[0]);
                    if(d<=scoreD){
                        return level[1];
                    }
                }
            }
        }catch (Exception e){
            return score;
        }
        return score;
    }

    public static void removeStandardByOrgId(String orgId){
        CacheUtils.put(STANDARD_SETTING_CACHE, STANDARD_SETTING_ID+orgId);
    }

    public static void removeStandardByOrgId(String orgId,String subjectId){
        CacheUtils.put(STANDARD_SETTING_SUBJECT_CACHE, STANDARD_SETTING_SUBJECT_ID+orgId+subjectId);
    }

}
