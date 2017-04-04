var editor;
jQuery(function($) {
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title : function(title) {
			var $title = this.options.title || '&nbsp;'
			if (("title_html" in this.options)
					&& this.options.title_html == true)
				title.html($title);
			else
				title.text($title);
		}
	}));
	$('#btn-search').on('click', function() {
		$('#fm-search').ajaxForm({
			beforeSubmit : function(formData, jqForm, options) {
				var params = {};
				$.each(formData, function(n, obj) {
					params[obj.name] = obj.value;
				});
				$.extend(params, {
					time : new Date()
				});
				// console.log(params);
				jQuery(cfg.grid_selector).jqGrid('setGridParam', {
					page : 1,
					postData : params
				}).trigger("reloadGrid");
				return false;
			}
		});
	});

	$('#btn-view-add').on(
			'click',
			function() {
				jQuery(cfg.grid_selector).jqGrid(
						'editGridRow',
						'new',
						{
							closeAfterAdd : true,
							recreateForm : true,
							viewPagerButtons : false,
							beforeShowForm : function(e) {
								var form = $(e[0]);
								form.closest('.ui-jqdialog').find(
										'.ui-jqdialog-titlebar').wrapInner(
										'<div class="widget-header" />')
								style_edit_form(form);
								editor=CKEDITOR.replace('docText');
                                						editor.on( 'change', function( event ) {
                                                            var data = this.getData();//内容

                                                            $("textarea[name=docText]").val(data);

                                                        });
							}
						})

			});
	$('#btn-view-edit').on(
			'click',
			function() {
				var gr = jQuery(cfg.grid_selector).jqGrid('getGridParam',
						'selrow');
				if (!gr) {
					$.jgrid.info_dialog($.jgrid.nav.alertcap,
							$.jgrid.nav.alerttext)
				}
				jQuery(cfg.grid_selector).jqGrid(
						'editGridRow',
						gr,
						{
							closeAfterAdd : true,
							recreateForm : true,
							viewPagerButtons : true,
							beforeShowForm : function(e) {
								var form = $(e[0]);
								form.closest('.ui-jqdialog').find(
										'.ui-jqdialog-titlebar').wrapInner(
										'<div class="widget-header" />')
								style_edit_form(form);
								editor=CKEDITOR.replace('docText');
                                                        						editor.on( 'change', function( event ) {
                                                                                    var data = this.getData();//内容
                                                                                    $("textarea[name=docText]").val(data);
                                                                                });
                                    var gr = jQuery(cfg.grid_selector).jqGrid('getGridParam',
                                    						'selrow');
                                    var gd=jQuery(cfg.grid_selector).jqGrid('getRowData',gr);
                                    loadView(gd.id);


							}
						})

			});
    $('#btn-view-del').on(
			'click',
			function() {
				
				var gr = jQuery(cfg.grid_selector).jqGrid('getGridParam',
						'selrow');
				if (!gr) {
					$.jgrid.info_dialog($.jgrid.nav.alertcap,
							$.jgrid.nav.alerttext);
					return;
				}
				jQuery(cfg.grid_selector).jqGrid(
						'delGridRow',
						gr,
						{
							beforeShowForm : function(e) {
								var form = $(e[0]);
								form.closest('.ui-jqdialog').find(
										'.ui-jqdialog-titlebar').wrapInner(
										'<div class="widget-header" />')
								style_edit_form(form);
							}
						})
			});
});

function preview(id,title){
        var dialog = $( "#dialog-message-view" ).removeClass('hide').dialog({
			modal: false,
			width:800,
			title: "<div class='widget-header widget-header-small'><div class='widget-header-pd'>"+title+"</div></div>",
			title_html: true,
			buttons: [

				{
					html: "<i class='ace-icon fa fa-check bigger-110'></i>&nbsp; 确定",
					"class" : "btn btn-info btn-xs",
					click: function() {
                       $( this ).dialog( "close" );
					}
				},
				{
					html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取消",
					"class" : "btn btn-xs",
					click: function() {
						$( this ).dialog( "close" );
					}
				}
			]
		});
		loadView(id);
}
function loadView(id) {
	$.ajax({
		type : "post",
		url : cfg.view_load_data_url,
		data : {
			id : id
		},
		beforeSend : function(XMLHttpRequest) {
		},
		success : function(rst, textStatus) {
			$.each(rst.value, function(key, value) {
				if (key == 'category') {
                	value = rsd(value, '83');
                }
                if (key == 'status') {
                    value = value=="1"?"YES":"";
                }if (key.indexOf('Date')!=-1||key.indexOf('time')!=-1||key.indexOf('Time')!=-1||key.indexOf('birthday')!=-1) {
                 	value = Common.DateFormatter(value);
                }
				$("#dialog-message-view").find('#' + key).html(value);
			});
			if(editor){
			    $("textarea[name=intro]").val(rst.value.intro);
			    $("textarea[name=docText]").val(rst.value.docText);
			    editor.setData(rst.value.docText);

			}

		},
		error : function() {
			alert("加载错误！");
		}
	});
}
