/**
 * @Title: NoticeVo.java
 * @Package org.platform.snail.edu.vo
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:snail
 * 
 * @author chenxiaoke
 * @date 2014年12月24日 下午3:44:43
 * @version V1.0
 */

package com.huacainfo.ace.portal.vo;

import com.huacainfo.ace.portal.model.Notice;

/**
 * @ClassName: NoticeVo
 * @Description: TODO
 * @author chenxiaoke
 * @date 2014年12月24日 下午3:44:43
 *
 */

public class NoticeVo extends Notice{
	private static final long serialVersionUID = 1L;
	    private String name;
	    
	    private String departmentName;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}
	    
}
