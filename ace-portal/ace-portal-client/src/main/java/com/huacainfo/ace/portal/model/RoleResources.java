package com.huacainfo.ace.portal.model;

import java.util.Date;

public class RoleResources implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4960498241543463486L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ROLE_RESOURCES.ROLE_ID
     *
     * @mbggenerated Fri Dec 14 11:16:28 CST 2012
     */
    private String roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ROLE_RESOURCES.RESOURCES_ID
     *
     * @mbggenerated Fri Dec 14 11:16:28 CST 2012
     */
    private String resourcesId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ROLE_RESOURCES.CREATE_TIME
     *
     * @mbggenerated Fri Dec 14 11:16:28 CST 2012
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ROLE_RESOURCES.ROLE_ID
     *
     * @return the value of ROLE_RESOURCES.ROLE_ID
     *
     * @mbggenerated Fri Dec 14 11:16:28 CST 2012
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ROLE_RESOURCES.ROLE_ID
     *
     * @param roleId the value for ROLE_RESOURCES.ROLE_ID
     *
     * @mbggenerated Fri Dec 14 11:16:28 CST 2012
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ROLE_RESOURCES.RESOURCES_ID
     *
     * @return the value of ROLE_RESOURCES.RESOURCES_ID
     *
     * @mbggenerated Fri Dec 14 11:16:28 CST 2012
     */
    public String getResourcesId() {
        return resourcesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ROLE_RESOURCES.RESOURCES_ID
     *
     * @param resourcesId the value for ROLE_RESOURCES.RESOURCES_ID
     *
     * @mbggenerated Fri Dec 14 11:16:28 CST 2012
     */
    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId == null ? null : resourcesId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ROLE_RESOURCES.CREATE_TIME
     *
     * @return the value of ROLE_RESOURCES.CREATE_TIME
     *
     * @mbggenerated Fri Dec 14 11:16:28 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ROLE_RESOURCES.CREATE_TIME
     *
     * @param createTime the value for ROLE_RESOURCES.CREATE_TIME
     *
     * @mbggenerated Fri Dec 14 11:16:28 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}