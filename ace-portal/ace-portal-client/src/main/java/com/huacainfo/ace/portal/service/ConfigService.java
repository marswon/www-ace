package com.huacainfo.ace.portal.service;

import com.huacainfo.ace.common.model.UserProp;
import com.huacainfo.ace.common.result.MessageResponse;
import com.huacainfo.ace.common.result.PageResult;
import com.huacainfo.ace.common.result.SingleResult;
import com.huacainfo.ace.portal.model.Config;
import com.huacainfo.ace.portal.vo.ConfigQVo;
import com.huacainfo.ace.portal.vo.ConfigVo;

public interface ConfigService {
	/**
	 * 
	    * @Title:findConfigList 
	    * @Description:  TODO(系统参数分页查询) 
	 		* @param:        @param condition
	 		* @param:        @param start
	 		* @param:        @param limit
	 		* @param:        @param orderBy
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       PageResult<ConfigVo>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月16日 下午3:14:27
	 */
	public abstract PageResult<ConfigVo> findConfigList(ConfigQVo condition,
			int start, int limit, String orderBy) throws Exception;
	/**
	 * 
	    * @Title:insertConfig 
	    * @Description:  TODO(保存系统参数) 
	 		* @param:        @param config
	 		* @param:        @param userProp
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月16日 下午3:14:45
	 */

	public abstract MessageResponse insertConfig(Config config,
			UserProp userProp) throws Exception;
	/**
	 * 
	    * @Title:updateConfig 
	    * @Description:  TODO(更新系统参数) 
	 		* @param:        @param config
	 		* @param:        @param userProp
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月16日 下午3:33:15
	 */

	public abstract MessageResponse updateConfig(Config config,
			UserProp userProp) throws Exception;
	/**
	 * 
	    * @Title:selectConfigByPrimaryKey 
	    * @Description:  TODO(获取一条系统参数数据) 
	 		* @param:        @param id
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       SingleResult<Config>    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月16日 下午3:33:49
	 */

	public abstract SingleResult<Config> selectConfigByPrimaryKey(String id)
			throws Exception;
	/**
	 * 
	    * @Title:deleteConfigByConfigId 
	    * @Description:  TODO(删除系统参数) 
	 		* @param:        @param id
	 		* @param:        @param userProp
	 		* @param:        @return
	 		* @param:        @throws Exception    
	 		* @return:       MessageResponse    
	 		* @throws   
	    * @author: chenxiaoke 
	    * @version: 2016年11月16日 下午3:34:10
	 */
	public abstract MessageResponse deleteConfigByConfigId(String id,
			UserProp userProp) throws Exception;
	
	public MessageResponse insertDeptConfig(String deptId);

}
