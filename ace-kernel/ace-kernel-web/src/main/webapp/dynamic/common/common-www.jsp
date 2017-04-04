<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%
session.setAttribute("portalPath", "/portal");

%>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
	var portalPath = '${portalPath}';
</script>

<script type="text/javascript"
	src="/portal/content/common/js/base.js?version=${cfg.version}"></script>
<link rel="stylesheet"
	href="/portal/content/common/assets/css/bootstrap.min.css?version=${cfg.version}" />
<link rel="stylesheet"
	href="/portal/content/common/assets/css/font-awesome.min.css?version=${cfg.version}" />
<link rel="stylesheet"
	href="/portal/content/common/assets/css/jquery-ui.min.css?version=${cfg.version}" />
<link rel="stylesheet"
	href="/portal/content/common/assets/css/datepicker.css?version=${cfg.version}" />
<link rel="stylesheet"
	href="/portal/content/common/assets/css/ace-fonts.css?version=${cfg.version}" />
	
<link rel="stylesheet" href="/portal/content/common/assets/css/ace-ie8.min.css?version=${cfg.version}" />

	
<!--[if lte IE 9]>
			<link rel="stylesheet" href="${pageContext.request.contextPath}/content/common/assets/css/ace-part2.min.css?version=${cfg.version}" />
<![endif]-->
		
<link rel="stylesheet"
	href="/portal/content/common/assets/css/ace-skins.min.css?version=${cfg.version}" />
<link rel="stylesheet"
	href="/portal/content/common/assets/css/ace-rtl.min.css?version=${cfg.version}" />
	
<!--[if lte IE 9]>
		  <link rel="stylesheet" href="/portal/content/common/assets/css/ace-ie.min.css?version=${cfg.version}" />
<![endif]-->

       <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

	<!--[if lte IE 8]>
	<script src="/portal/content/common/assets/js/gz/html5shiv.js?version=${cfg.version}"></script>
	<script src="/portal/content/common/assets/js/gz/respond.min.js?version=${cfg.version}"></script>
	<![endif]-->		



<link rel="stylesheet" href="/portal/content/common/css/ace-ui-custom.css?version=${cfg.version}" />