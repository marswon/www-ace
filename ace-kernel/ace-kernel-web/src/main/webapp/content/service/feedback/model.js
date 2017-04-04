var _colNames =['主键','主题','分类','内容','状态','创建人编号','创建人姓名','阅读量','入库日期','最后更新人编号','最后更新人姓名','最后更新时间','操作'];
var _colModel = function() {
	return [
	{name : 'id',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'title',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'category',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'docText',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'status',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'createUserId',hidden : true,editable : false,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'createUserName',hidden : true,editable : false,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'reading',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'createDate',hidden : true,editable : false,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'lastModifyUserId',hidden : true,editable : false,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'lastModifyUserName',hidden : true,editable : false,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'lastModifyDate',hidden : true,editable : false,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},
	 {
                name: 'opt',
                width: 100,
                hidden:false,
                editable: false,
                renderer:function(value, cur){
                    return renderBtn(cur);
                }
            }
	];
}
function aceSwitch(cellvalue, options, cell) {
	console.log('aceSwitch');
	setTimeout(function() {
		$(cell).find('input[type=checkbox]').addClass(
				'ace ace-switch ace-switch-5').after(
				'<span class="lbl"></span>');
	}, 0);
}
// enable datepicker
function pickDate(cellvalue, options, cell) {
	setTimeout(function() {
		$(cell).find('input[type=text]').datepicker({
			format : 'yyyy-mm-dd',
			autoclose : true
		});
	}, 0);
}
function renderBtn(cur) {
	var id = $.jgrid.getAccessor(cur, 'id');
	var title = $.jgrid.getAccessor(cur, 'name');
	var html = [];
	html.push('<a target="_blank" href="');
	html.push('javascript:preview(\'' + id + '\',\'' + title + '\')');
	html.push('"');
	html.push('><span class="badge badge-info">查看</span></a>');
	return html.join(' ');
}