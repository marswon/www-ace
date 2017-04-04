package com.huacainfo.ace.portal.web.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huacainfo.ace.common.model.PageParam;
import com.huacainfo.ace.common.model.view.Tree;
import com.huacainfo.ace.common.result.ListResult;
import com.huacainfo.ace.common.result.MessageResponse;
import com.huacainfo.ace.common.result.PageResult;
import com.huacainfo.ace.common.result.SingleResult;
import com.huacainfo.ace.common.tools.CommonUtils;
import com.huacainfo.ace.portal.model.Department;
import com.huacainfo.ace.portal.service.DepartmentService;
import com.huacainfo.ace.portal.vo.DepartmentVo;

@Controller
@RequestMapping("/department")
public class DepartmentController extends PortalBaseController {
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DepartmentService departmentService;
	/**
	 * 
	    * @Title:findDepartmentList 
	    * @Description:  TODO(机构分页查询) 
	 		* @param:        @param condition
	 		* @param:        @param page
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       PageResult<DepartmentVo>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:37:41
	 */
	@RequestMapping(value = "/findDepartmentList.do")
	@ResponseBody
	public PageResult<DepartmentVo> findDepartmentList(Department condition,
			PageParam page) throws Exception {
		PageResult<DepartmentVo> rst;
		if(CommonUtils.isBlank(condition.getAreaCode())){
			condition.setAreaCode(this.getCurUserProp().getAreaCode());
		}
		if(CommonUtils.isBlank(condition.getParentDepartmentId())){
			condition.setParentDepartmentId(this.getCurUserProp().getCorpId());
		}
		rst = this.departmentService.findDepartmentList(condition,
				page.getStart(), page.getLimit(), page.getOrderBy());
		if (rst.getTotal() == 0) {
			rst.setTotal(page.getTotalRecord());
		}
		return rst;
	}
	/**
	 * 
	    * @Title:insertDepartment 
	    * @Description:  TODO(添加机构信息) 
	 		* @param:        @param jsons
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:38:02
	 */
	@RequestMapping(value = "/insertDepartment.do", method = RequestMethod.POST)
	@ResponseBody
	public MessageResponse insertDepartment(String jsons)
			throws Exception {
		Department obj = JSON.parseObject(jsons, Department.class);
		return this.departmentService.insertDepartment(obj,
				this.getCurUserProp());
	}
	/**
	 * 
	    * @Title:updateDepartment 
	    * @Description:  TODO(更新机构信息) 
	 		* @param:        @param jsons
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:39:19
	 */
	@RequestMapping(value = "/updateDepartment.do", method = RequestMethod.POST)
	@ResponseBody
	public MessageResponse updateDepartment(String jsons)
			throws Exception {
		Department obj = JSON.parseObject(jsons, Department.class);
		return this.departmentService.updateDepartment(obj,
				this.getCurUserProp());
	}
	/**
	 * 
	    * @Title:updateDepartmentStautsByPrimaryKey 
	    * @Description:  TODO(更新机构状态) 
	 		* @param:        @param jsons
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:39:32
	 */
	@RequestMapping(value = "/updateRegAudit.do", method = RequestMethod.POST)
	@ResponseBody
	public MessageResponse updateRegAudit(String departmentId, String status)
			throws Exception {

		return this.departmentService.updateDepartmentStautsByPrimaryKey(
				departmentId, status, this.getCurUserProp());
	}
	/**
	 * 
	    * @Title:selectDepartmentByPrimaryKey 
	    * @Description:  TODO(根据主键获取机构信息) 
	 		* @param:        @param departmentId
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       SingleResult<DepartmentVo>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:40:27
	 */
	@RequestMapping(value = "/selectDepartmentByPrimaryKey.do")
	@ResponseBody
	public SingleResult<DepartmentVo> selectDepartmentByPrimaryKey(
			String departmentId) throws Exception {
		return this.departmentService
				.selectDepartmentByPrimaryKey(departmentId);
	}
	/**
	 * 
	    * @Title:selectDepartmentTreeList 
	    * @Description:  TODO(获取机构树) 
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       List<Tree>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:40:45
	 */
	@RequestMapping(value = "/selectDepartmentTreeList.do")
	@ResponseBody
	public List<Tree> selectDepartmentTreeList(String id) throws Exception {
		List<Tree> list = this.departmentService.selectDepartmentTreeList(id);
		return list;
	}
	/**
	 * 
	    * @Title:delDepartmentByPrimaryKey 
	    * @Description:  TODO(删除机构信息) 
	 		* @param:        @param jsons
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:40:58
	 */
	@RequestMapping(value = "/delDepartmentByPrimaryKey.do", method = RequestMethod.POST)
	@ResponseBody
	public MessageResponse delDepartmentByPrimaryKey(String jsons)
			throws Exception {
		JSONObject json = JSON.parseObject(jsons);
		String departmentId = json.getString("id");
		return this.departmentService.delDepartmentByPrimaryKey(departmentId,
				this.getCurUserProp());
	}
	/**
	 * 
	    * @Title:selectUsersListByDepartmentId 
	    * @Description:  TODO(获取机构下的人员列表) 
	 		* @param:        @param departmentId
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       ListResult<Map<String,String>>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:41:55
	 */
	@RequestMapping(value = "/selectUsersListByDepartmentId.do")
	@ResponseBody
	public ListResult<Map<String, String>> selectUsersListByDepartmentId(
			String departmentId) throws Exception {
		return this.departmentService
				.selectUsersListByDepartmentId(departmentId);
	}
}
