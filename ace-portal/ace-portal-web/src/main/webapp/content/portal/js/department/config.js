var cfg = {};
cfg.grid_load_data_url = contextPath + '/department/findDepartmentList.do';
cfg.grid_add_data_url = contextPath + '/department/insertDepartment.do';
cfg.grid_edit_data_url = contextPath + '/department/updateDepartment.do';
cfg.grid_delete_data_url = contextPath + '/department/delDepartmentByPrimaryKey.do';
cfg.grid_selector= "#grid-table";
cfg.pager_selector= "#grid-pager";
//cfg.caption= "部门";
cfg.rowNum= default_page_list[0];
cfg.dataId= 'departmentId';
cfg.gridHeight=window.innerHeight-layoutTopHeight;
cfg.jgridEditWinWidth=750;
cfg.jgridAlertWidth=400;
cfg.jgrdInfoDialogWidth=500;
if(cfg.gridHeight<100){
	cfg.gridHeight=window.innerHeight-layoutTopHeight;
}