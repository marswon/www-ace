var cfg = {};
cfg.grid_load_data_url = contextPath + '/actFormCategory/findActFormCategoryList.do';
cfg.grid_add_data_url = contextPath + '/actFormCategory/insertActFormCategory.do';
cfg.grid_edit_data_url = contextPath + '/actFormCategory/updateActFormCategory.do';
cfg.grid_delete_data_url = contextPath + '/actFormCategory/deleteActFormCategoryByActFormCategoryId.do';
cfg.grid_selector= "#grid-table";
cfg.pager_selector= "#grid-pager";
//cfg.caption= "参数";
cfg.rowNum= default_page_list[0];
cfg.dataId= 'id';
cfg.gridHeight=window.innerHeight-layoutTopHeight;
cfg.jgridEditWinWidth=550;
cfg.jgridAlertWidth=400;
cfg.jgrdInfoDialogWidth=500;
if(cfg.gridHeight<100){
	cfg.gridHeight=250;
}