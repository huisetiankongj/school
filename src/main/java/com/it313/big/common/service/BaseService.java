/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.it313.big.common.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import com.it313.big.common.config.Global;
import com.it313.big.common.persistence.BaseEntity;
import com.it313.big.common.utils.StringUtils;
import com.it313.big.modules.sys.entity.Role;
import com.it313.big.modules.sys.entity.User;

/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class BaseService {

	//1：所有数据；2：所属及下级机构数据；3：所在公司数据；4：所有下级机构数据；5：所有上级机构数据；6：直接上级机构数据；7：直接下级机构数据；8：仅本人数据；9：按明细设置
	/**
	 * 所有组织机构
	 */
	public static final String ALL="1";
	/**
	 * 所属及下级机构数据
	 */
	public static final String BELONG_CHILDREN="2";
	/**
	 * 所在公司数据
	 */
	public static final String BELONG="3";
	/**
	 * 所有下级机构数据
	 */
	public static final String CHILDREN="4";
	/**
	 * 所有上级机构数据
	 */
	public static final String PARENT="5";
	/**
	 * 直接上级机构数据
	 */
	public static final String DIRECT_PARENT="6";
	/**
	 * 直接下级机构数据
	 */
	public static final String DIRECT_CHILDREN="7";
	/**
	 * 仅本人数据
	 */
	public static final String SELF="8";
	/**
	 * 按明细设置
	 */
	public static final String DETAIL="9";
	
	private static final String COMMON_TABLE_PREFIX = Global.getConfig("big.common.tablePrefix");

	private final static String QUERY = 
			"SELECT 1 FROM "+COMMON_TABLE_PREFIX+".SYS_ORG O WHERE O.ID=%s AND O.ID='%s'";
	private final static String QUERY_BELONG_CHILDREN =
			"SELECT 1 FROM "+COMMON_TABLE_PREFIX+".SYS_ORG O, "+COMMON_TABLE_PREFIX+".SYS_ORG B WHERE O.LEFT_VALUE >= B.LEFT_VALUE AND O.RIGHT_VALUE <= B.RIGHT_VALUE AND B.ID=%s AND O.ID=%s";
	private final static String QUERY_CHILDREN = 
			"SELECT 1 FROM "+COMMON_TABLE_PREFIX+".SYS_ORG O, "+COMMON_TABLE_PREFIX+".SYS_ORG B WHERE O.LEFT_VALUE > B.LEFT_VALUE AND O.RIGHT_VALUE < B.RIGHT_VALUE AND B.ID=%s AND O.ID=%s";
	private final static String QUERY_DIRECT_CHILDREN =
			QUERY_CHILDREN + " AND A.LAYER = B.LAYER+1";
	private final static String QUERY_PARENT =
			"SELECT 1 FROM "+COMMON_TABLE_PREFIX+".SYS_ORG A, "+COMMON_TABLE_PREFIX+".SYS_ORG B WHERE A.LEFT_VALUE < B.LEFT_VALUE AND A.RIGHT_VALUE > B.RIGHT_VALUE AND B.ID=%s AND O.ID=%s";
	private final static String QUERY_DIRECT_PARENT =
			QUERY_PARENT + " AND A.LAYER = B.LAYER-1";
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @param orgAlias 机构表别名，多个用“,”逗号隔开。
	 * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
	 * @return 标准连接条件对象
	 */
	public static String dataScopeFilter(User user, String orgAlias, String userAlias) {
		StringBuilder sqlString = new StringBuilder();
		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScope = Lists.newArrayList();
		// 超级管理员，跳过权限过滤
		if (!user.isAdmin()){
//			boolean isDataScopeAll = false;
			for (Role r : user.getRoleList()){
				for (String oa : StringUtils.split(orgAlias, ",")){
					if (!dataScope.contains(r.getDataScope()) && StringUtils.isNotBlank(oa)){
						if(BELONG.equals(r.getDataScope())) {
							//sqlString.append(" OR " + oa + ".id = '" + user.getOrg().getId() + "'");
							sqlString.append(" OR EXISTS (" + String.format(QUERY, oa, user.getOrg().getId())+")");
						}else if(BELONG_CHILDREN.equals(r.getDataScope())){
							sqlString.append(" OR EXISTS (" + String.format(QUERY_BELONG_CHILDREN,  "'"+user.getOrg().getId()+"'", oa)+")");
						} else if(CHILDREN.equals(r.getDataScope())) {
							sqlString.append(" OR EXISTS (" + String.format(QUERY_CHILDREN,  "'"+user.getOrg().getId()+"'", oa)+")");
						} else if(PARENT.equals(r.getDataScope())) {
							sqlString.append(" OR EXISTS (" + String.format(QUERY_PARENT,  "'"+user.getOrg().getId()+"'", oa/* + ".org_id "*/)+")");
						} else if(DIRECT_PARENT.equals(r.getDataScope())) {
							sqlString.append(" OR EXISTS (" + String.format(QUERY_DIRECT_PARENT, "'"+user.getOrg().getId()+"'", oa)+")");
						} else if(DIRECT_CHILDREN.equals(r.getDataScope())) {
							sqlString.append(" OR EXISTS (" + String.format(QUERY_DIRECT_CHILDREN, "'"+user.getOrg().getId()+"'", oa)+")");
						} else if (Role.DATA_SCOPE_SELF.equals(r.getDataScope())){
							if(StringUtils.isNotBlank(userAlias)) {
								sqlString.append(" OR EXISTS (SELECT 1 FROM "+COMMON_TABLE_PREFIX+".sys_user u123456, "+COMMON_TABLE_PREFIX+".sys_user_role ur123456");
								sqlString.append(" WHERE ur123456.user_id = u123456.id");
								sqlString.append(" AND u123456.id = '" + user.getId() + "'");
								sqlString.append(" AND ur123456.role_id =" + userAlias +")");//AND ur123456.role_id = a.id
								sqlString.append(" OR " + userAlias + " = '" + user.getId() + "'");
								sqlString.append(" OR a.create_by='" + user.getId() + "'");
							}
						} else if (Role.DATA_SCOPE_CUSTOM.equals(r.getDataScope())){
							sqlString.append(" OR EXISTS (SELECT 1 FROM "+COMMON_TABLE_PREFIX+".sys_role_org ro123456, "+COMMON_TABLE_PREFIX+".sys_org o123456");
							sqlString.append(" WHERE ro123456.org_id = o123456.id");
							sqlString.append(" AND ro123456.role_id = '" + r.getId() + "'");
							sqlString.append(" AND o123456.id=" + oa +")");
						} else if (ALL.equals(r.getDataScope())){
							//isDataScopeAll = true;
							sqlString = new StringBuilder();
							break;
						} 
						//else if (Role.DATA_SCOPE_SELF.equals(r.getDataScope())){
						dataScope.add(r.getDataScope());
					}
				}
			}
			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
				
			/*if (!isDataScopeAll){
				if (StringUtils.isNotBlank(userAlias)){
					for (String ua : StringUtils.split(userAlias, ",")){
						sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
					}
				}else {
					for (String oa : StringUtils.split(orgAlias, ",")){
						//sqlString.append(" OR " + oa + ".id  = " + user.getOrg().getId());
						sqlString.append(" OR " + oa + ".id IS NULL");
					}
				}
			}else{
				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
				sqlString = new StringBuilder();
			}*/
		}
		if (StringUtils.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}

	/**
	 * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
	 * @param entity 当前过滤的实体类
	 * @param sqlMapKey sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
	 * @param orgWheres org表条件，组成：部门表字段=业务表的部门字段
	 * @param userWheres user表条件，组成：用户表字段=业务表的用户字段
	 * @example
	 * 		dataScopeFilter(user, "dsf", "id=a.org_id", "id=a.create_by");
	 * 		dataScopeFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。
	 */
	public static void dataScopeFilter(BaseEntity<?> entity, String sqlMapKey, String orgWheres, String userWheres) {

		User user = entity.getCurrentUser();
		
		// 如果是超级管理员，则不过滤数据
		if (user.isAdmin()) {
			return;
		}

		// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所有下级机构数据；5：所有上级机构数据；6：直接上级机构数据；7：直接下级机构数据；8：仅本人数据；9：按明细设置）
		StringBuilder sqlString = new StringBuilder();
		
		// 获取到最大的数据权限范围
		String roleId = "";
		int dataScopeInteger = 8;
		for (Role r : user.getRoleList()){
			int ds = Integer.valueOf(r.getDataScope());
			if (ds == 9){
				roleId = r.getId();
				dataScopeInteger = ds;
				break;
			}else if (ds < dataScopeInteger){
				roleId = r.getId();
				dataScopeInteger = ds;
			}
		}
		String dataScopeString = String.valueOf(dataScopeInteger);
		
		// 生成部门权限SQL语句
		for (String where : StringUtils.split(orgWheres, ",")){
			if (Role.DATA_SCOPE_ORG_AND_CHILD.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_ORG");
				sqlString.append(" WHERE (id = '" + user.getOrg().getId() + "'");
				sqlString.append(" AND " + where +")");
			}else if (Role.DATA_SCOPE_ORG_AND_CHILD.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_ORG");
				sqlString.append(" WHERE (id = '" + user.getOrg().getId() + "'");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_ORG.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_ORG");
				sqlString.append(" WHERE id = '" + user.getOrg().getId() + "'");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_CUSTOM.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM sys_role_org ro123456, sys_org o123456");
				sqlString.append(" WHERE ro123456.org_id = o123456.id");
				sqlString.append(" AND ro123456.role_id = '" + roleId + "'");
				sqlString.append(" AND o123456." + where +")");
			}
		}
		// 生成个人权限SQL语句
		for (String where : StringUtils.split(userWheres, ",")){
			if (Role.DATA_SCOPE_SELF.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM sys_user");
				sqlString.append(" WHERE id='" + user.getId() + "'");
				sqlString.append(" AND " + where + ")");
			}
		}

//		System.out.println("dataScopeFilter: " + sqlString.toString());

		// 设置到自定义SQL对象
		entity.getSqlMap().put(sqlMapKey, sqlString.toString());
		
	}


	/**
	 * 加油站数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @param orgAlias 机构表别名，多个用“,”逗号隔开。
	 * @return 标准连接条件对象
	 */
	public static String dataScopeFilter(User user, String orgAlias) {
		StringBuilder sqlString = new StringBuilder();
		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScope = Lists.newArrayList();
		// 超级管理员，跳过权限过滤
		if (user != null && StringUtils.isNotBlank(user.getId()) && !user.isAdmin()){
			for (Role r : user.getRoleList()){
				for (String oa : StringUtils.split(orgAlias, ",")){
					if (!dataScope.contains(r.getDataScope()) && StringUtils.isNotBlank(oa)){
						if(BELONG.equals(r.getDataScope()) || Role.DATA_SCOPE_SELF.equals(r.getDataScope())) {
							sqlString.append(" OR EXISTS (" + String.format(QUERY, oa, user.getOrg().getId())+")");
						}else if(BELONG_CHILDREN.equals(r.getDataScope())){
							sqlString.append(" OR EXISTS (" + String.format(QUERY_BELONG_CHILDREN,  "'"+user.getOrg().getId()+"'", oa)+")");
						}  else if(CHILDREN.equals(r.getDataScope())) {
							sqlString.append(" OR EXISTS (" + String.format(QUERY_CHILDREN,  "'"+user.getOrg().getId()+"'", oa)+")");
						} else if(PARENT.equals(r.getDataScope())) {
							sqlString.append(" OR EXISTS (" + String.format(QUERY_PARENT,  "'"+user.getOrg().getId()+"'", oa)+")");
						} else if(DIRECT_PARENT.equals(r.getDataScope())) {
							sqlString.append(" OR EXISTS (" + String.format(QUERY_DIRECT_PARENT, "'"+user.getOrg().getId()+"'", oa)+")");
						} else if(DIRECT_CHILDREN.equals(r.getDataScope())) {
							sqlString.append(" OR EXISTS (" + String.format(QUERY_DIRECT_CHILDREN, "'"+user.getOrg().getId()+"'", oa)+")");
						} else if (Role.DATA_SCOPE_CUSTOM.equals(r.getDataScope())){
							sqlString.append(" OR EXISTS (SELECT 1 FROM "+COMMON_TABLE_PREFIX+".sys_role_org ro123456, BIG_COMMON_2.sys_org o123456");
							sqlString.append(" WHERE ro123456.org_id = o123456.id");
							sqlString.append(" AND ro123456.role_id = '" + r.getId() + "'");
							sqlString.append(" AND o123456.id=" + oa +")");
						} else if (ALL.equals(r.getDataScope())){
							sqlString = new StringBuilder();
							break;
						}
						dataScope.add(r.getDataScope());
					}
				}
			}
		}
		if (StringUtils.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}
}
