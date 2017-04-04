<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<title>意见反馈</title>
</head>
<jsp:include page="../../common/common.jsp" />
<script type="text/javascript">


</script>
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
					
				类别：<input
							class="easyui-combobox" style="width: 200px" name="payType"
							data-options="
                    url:'${portalPath}/dict/findListByCategoryId.do?categoryId=69&selected=false',
                    method:'get',
                    valueField:'code',
                    textField:'name',
                    panelHeight:'auto'">

                     名称： <input name="name" type="text"
                                        							style="width: 200px;" />
						<button class="btn btn-info" id="btn-search"
							authority="${pageContext.request.contextPath}/feedback/findFeedbackList.do">
							 <i
								class="ace-icon fa fa-search  align-middle bigger-125 icon-on-right"></i>
						</button>

						
					</form>
					<div class="space10"></div>
					<div id="toolbar" class="toolbar">

						
						<button class="btn btn-info" id="btn-view-add"
							authority="${pageContext.request.contextPath}/feedback/insertFeedback.do">
							 <i
								class="ace-icon fa fa-plus-square  align-middle bigger-125 icon-on-right"></i>
						</button>
						<button class="btn btn-info" id="btn-view-edit"
							authority="${pageContext.request.contextPath}/feedback/updateFeedback.do">
							 <i
								class="ace-icon fa fa-edit  align-middle bigger-125 icon-on-right"></i>
						</button>
						<button class="btn btn-warning" id="btn-view-del"
							authority="${pageContext.request.contextPath}/feedback/deleteFeedbackByFeedbackId.do">
							 <i
								class="ace-icon glyphicon  glyphicon-remove  align-middle bigger-125 icon-on-right"></i>
						</button>
						
					</div>
				</div>
			</div>
		</div>

		<table id="grid-table"></table>

		<div id="grid-pager"></div>
		
		
	</div>
	<div id="dialog-message" class="hide">
        		<div id="uploader">
        			<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
        		</div>
    </div>
    <div id="dialog-message-file" class="hide">
        <div id="load" class="loading"></div>
    </div>

    <div id="dialog-message-view" class="hide">
<div class="profile-user-info profile-user-info-striped">
<div class="profile-info-row">
<div class="profile-info-name">
主键</div>
<div class="profile-info-value">
<span class="editable editable-click" id="id">
</span>
</div>
<div class="profile-info-name">
主题</div>
<div class="profile-info-value">
<span class="editable editable-click" id="title">
</span>
</div>
<div class="profile-info-name">
分类</div>
<div class="profile-info-value">
<span class="editable editable-click" id="category">
</span>
</div>
</div>
<div class="profile-info-row">
<div class="profile-info-name">
内容</div>
<div class="profile-info-value">
<span class="editable editable-click" id="docText">
</span>
</div>
<div class="profile-info-name">
状态</div>
<div class="profile-info-value">
<span class="editable editable-click" id="status">
</span>
</div>
<div class="profile-info-name">
创建人编号</div>
<div class="profile-info-value">
<span class="editable editable-click" id="createUserId">
</span>
</div>
</div>
<div class="profile-info-row">
<div class="profile-info-name">
创建人姓名</div>
<div class="profile-info-value">
<span class="editable editable-click" id="createUserName">
</span>
</div>
<div class="profile-info-name">
阅读量</div>
<div class="profile-info-value">
<span class="editable editable-click" id="reading">
</span>
</div>
<div class="profile-info-name">
入库日期</div>
<div class="profile-info-value">
<span class="editable editable-click" id="createDate">
</span>
</div>
</div>
<div class="profile-info-row">
<div class="profile-info-name">
最后更新人编号</div>
<div class="profile-info-value">
<span class="editable editable-click" id="lastModifyUserId">
</span>
</div>
<div class="profile-info-name">
最后更新人姓名</div>
<div class="profile-info-value">
<span class="editable editable-click" id="lastModifyUserName">
</span>
</div>
<div class="profile-info-name">
最后更新时间</div>
<div class="profile-info-value">
<span class="editable editable-click" id="lastModifyDate">
</span>
</div>
</div>
<div class="profile-info-row">
</div>
</div>

    </div>
	<jsp:include page="../../common/footer-1.jsp" />
	<script
		src="${pageContext.request.contextPath}/content/service/feedback/config.js?version=${cfg.version}"></script>
	<script
		src="${pageContext.request.contextPath}/content/service/feedback/model.js?version=${cfg.version}"></script>
	<script
		src="${pageContext.request.contextPath}/content/service/feedback/controller.js?version=${cfg.version}"></script>
	<script
		src="${pageContext.request.contextPath}/content/service/feedback/view.js?version=${cfg.version}"></script>
	<jsp:include page="../../common/footer-2.jsp" />
	<script type="text/javascript">
window.onresize = function () {
	console.log('autoWidthJqgrid');
	$(cfg.grid_selector).jqGrid('setGridWidth', $(".page-content").width());
	$(cfg.grid_selector).jqGrid('setGridHeight', window.innerHeight-layoutTopHeight);
	parent.autoWidth();
}
</script>
</body>
</html>