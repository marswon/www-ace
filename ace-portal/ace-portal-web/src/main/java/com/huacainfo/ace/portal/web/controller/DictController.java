package com.huacainfo.ace.portal.web.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huacainfo.ace.common.model.PageParam;
import com.huacainfo.ace.common.model.view.Tree;
import com.huacainfo.ace.common.result.MessageResponse;
import com.huacainfo.ace.common.result.PageResult;
import com.huacainfo.ace.common.result.SingleResult;
import com.huacainfo.ace.common.tools.CommonKeys;
import com.huacainfo.ace.common.tools.CommonUtils;
import com.huacainfo.ace.common.tools.DictUtils;
import com.huacainfo.ace.portal.model.Dict;
import com.huacainfo.ace.portal.service.DictService;
import com.huacainfo.ace.portal.vo.DictVo;

@Controller
@RequestMapping("/dict")
public class DictController extends PortalBaseController {
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DictService dictService;
	/**
	 * 
	    * @Title:findDictList 
	    * @Description:  TODO(静态字典分页查询) 
	 		* @param:        @param condition
	 		* @param:        @param page
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       PageResult<DictVo>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:50:32
	 */
	@RequestMapping(value = "/findDictList.do")
	@ResponseBody
	public PageResult<DictVo> findDictList(Dict condition, PageParam page)
			throws Exception {
		PageResult<DictVo> rst = this.dictService.findDictList(condition,
				page.getStart(), page.getLimit(), page.getOrderBy());
		if (rst.getTotal() == 0) {
			rst.setTotal(page.getTotalRecord());
		}
	
		return rst;

	}
	/**
	 * 
	    * @Title:insertDict 
	    * @Description:  TODO(添加静态字典) 
	 		* @param:        @param jsons
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:50:57
	 */
	@RequestMapping(value = "/insertDict.do")
	@ResponseBody
	public MessageResponse insertDict(String jsons) throws Exception {
		Dict obj = JSON.parseObject(jsons, Dict.class);
		MessageResponse rst = this.dictService.insertDict(obj,
				this.getCurUserProp());
		return rst;
	}
	/**
	 * 
	    * @Title:updateDict 
	    * @Description:  TODO(更新静态字典) 
	 		* @param:        @param jsons
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:51:11
	 */
	@RequestMapping(value = "/updateDict.do")
	@ResponseBody
	public MessageResponse updateDict(String jsons) throws Exception {

		Dict obj = JSON.parseObject(jsons, Dict.class);
		return this.dictService.updateDict(obj, this.getCurUserProp());
	}
	/**
	 * 
	    * @Title:selectDictByPrimaryKey 
	    * @Description:  TODO(根据主键获取静态字典) 
	 		* @param:        @param id
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       SingleResult<Dict>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:51:24
	 */
	@RequestMapping(value = "/selectDictByPrimaryKey.do")
	@ResponseBody
	public SingleResult<Dict> selectDictByPrimaryKey(int id) throws Exception {
		return this.dictService.selectDictByPrimaryKey(id);
	}
	/**
	 * 
	    * @Title:deleteDictByDictId 
	    * @Description:  TODO(删除静态字典) 
	 		* @param:        @param jsons
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:51:40
	 */
	@RequestMapping(value = "/deleteDictByDictId.do")
	@ResponseBody
	public MessageResponse deleteDictByDictId(String jsons)
			throws Exception {
		JSONObject json = JSON.parseObject(jsons);
		int id = json.getInteger("id");
		return this.dictService.deleteDictByDictId(id, this.getCurUserProp());
	}
	/**
	 * 
	    * @Title:findListByCategoryId 
	    * @Description:  TODO(根据类别获取静态字典) 
	 		* @param:        @param categoryId
	 		* @param:        @param selected
	 		* @param:        @param request
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       List<Dict>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:51:53
	 */
	@RequestMapping(value = "/findListByCategoryId.do")
	@ResponseBody
	public List<Dict> findListByCategoryId(String categoryId, String selected,
			HttpServletRequest request) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = request.getParameter(key);
			params.put(key, value);
		}
		return this.dictService.findListByCategoryId(categoryId, selected,
				params);
	}
	/**
	 * 
	    * @Title:deploy 
	    * @Description:  TODO(发布静态字典) 
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:52:13
	 */
	@RequestMapping(value = "/deploy.do")
	@ResponseBody
	public MessageResponse deploy() throws Exception {
		ServletContext servletContext = this.getRequest().getSession()
				.getServletContext();
		String path = servletContext.getRealPath("/");
		String fileName = "";
		String syid="sys";
		List<Map<String, String>> list =this.dictService.selectSyidBydc();
		for(Map<String, String> e:list){
			syid=e.get("syid");
			fileName = "content/common/js/dict_"+syid+".js";
			Map<String, List<Map<String, Object>>> dictMap = dictService
					.flushJavaScriptFile(syid);
			servletContext.setAttribute(CommonKeys.dictAppKey+syid, dictMap);
			String dictJsonListString = DictUtils.toJsonString(dictMap,
					new String[] { "TABLE_NAME" });
			CommonUtils.writeStringToFile(path + fileName,
					preDealDictJSONString(dictJsonListString));
		}
		return new MessageResponse(1, "发布成功！");
	}
	/**
	 * 
	    * @Title:getDictTreeList 
	    * @Description:  TODO(获取字典树) 
	 		* @param:        @param id
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       List<Tree>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月17日 下午1:54:10
	 */
	@RequestMapping(value = "/getDictTreeList.do")
	@ResponseBody
	public List<Tree> getDictTreeList(String id)throws Exception {
		if(CommonUtils.isBlank(id)){
			id="0";
		}
		List<Tree> list=this.dictService.selectDictTreeList(id);
		return list;
	}
	private String preDealDictJSONString(String dictJsonListString) {
		dictJsonListString = dictJsonListString.replaceAll(" ", "");
		dictJsonListString = dictJsonListString.replaceAll("\n", "");
		dictJsonListString = dictJsonListString.replaceAll("\t", "");
		dictJsonListString = "var staticDictObject=" + dictJsonListString;
		return dictJsonListString;
	}
}
