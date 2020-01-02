package com.it313.big.modules.sys.utils;

import com.it313.big.common.utils.SpringContextHolder;
import com.it313.big.modules.sys.entity.Org;
import com.it313.big.modules.sys.service.OrgService;

public class OrgUtils {
    private static OrgService orgService = SpringContextHolder.getBean(OrgService.class);

    public static Org getSchoolOrg(String orgId){
        Org parentOrg = orgService.get(orgId);
        int xhNum = 0;
        while(!"2".equals(parentOrg.getType())&&xhNum<5){
            parentOrg = orgService.get(parentOrg.getParentId());
            xhNum++;
        }

        return parentOrg;
    }
}
