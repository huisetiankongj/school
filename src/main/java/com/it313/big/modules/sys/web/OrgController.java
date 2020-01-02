/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.it313.big.modules.sys.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it313.big.common.config.Global;
import com.it313.big.common.persistence.paginate.Paginate;
import com.it313.big.common.utils.StringUtils;
import com.it313.big.common.web.BaseController;
import com.it313.big.modules.sys.entity.Org;
import com.it313.big.modules.sys.entity.User;
import com.it313.big.modules.sys.service.OrgService;
import com.it313.big.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/org")
public class OrgController extends BaseController {

	@Autowired
	private OrgService orgService;
	
	@ModelAttribute("org")
	public Org get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return orgService.get(id);
		}else{
			return new Org();
		}
	}

	@RequiresPermissions("sys:org:view")
	@RequestMapping(value = {"index"})
	public String index() {
		return "modules/sys/orgList";
	}

	@RequiresPermissions("sys:org:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public Object list(@RequestBody Org org) {
		this.setPageParams(org.getPaginate()); // 核心分页代码 
		List<Org> list = orgService.findList(org);
		Paginate<Org> page = new Paginate<Org>(list, org.getPaginate().getMenuId());
		return page;
	}
	
	@RequiresPermissions("sys:org:view")
	@RequestMapping(value = "form")
	public String form(Org org, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(org.getParentId())){
			org.setParent(user.getOrg());
		}
		org.setParent(orgService.get(org.getParentId()));
		if (org.getArea()==null){
			org.setArea(user.getOrg().getArea());
		}
		model.addAttribute("org", org);
		return "modules/sys/orgForm";
	}
	
	@RequiresPermissions("sys:org:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(Org org, ModelMap modelMap) {
		if(Global.isDemoMode()){
			return setErrorMsg("演示模式，不允许操作！");
		}
		if (!beanValidator(modelMap, org)){
			return setErrorMsg((String)modelMap.get("message"));
		}
		orgService.saveOrg(org);
		return Boolean.TRUE;
	}
	
	@RequiresPermissions("sys:org:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(@RequestBody List<Org> orgs) {
		if(Global.isDemoMode()){
			return setErrorMsg("演示模式，不允许操作！");
		}
//		if (Org.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
			orgService.delete(orgs);
			return Boolean.TRUE;
//		}
	}
	
	@RequiresPermissions("sys:org:edit")
	@RequestMapping(value = "updateState")
	@ResponseBody
	public Object updateState(@RequestBody List<Org> orgs) {
		if(Global.isDemoMode()){
			return setErrorMsg("演示模式，不允许操作！");
		}
		return true;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, @RequestParam(required=false) Boolean isUseable, HttpServletResponse response) {
		System.out.println("=================");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Org> list = orgService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Org e = list.get(i);
			/*if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& ((isUseable !=null && isUseable == true) || Global.YES.equals(e.getUseable()))){*/
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				map.put("leftValue", e.getLeftValue());
				map.put("rightValue", e.getRightValue());
				map.put("layer", e.getLayer());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			/*}*/
		}
		return mapList;
	}
}
