package com.it313.big.modules.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it313.big.modules.sys.dao.OrgDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it313.big.common.service.ServiceException;
import com.it313.big.common.tree.TreeService;
import com.it313.big.modules.sys.States;
import com.it313.big.modules.sys.entity.Org;
import com.it313.big.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class OrgService extends TreeService<OrgDao, Org> {

	private Object lock = new Object();
	
	@Transactional(readOnly = false)
	public Org update(Org org) {
		synchronized (lock) {
			Org old = dao.get(org.getId());
			//默认之前的fullName
			org.setFullName(old.getFullName());
			boolean nameChanged = false, parentChanged = false;
			if((old.getParent().getId() != null && !old.getParent().getId().equals(org.getParent().getId())) || 
				(org.getParent().getId() != null && !org.getParent().getId().equals(old.getParent().getId())))
				parentChanged = true;
			if(!org.getName().equals(old.getName()))
				nameChanged = true;
			if(nameChanged || parentChanged)
			{//父节点发生变化或组织机构名发生变更，全名需要更改，包括子孙节点
				String fullName = null;
				if(parentChanged)
				{
					//需要严谨的逻辑判断，父节点不能是自己
					assert(!org.getId().equals(org.getParent().getId()));
					
					if(!shiftLayer(org.getId(), org.getParent().getId()))
						return null;//父节点不能是自身的子节点

					if(org.getParent().getId() != null)
					{
						Org parent = dao.get(org.getParent().getId());
						fullName = parent.getFullName()+org.getName();
					}
					else
						fullName = org.getName();
				}
				else
				{
					if(org.getParent().getId()!=null){
						Org parent = dao.get(org.getParent().getId());
						fullName = parent.getFullName()+org.getName();
					}else{
						fullName = old.getParentOrgName() == null ? org.getName() : old.getParentOrgName()+org.getName();
					}
				}
				org.setFullName(fullName);
				
				Map<String, List<Org>> tree = getOrgTree();
				
				List<Org> children = updateChildrenFullName(tree, org, null);
				if(children!=null&&children.size()>0){
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("fullName", children.get(0).getFullName());
					params.put("children", children);
					dao.updateFullName(params);
				}
			}
			 dao.update(org);
			 return org;
		}
	}
	
	public Map<String, List<Org>> getOrgTree()
	{
		HashMap<String, List<Org>> tree = new HashMap<String, List<Org>>();
		List<Org> all = dao.findAllList();
		
		for(Org org : all)
		{
			List<Org> children = tree.get(org.getParent().getId());
			if(children == null)
			{
				children = new ArrayList<Org>();
				tree.put(org.getParent().getId(), children);
			}
			children.add(org);
		}
		
		return tree;
	}
	@Transactional(readOnly = false)
	public Org saveOrg(Org org) {
		synchronized (lock) {
			if(StringUtils.isNotBlank(org.getParentId()))
			{
				Org parent = dao.get(org.getParentId());
				if(parent != null)
				{
					org.setFullName(parent.getFullName()+org.getName());
				}
			}
			else
				org.setFullName(org.getName());
			addLast(org);
			save(org);
			return org;
		}
	}

	private List<Org> updateChildrenFullName(Map<String, List<Org>> tree, Org parent, List<Org> children)
	{
		if(children == null)
			children = new ArrayList<Org>();
		
		List<Org> list = tree.get(parent.getId());
		if(list != null)
		{
			for(Org org : list)
			{
				org.setFullName(parent.getFullName()+org.getName());
				updateChildrenFullName(tree, org, children);
			}
			children.addAll(list);
		}
		return children;
	}

	//BUG 1124 若一个组织机构的上级处于禁用状态，则该组织机构不允许被启用
	/*private void checkParentState(Org org, String state) {
		if(States.isActive(state) && org.getParent().getId() != null) {
			Org parent = orgDao.findById(org.getParent().getId());
			if(parent != null && !States.isActive(parent.getState()))
				throw new GermException(MessageCodes.E_PARENT_ORG_IS_INACTIVE, org.getName(), parent.getName());
		}
	}*/
	
	private List<Org> updateChildrenState(Map<String,List<Org>> tree,Org parentOrg,List<Org> rst,String state){
		if(rst == null){
			rst = new ArrayList<Org>();
		}
		List<Org> children = tree.get(parentOrg.getId());
		if(children != null){
			for(Org org : children){
				org.setState(state);
				updateChildrenState(tree, org,rst,state);
			}
			rst.addAll(children);
		}
		return rst;
	}


	public List<Org> findAll(){
		return UserUtils.getOrgList();
	}

	public List<Org> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOrgAllList();
		}else{
			return UserUtils.getOrgList();
		}
	}
	
	@Transactional(readOnly = false)
	public void save(Org org) {
		super.save(org);
		UserUtils.removeCache(UserUtils.CACHE_ORG_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(List<Org> offices) {
		/*for (Org office : offices) {
			super.delete(office);
		}*/
		//dao.deleteTree(baseLeft, baseRight);
		UserUtils.removeCache(UserUtils.CACHE_ORG_LIST);
	}
	
	@Transactional(readOnly = false)
	public void updateState(String id, String state) {
		Org org = super.get(id);
		checkParentState(org, state); //BUG 1124 若一个组织机构的上级处于禁用状态，则该组织机构不允许被启用
		org.setState(state);
		dao.update(org);
	}

	//BUG 若一个组织机构的上级处于禁用状态，则该组织机构不允许被启用
	private void checkParentState(Org org, String state) {
		if(States.isActive(state) && org.getParentId() != null) {
			Org parent = super.get(org.getParentId());
			if(parent != null && !States.isActive(parent.getState()))
				throw new ServiceException(parent.getName()+"处于禁用状态，"+org.getName()+"不允许被启用");
		}
	}

}
