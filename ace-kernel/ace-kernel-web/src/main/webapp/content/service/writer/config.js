var cfg = {};
cfg.grid_load_data_url = contextPath + '/writer/findWriterList.do';
cfg.grid_add_data_url = contextPath + '/writer/insertWriter.do';
cfg.grid_edit_data_url = contextPath + '/writer/updateWriter.do';
cfg.grid_delete_data_url = contextPath + '/writer/deleteWriterByWriterId.do';
cfg.grid_selector= "#grid-table";
cfg.pager_selector= "#grid-pager";
//cfg.caption= "参数";
cfg.rowNum= default_page_list[0];
cfg.dataId= 'id';
cfg.gridHeight=window.innerHeight-layoutTopHeight;
cfg.jgridEditWinWidth=window.innerWidth*0.85;
cfg.jgridAlertWidth=400;
cfg.jgrdInfoDialogWidth=500;
if(cfg.gridHeight<100){
	cfg.gridHeight=250;
}