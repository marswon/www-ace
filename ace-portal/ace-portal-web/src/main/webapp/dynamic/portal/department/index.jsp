<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<title>deparment</title>
</head>
<jsp:include page="../../common/common.jsp" />
<style>
.layout-user {
	width: 60px;
	height: 20px;
	float: left;
	margin: 1px 1px 1px;
}
</style>
<body>
	<div class="page-content">
		<div class="widget-box" id="widget-box">
			<div class="widget-header">
				<h5 class="widget-title smaller">设置查询条件</h5>

				<div class="widget-toolbar"></div>
			</div>

			<div class="widget-body">
				<div class="widget-main padding-6">
					<form action="#" id="fm-search">
					地区： <input id="cc2" name="areaCode"
							class="easyui-combotree"
							data-options="url:'${pageContext.request.contextPath}/system/selectProvinceTreeList.do',method:'get',animate: true,
                lines:false,"
							style='width: 200px; line-height: 25px; height: 25px;'> <a
							href="javascript:clearAreaCode()">清除</a> 
							
						上级机构： <input id="cc1" name="parentDepartmentId"
							class="easyui-combotree"
							data-options="url:'${pageContext.request.contextPath}/system/selectDepartmentTreeList.do',method:'get',animate: true,
                lines:false,"
							style='width: 200px; line-height: 25px; height: 25px;'> <a
							href="javascript:clearQparams()">清除</a> 机构名称： <input
							name="departmentName" type="text" maxlength="20"
							style="width: 200px; height: 25px;" />
						<button class="btn btn-info" id="btn-search"
							authority="${pageContext.request.contextPath}/department/findDepartmentList.do">
							<i
								class="ace-icon fa fa-search  align-middle bigger-125 icon-on-right"></i>
						</button>

					</form>
					<div class="space10"></div>
					<div id="toolbar" class="toolbar">

						<button class="btn btn-info" id="btn-view-add"
							authority="${pageContext.request.contextPath}/department/insertDepartment.do">
							<i
								class="ace-icon fa fa-plus-square  align-middle bigger-125 icon-on-right"></i>
						</button>
						<button class="btn btn-info" id="btn-view-edit"
							authority="${pageContext.request.contextPath}/department/updateDepartment.do">
							<i
								class="ace-icon fa fa-edit  align-middle bigger-125 icon-on-right"></i>
						</button>
						
<!-- 						<button class="btn btn-purple" id="btn-view-ins" -->
<!-- 							authority="/kernel/memberInfo/insertMemberInfo.do"> -->
<!-- 							<i -->
<!-- 								class="ace-icon fa  fahicon-gavel  align-middle bigger-125 icon-on-right"></i> -->
<!-- 						</button> -->
						<button class="btn btn-warning" id="btn-view-del"
							authority="${pageContext.request.contextPath}/department/delDepartmentByPrimaryKey.do">
							<i
								class="ace-icon glyphicon  glyphicon-remove  align-middle bigger-125 icon-on-right"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="easyui-layout" id="cc" style="width: 100%; height: 300px;">



				<div data-options="region:'center',border:false,fit:true" id="easyui-center">
				<table id="grid-table"></table>

				<div id="grid-pager"></div>
			</div>
			<div id="cc-west" class="easyui-west"
				data-options="region:'west',split:true" title="我的树"
				style="width: 200px;">
				<ul id="tt" class="easyui-tree"
					data-options="
               url: '${pageContext.request.contextPath}/department/selectDepartmentTreeList.do',
                method: 'get',
                animate: true,
                lines:false,
                onContextMenu: function(e,node){
                    e.preventDefault();
                    $(this).tree('select',node.target);
                    autotreeq(node);
                    $('#mm').menu('show',{
                        left: e.pageX,
                        top: e.pageY
                    });
                }
            "></ul>

			</div>
			<!--  
		<div data-options="region:'south',split:true" style="height:50px;"></div>
        <div data-options="region:'east',split:true" title="East" style="width:100px;"></div>
        -->
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div onclick="treeappend()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="treeedit()" data-options="iconCls:'icon-edit'">编辑</div>
		<div onclick="treeremove()" data-options="iconCls:'icon-remove'">删除</div>
		<div class="menu-sep"></div>
		<div onclick="treereload()" data-options="iconCls:'icon-refresh'">刷新</div>
		<div onclick="expand()">展开</div>
		<div onclick="collapse()">收回</div>
	</div>
	

	<jsp:include page="../../common/footer-1.jsp" />
	<script
		src="${pageContext.request.contextPath}/content/portal/js/department/config.js?version=${cfg.version}"></script>
	<script
		src="${pageContext.request.contextPath}/content/portal/js/department/model.js?version=${cfg.version}"></script>
	<script
		src="${pageContext.request.contextPath}/content/portal/js/department/controller.js?version=${cfg.version}"></script>
	<script
		src="${pageContext.request.contextPath}/content/portal/js/department/view.js?version=${cfg.version}"></script>

	<jsp:include page="../../common/footer-2.jsp" />

	<script type="text/javascript">
		window.onresize = function() {
			setTimeout("autoResize()", 100);
			setTimeout("autoResize()",1000);
		}
		function autoResize() {
			jQuery('.panel-tool').find('a').on('click', function(e) {
				setTimeout("autoResize()", 1000);
			});
			var h = window.innerHeight - 130;
			$('#cc').layout('resize', {
				width : '100%',
				height : h
			});
			$('#cc').css("height", h);
			$(cfg.grid_selector).jqGrid('setGridHeight', h - 65);
			var display = $('#cc-west').css('display');
			console.log(display)
			if (display == 'none') {
				$(cfg.grid_selector).jqGrid('setGridWidth', $(".page-content").width()-26);
			} else {
				$(cfg.grid_selector).jqGrid('setGridWidth',
						$(".page-content").width() - 200);
			}
			console.log('autoResize:' + h);
			parent.autoWidth();
		}
		jQuery(function($) {
			jQuery('.layout-button-left').on('click', function(e) {
				setTimeout("autoResize()", 1000);
			});

		});
	</script>
	
	<div id="dialog-message" class="hide">
<!-- 		<select class="easyui-combogrid" -->
<!-- 			style="width: 585px; height: 30px; line-height: 30px;" -->
<!-- 			id="combogrid-tmp" -->
<%-- 			data-options="panelWidth: 585,idField: 'USER_ID',textField: 'NAME',url: '${sessionScope.portalContextPath}/system/selectUsers.do', --%>
<!-- 			mode:'remote',  -->
<!-- 			fitColumns:true, -->
<!-- 			method: 'get',columns: [[ -->
<!-- 			{field:'USER_ID',title:'用户编号',width:150}, -->
<!-- 			{field:'NAME',title:'姓名',width:100}, -->
<!-- 			{field:'MOBILE',title:'手机号',width:150,align:'right'}, -->
<!-- 			{field:'DEPARTMENT_NAME',title:'所属机构',width:250,align:'right'} -->
<%-- 			 ]]"></select> --%>

		<div style="height: 5px"></div>
<!-- 		<div> -->
<!-- 			<button class="btn btn-purple" id="btn-view-select-tmp" -->
<!-- 				authority="false"> -->
<!-- 				添加<i -->
<!-- 					class="ace-icon glyphicon  glyphicon-plus  align-middle bigger-125 icon-on-right"></i> -->
<!-- 			</button> -->
<!-- 			<button class="btn btn-purple" id="btn-view-remove-tmp" -->
<!-- 				authority="false"> -->
<!-- 				清除<i -->
<!-- 					class="ace-icon glyphicon  glyphicon-remove  align-middle bigger-125 icon-on-right"></i> -->
<!-- 			</button> -->
<!-- 		</div> -->
		<div style="height: 5px"></div>
		<div id="task-content-tmp" class="easyui-panel"
			style="padding: 5px; width: 585px; height: 200px"></div>

	<script type="text/javascript">
	jQuery(function($) {
		$( "#btn-view-select-tmp" ).on('click', function(e) {
			e.preventDefault();
			selectMobile();
		});
		$('#combogrid-tmp').combogrid({
			onSelect: function(index,row){
				selectMobile();
			}
		});
		$( "#btn-view-remove-tmp" ).on('click', function(e) {
			e.preventDefault();
			$('#task-content-tmp').html('');
		});
	});
	
	function selectMobile(){
		var html = new Array();
		var g = $('#combogrid-tmp').combogrid('grid');	// get datagrid object
		var r = g.datagrid('getSelected');	// get the selected row
		var isExit=false;
		if(r&&r.USER_ID){
			$.each($('user'),function(i,obj){
				if($(obj).attr("id")==r.USER_ID){
					alert("重复人员。");
					isExit=true;
					return;
				}
			});
			
			html.push('<div class="layout-user" >');
			html.push('<user id="'+r.USER_ID+'" class="badge badge-'+cssColor9[0]+'">');
			html.push(r.NAME);
			html.push('</user>');
			html.push('</div>');
			if(!isExit){
				if(r.DEPARTMENT_NAME&&r.DEPARTMENT_NAME!=''){
					if(confirm("已分配过的人员，确定要更改机构吗？")){
						$('#task-content-tmp').html($('#task-content-tmp').html()+html.join(''));
					}
				}else{
					$('#task-content-tmp').html($('#task-content-tmp').html()+html.join(''));
					
				}
				
			}
			
		}else{
			alert("请选择人员且编号不能为空。");
		}
	}
	</script>
	</div>
</body>
</html>