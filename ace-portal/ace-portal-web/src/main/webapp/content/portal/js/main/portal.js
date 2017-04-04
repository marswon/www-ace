var buildMenu = function(menus) {
	var buildMenuHtml = function(menus) {
		var html = [];
		var num = 0;
		$
				.each(
						menus,
						function(i, menu) {
							html.push('<li class="">');
							html
									.push('<a class="dropdown-toggle" href="#"><i class="menu-icon '
											+ menu.icon
											+ '"></i><span class="menu-text">'
											+ menu.text + ' </span>');
							if (menu.leaf != true && menu.leaf != 'true') {
								html
										.push('<b class="arrow fa fa-angle-down"></b>');
							}
							html.push('</a>');
							var initSubMenu = function(menu) {
								if (menu.leaf != true && menu.leaf != 'true') {
									var childrens = menu.children, len = childrens.length;
									html.push('<ul class="submenu">');
									for (var i = 0; i < len; i++) {
										html.push('<li class="">');
										if (childrens[i].href) {
											html
													.push('<a  href="#" title="'
															+ childrens[i].text
															+ '" url="'
															// + contextPath
															+ childrens[i].href
															+ '" ><i class="menu-icon '
															+ childrens[i].icon
															+ '"></i>'
															+ childrens[i].text
															+ '</a><b class="arrow"></b>');
										} else {
											html
													.push('<a class="dropdown-toggle" href="#"><i class="menu-icon '
															+ childrens[i].icon
															+ '"></i>'
															+ childrens[i].text
															+ '<b class="arrow fa fa-angle-down"></b></a>');
										}
										initSubMenu(childrens[i]);
										html.push('</li>');

									}
									html.push('</ul>');
								}
							}
							initSubMenu(menu);
							html.push('</li>');
						});
		return html.join('');
	};
	var htmlFrame = buildMenuHtml(menus);
	$('#menu').empty().append(htmlFrame);
	$('#menu a[url]').bind('click', function() {
		var url = $(this).attr("url");
		$('#menu a[url]').parent('li').removeClass("active")
		$(this).parent('li').addClass("active");
		if (url) {
			if(url.indexOf("/")!=-1){
				//普通地址
				addPanel($(this).attr("title"), url, true)
			}else{
				//工作流
				var key=url.split('?')[0];
				var name=$(this).attr("title");
				addWorkflow(key, name)
			}
			
			// $("#mainFrame").attr("src",url);
		}
	});

}

$.ajax({
	url : contextPath + '/system/getTreeList.do?loadButton=false&icon=ace',
	type : 'POST',
	timeout : 30000,
	dataType : 'json',
	success : function(data) {
		buildMenu(data);
		//initWorkflowList();
		addPanel('首页', '/'+activeSyId + '/index.jsp', false);
		console.log( userProp.roleType);
		initSysPan();
	}
});
function addPanel(title, src, closable) {
	var screenHeight = window.innerHeight - 76;
	var iframe = '<iframe name="mainFrame" id="ifr" src="' + src
			+ '" width="100%" height="' + screenHeight
			+ 'px" frameborder="0"  scrolling="auto"></iframe>';
	var isExit = $('#tt').tabs('exists',title);
	if(!isExit){
		$('#tt').tabs('add', {
			title : title,
			content : iframe,
			closable : closable
		});
	}else{
		$('#tt').tabs('select',title);
	}
	
}
function removePanel() {
	var tab = $('#tt').tabs('getSelected');
	if (tab) {
		var index = $('#tt').tabs('getTabIndex', tab);
		$('#tt').tabs('close', index);
	}
}
function reloadGrid() {
	var tab = $('#tt').tabs('getSelected');
	var index = $('#tt').tabs('getTabIndex', tab);
	var ifr = frames[index-1];
	var win = ifr.window || ifr.contentWindow;
	win.reloadGrid();
}
jQuery(function($) {
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title: function(title) {
			var $title = this.options.title || '&nbsp;'
			if( ("title_html" in this.options) && this.options.title_html == true )
				title.html($title);
			else title.text($title);
		}
	}));
	
});
 

function initTaskList() {
	$.ajax({
				type : "post",
				url : contextPath+"/task/findListByUserId.do",
				data : {},
				beforeSend : function(XMLHttpRequest) {
				},
				success : function(rst, textStatus) {

					if (rst) {
						
						var html = new Array();
						html.push('<li class="dropdown-header">');
						html.push('<i class="ace-icon fa fa-check"></i>');
						html.push(rst.allRows);
						html.push(' 系统通知');
						html.push('</li>');
						for ( var i in rst.list) {
							var o = rst.list[i];

							html.push('<li>');
							html.push('<a href="javascript:addPanel(\''+o.title+'\',\''+o.url+'\',true)">');
							html.push('<div class="clearfix">');
							html.push('<span class="pull-left">'+o.title+'</span>');
							html.push('<span class="pull-right">'+o.createTime+'</span>');
							html.push('</div>');
							//html.push('<div class="progress progress-mini">');
							//html.push('<div style="width:65%" class="progress-bar"></div>');
							//html.push('</div>');
							html.push('</a>');
							html.push('</li>');

							console.log(rst.list[i]);
						}
						
						 $('#notice-task-list').html(html.join(''));
						 $('#notice-task-list-point').html(rst.allRows);
						 notification(rst.allRows);
					}
				},
				complete : function(XMLHttpRequest, textStatus) {

				},
				error : function() {

				}
			});
}
function notification(t){
	$('#notification').html('<i class="ace-icon fa fa-bell icon-animated-bell"></i><span class="badge badge-important">'+t+'</span>');	
}
function message(t){
	$('#message').html('<i class="ace-icon fa fa-envelope icon-animated-vertical"></i><span class="badge badge-success">'+t+'</span>');	
}
window.setInterval(autoHttp, 30000); 
function autoHttp(){
	console.log("===>autoHttp");
	//initWorkflowList();
	//initTaskList();
}
function modifyPasswd(){
	
	var dialog=$( "#dialog-message" ).removeClass('hide').dialog({
		resizable: false,
		modal: false,
		title: "密码修改",
		buttons: [
			{
				html: "<i class='ace-icon fa fa-check bigger-110'></i>&nbsp; 确定",
				"class" : "btn btn-info btn-xs",
				click: function() {
					if($('#password').val()==''){
						alert("请输入密码！");
						return;
					}
					if($('#password').val()!=$('#repassword').val()){
						alert("两次输入的密码不一致！");
						return;
					}
					if(confirm("确定要修改吗？")){
					$.ajax({
						type : "post",
						url : contextPath + "/system/updatePassword.do",
						data:{password:$('#password').val(),repassword:$('#repassword').val()},
						beforeSend : function(XMLHttpRequest) {
							
						},
						success : function(rst, textStatus) {
							if (rst.state) {
								alert(rst.errorMessage);
								dialog.dialog( "close" ); 
							}else{
								alert(rst.errorMessage);
							}
						},
						complete : function(XMLHttpRequest, textStatus) {
							
						},
						error : function() {
							
						}
					});
					}
				}
			}
			,
			{
				html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取消",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
}
function settings(){
	//addPanel('设置档案','',true);
}
function memberCenter(){
	addPanel('用户中心','/kernel/dynamic/service/memberCenter/index.jsp',true);
}
$(function(){
    
    $(".tabs-header").bind('contextmenu',function(e){
        e.preventDefault();
        $('#rcmenu-tabs').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
    });
    //关闭当前标签页
    $("#closecur").bind("click",function(){
        var tab = $('#tt').tabs('getSelected');
        var index = $('#tt').tabs('getTabIndex',tab);
        $('#tt').tabs('close',index);
    });
    //关闭所有标签页
    $("#closeall").bind("click",function(){
        var tablist = $('#tt').tabs('tabs');
        for(var i=tablist.length-1;i>=1;i--){
            $('#tt').tabs('close',i);
        }
    });
    //关闭非当前标签页（先关闭右侧，再关闭左侧）
    $("#closeother").bind("click",function(){
        var tablist = $('#tt').tabs('tabs');
        var tab = $('#tt').tabs('getSelected');
        var index = $('#tt').tabs('getTabIndex',tab);
        for(var i=tablist.length-1;i>index;i--){
        		$('#tt').tabs('close',i);  
        }
        var num = index-1;
        for(var i=num;i>0;i--){
            $('#tt').tabs('close',i);
        }
    });
   
    
    setTimeout("checkPwd()",10000);
});

function switchSys(syid) {
	$.ajax({
				type : "post",
				url : contextPath+"/system/switchSys.do",
				data : {syid:syid},
				beforeSend : function(XMLHttpRequest) {
					
				},
				success : function(rst, textStatus) {
					if (rst) {
						window.location.reload(true);
					}
				}
			});
}

function initSysPan(){
	var syshtml=[];
	syshtml.push('<button class="btn btn-success"><i class="ace-icon fa fa-signal"></i></button>\r\n');
	syshtml.push('<button class="btn btn-info"><i class="ace-icon fa fa-pencil"></i></button>\r\n');
	syshtml.push('<button class="btn btn-warning" id="fa-calendar-set"><i class="ace-icon fa fa-calendar"></i></button>\r\n');
	syshtml.push('<button class="btn btn-danger" id="fa-cloud-upload"><i class="ace-icon fa fa-cloud-upload"></i></button>');
	$.ajax({
		type : "post",
		url : contextPath+"/syCfg/selectSyCfgByUser.do",
		success : function(rst, textStatus) {
			$(rst.value).each(function(i,o){
				syshtml[i]='<button class="btn  '+o.colorCls+'" onclick="switchSys(\''+o.id+'\')" data-rel="tooltip" data-placement="top" title=" '+o.name+'"><i class="ace-icon '+o.iconCls+'"></i></button>\r\n';
			});
			$("#sidebar-shortcuts-large").html(syshtml.join(''));
			$('[data-rel=tooltip]').tooltip();
		}
	});
}
