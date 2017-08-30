Ext.QuickTips.init();
Ext.ns("Ext.Stock.trackcyclestatistics"); // 自定义一个命名空间
trackcyclestatistics = Ext.Stock.trackcyclestatistics; // 定义命名空间的别名
trackcyclestatistics = {
	year : ctx + '/stock/trackcycle', // 轨道周期
	del:ctx+'/stock/deltrack/',//删除轨道趋势
	save: ctx + "/stock/savetrack",//保存轨道趋势
	savestate:ctx+"/stock/savestate",//个股类型
	updatedata:ctx+"/stock/updatedata",//更新个股数据
	clearDataCache:ctx+"/stock/clearDataCache"//清空缓存
}
/** 改变页的combo*/
trackcyclestatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			trackcyclestatistics.pageSize  = parseInt(comboBox.getValue());
			trackcyclestatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			trackcyclestatistics.store.baseParams.limit = trackcyclestatistics.pageSize;
			trackcyclestatistics.store.baseParams.start = 0;
			trackcyclestatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
trackcyclestatistics.pageSize = parseInt(trackcyclestatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
trackcyclestatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'trackcycle',
		state:1,
		start : 0,
		limit : trackcyclestatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : trackcyclestatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'track','market_date', 'slf_track_t','slf_track_d','top_track_t','top_track_d','cen_track_t','cen_track_d','dwn_track_t','dwn_track_d','dwn_track_d','form_des','stock_summary','weekprice','monthprice','deepprice','targetprice','statestr','snum' ]),
	listeners : {
		'load' : function(store, records, options) {
			trackcyclestatistics.alwaysFun();
		}
	}
});
trackcyclestatistics.alwaysFun = function() {
	Share.resetGrid(trackcyclestatistics.grid);
};
//trackcyclestatistics.store.load(); 
/** 基本信息-选择模式 */
trackcyclestatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		 'rowselect': function (selectionModel, rowIndex, record) {
            trackcyclestatistics.deleteAction.enable();
            trackcyclestatistics.editAction.enable();
            trackcyclestatistics.editTypeAction.enable();
        },
        'rowdeselect': function (selectionModel, rowIndex, record) {
            trackcyclestatistics.deleteAction.disable();
            trackcyclestatistics.editAction.disable();
             trackcyclestatistics.editTypeAction.disable();
        }
	}
});
/** 基本信息-数据列 */
trackcyclestatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ trackcyclestatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	} ,{
		header : '上市日期',
		dataIndex : 'market_date',
		width:110
	}, {
		header : '所属轨道',
		dataIndex : 'track'
	}, {
		header : '下轨区间',
		dataIndex : 'dwn_track_d',
		renderer:function(value, metadata, record){
			var obj = record.data;
			return obj.dwn_track_d+"-"+obj.dwn_track_t;
		}
	}, {
		header : '中轨区间',
		dataIndex : 'cen_track_d',
		renderer:function(value, metadata, record){
			var obj = record.data;
			return obj.cen_track_d+"-"+obj.cen_track_t;
		}
	}, {
		header : '上轨区间',
		dataIndex : 'top_track_d',
		renderer:function(value, metadata, record){
			var obj = record.data;
			return obj.top_track_d+"-"+obj.top_track_t;
		}
	}, {
		header : '自定区间',
		dataIndex : 'slf_track_d',
		renderer:function(value, metadata, record){
			var obj = record.data;
			return obj.slf_track_d+"-"+obj.slf_track_t;
		}
	}, {
		header : '主力套人形态',
		dataIndex : 'form_des',
		width:150
	}, {
		header : '主力依据总结',
		dataIndex : 'stock_summary',
		width:150
	}, {
		header : '周线',
		dataIndex : 'weekprice'
	}, {
		header : '月线',
		dataIndex : 'monthprice'
	}, {
		header : '深水区',
		dataIndex : 'deepprice'
	}, {
		header : '目标价',
		dataIndex : 'targetprice'
	}, {
		header : '板块数量',
		dataIndex : 'snum'
	}, {
		header : '自定义描述',
		dataIndex : 'statestr',
		width:200,
		renderer: function (value) {
            return Share.getStockDescription(stocks_type,value);
        }
	} ]
});
/**994**/
trackcyclestatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
trackcyclestatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                trackcyclestatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
trackcyclestatistics.bustypeCombo = new Ext.form.ComboBox({
		emptyText:'行业板块',
		name : 'bustype',
		width:100,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_bustype)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
trackcyclestatistics.formtypeCombo = new Ext.form.ComboBox({
		emptyText:'个股形态',
		name : 'formtype',
		width:100,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_formtype)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
trackcyclestatistics.sosmetypeCombo = new Ext.form.ComboBox({
		emptyText:'价值投资',
		name : 'sosmetype',
		width:100,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_sosmetype)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
trackcyclestatistics.newtypeCombo = new Ext.form.ComboBox({
		emptyText:'股票新分类',
		name : 'newtype',
		width:110,
		listWidth:140,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_newtype)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
trackcyclestatistics.lcstocktypeCombo = new Ext.form.ComboBox({
		emptyText:'龙川44行业',
		name : 'lcstocktype',
		width:110,
		listWidth:140,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_lcstocktype)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
trackcyclestatistics.stocktrackCombo = new Ext.form.ComboBox({
		emptyText:'轨道',
		name : 'stocktrackCombo',
		width:80,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_track)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
/** 查询 */
trackcyclestatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        trackcyclestatistics.searchFun();
    }
});
/**编辑*/
trackcyclestatistics.editAction = new Ext.Action({
    text: '编辑',
    disabled: true,
    handler: function () {
        var record = trackcyclestatistics.grid.getSelectionModel().getSelected();
        trackcyclestatistics.addWindow.setIconClass('role_edit'); // 设置窗口的样式
        trackcyclestatistics.addWindow.setTitle('编辑轨道趋势周期'); // 设置窗口的名称
        trackcyclestatistics.addWindow.show().center();
        trackcyclestatistics.formPanel.getForm().reset();
        trackcyclestatistics.formPanel.getForm().loadRecord(record);
    }
});
/**删除*/
trackcyclestatistics.deleteAction = new Ext.Action({
    text: '删除',
    disabled: true,
    handler: function () {
         trackcyclestatistics.delFun();
    }
});
/**个股类型*/
trackcyclestatistics.editTypeAction = new Ext.Action({
    text: '编辑类型',
    disabled: true,
    handler: function () {
        var record = trackcyclestatistics.grid.getSelectionModel().getSelected();
        trackcyclestatistics.editTypeWindow.setIconClass('role_edit'); // 设置窗口的样式
        trackcyclestatistics.editTypeWindow.setTitle('个股类型'); // 设置窗口的名称
        trackcyclestatistics.editTypeWindow.show().center();
        trackcyclestatistics.formTypePanel.getForm().reset();
        var data = record.get("statestr");
        var code = record.get("code") ;
        var record = new Ext.data.Record( {code:code,statestr:data,statestr1:data,statestr2:data,statestr3:data,statestr4:data}, code);
        trackcyclestatistics.formTypePanel.getForm().loadRecord(record);
    }
});
trackcyclestatistics.delFun = function () {
    var record = trackcyclestatistics.grid.getSelectionModel().getSelected();
    Ext.Msg.confirm('提示', '确定要清空该股的趋势周期吗?', function (btn, text) {
        if (btn == 'yes') {
            // 发送请求
            Share.AjaxRequest({
                url: trackcyclestatistics.del + record.data.code,
                callback: function (json) {
                    trackcyclestatistics.alwaysFun();
                    trackcyclestatistics.store.reload();
                }
            });
        }
    });
};
/**清空*/
trackcyclestatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		trackcyclestatistics.bustypeCombo.setValue(null);
		trackcyclestatistics.formtypeCombo.setValue(null);
		trackcyclestatistics.sosmetypeCombo.setValue(null);
		trackcyclestatistics.newtypeCombo.setValue(null);
		trackcyclestatistics.lcstocktypeCombo.setValue(null);
		trackcyclestatistics.stocktrackCombo.setValue(null);
		trackcyclestatistics.selectAll.checked = false;
		trackcyclestatistics.selectAll.el.dom.checked = false;
		trackcyclestatistics.codeText.setValue(null);
    }
});
/**更新数据*/
trackcyclestatistics.updatedataAction = new Ext.Action({
    text: '数据上传',
    handler: function () {
		//使用实例
		new Ext.Window({
			width : 650,
			title : '上传示例',
			height : 300,
			layout : 'fit',
			items : [{
				xtype : 'SWFUploader',
				border : false,
				fileSize : 1024 * 550,// 限制文件大小550MB
				uploadUrl : ctx + '/stockupload',
				flashUrl : ctx + '/resources/swfupload/swfupload.swf',
				filePostName : 'file', // 后台接收参数
				fileTypes : '*.zip,*.rar',// 可上传文件类型
				postParams : {}
			}] ,
			buttons: [{
		        text: '数据更新',
		        handler: function () {//对文件先解压，然后对数据文件进行统计分析
		        	var btn = this;
		        	btn.setDisabled(true);
		        	trackcyclestatistics.timer = setInterval(function(){
		        		// 发送请求
					    Share.AjaxRequest({
					        url: trackcyclestatistics.updatedata,
					        showWaiting:false,
					        callback: function (json) {
					        	if(json.updated){
						            clearInterval(trackcyclestatistics.timer);   //关闭定时器timer
						            btn.setDisabled(false);
						            clearDataCache
						            Share.AjaxRequest({
						               url: trackcyclestatistics.clearDataCache,
					       			   callback:function(){}
						            });
					       		}else if(json.path){
					       			Ext.Msg.alert("提示", "数据正在更新中!");
					       			clearInterval(trackcyclestatistics.timer);   //关闭定时器timer
						            btn.setDisabled(false);
					       		}
					        }
					    });
		        	},2000);
		        }
		    }]
		}).show();
    }
});
/** 顶部工具栏 */
trackcyclestatistics.tbar = [trackcyclestatistics.selectAll,trackcyclestatistics.codeText,trackcyclestatistics.bustypeCombo ,trackcyclestatistics.formtypeCombo,trackcyclestatistics.sosmetypeCombo,trackcyclestatistics.newtypeCombo,trackcyclestatistics.lcstocktypeCombo,trackcyclestatistics.stocktrackCombo,trackcyclestatistics.searchAction,trackcyclestatistics.clearAction,trackcyclestatistics.editAction,trackcyclestatistics.deleteAction,trackcyclestatistics.editTypeAction,trackcyclestatistics.updatedataAction];
/** 底部工具条 */
trackcyclestatistics.bbar = new Ext.PagingToolbar({
	pageSize : trackcyclestatistics.pageSize,
	store : trackcyclestatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', trackcyclestatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
trackcyclestatistics.grid = new Ext.grid.GridPanel({
	store : trackcyclestatistics.store,
	colModel : trackcyclestatistics.colModel,
	selModel : trackcyclestatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:trackcyclestatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : trackcyclestatistics.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : trackcyclestatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
	bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
	loadMask : true,
	layout : 'fit',//自适应布局
	viewConfig:{
		//forceFit:true,//处理Grid无法显示横向滚动条
		getRowClass : function(record,rowIndex,rowParams,store){ 
			//根据字典设置返回对应的颜色值
			for(var key in stock_gridstyle){
				if(record.data.statestr.contains(','+key+',')){ 
					return 'x-grid-record-stock-'+key; 
				}
			}
			return ''; 
		} 
	},
	stripeRows : true
});
/***
 * 返回查询的参数对象
 */
trackcyclestatistics.resetStoreParameters = function(){
	var busType = trackcyclestatistics.bustypeCombo.getValue()
	,formType = trackcyclestatistics.formtypeCombo.getValue()
	,sosmeType = trackcyclestatistics.sosmetypeCombo.getValue()
	,newtypeType = trackcyclestatistics.newtypeCombo.getValue()
	,lcstockType = trackcyclestatistics.lcstocktypeCombo.getValue()
	,stocktrack = trackcyclestatistics.stocktrackCombo.getValue();
	var types = [];
	if(busType){
		types.push(busType);
	}
	if(formType){
		types.push(formType);
	}
	if(sosmeType){
		types.push(sosmeType);
	}
	if(newtypeType){
		types.push(newtypeType);
	}
	if(lcstockType){
		types.push(lcstockType);
	}
	trackcyclestatistics.store.baseParams.stocktrack = stocktrack?stocktrack:null;
	trackcyclestatistics.store.baseParams.state = trackcyclestatistics.selectAll.checked?null:1;
	trackcyclestatistics.store.baseParams.code = trackcyclestatistics.codeText.getValue();
	trackcyclestatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
trackcyclestatistics.searchFun = function () {
	trackcyclestatistics.resetStoreParameters();
    trackcyclestatistics.alwaysFun();
    trackcyclestatistics.store.reload();
};
 /** 基本信息-详细信息的form */
trackcyclestatistics.formPanel = new Ext.form.FormPanel({
    frame: false,
    title: '轨道趋势周期',
    bodyStyle: 'padding:10px;border:0px',
    layout:'form',
    labelWidth:60,
    labelAlign:'right',
    items: [{
    	xtype: 'compositefield',
	    fieldLabel: '个股信息',
	    items: [ {
		        	xtype: 'displayfield',
		        	value: '编&nbsp;&nbsp;号'
		        },{
		            xtype: 'textfield',
		            width:80,
		            readOnly:true,
		            name : 'code'
		        },{
		        	xtype: 'displayfield',
		        	value: '&nbsp;&nbsp;名&nbsp;&nbsp;称'
		        },{
		            xtype: 'textfield',
		            width:80,
		            readOnly:true,
		            name : 'name'
		        },{
		        	xtype: 'displayfield',
		        	value: '行&nbsp;&nbsp;业'
		        },{
		            xtype: 'textfield',
		            width:80,
		            readOnly:true,
		            name : 'bus'
		        }
		    ]
	    },{
	   	xtype: 'compositefield',
	    fieldLabel: '所属轨道',
	    items: [{
		    	xtype: 'radiogroup',
		        items: [
		        	{boxLabel: '下轨', name: 'track', inputValue: '下轨'},
		        	{boxLabel: '中轨', name: 'track', inputValue: '中轨'},
		            {boxLabel: '上轨', name: 'track', inputValue: '上轨'},
		            {boxLabel: '自定', name: 'track', inputValue: '自定'}
		        ]
		    }
	    ]},{
		    xtype: 'compositefield',
		    fieldLabel: '下轨区间',
		    items: [{
		            xtype: 'textfield',
		            width:53,
		            name : 'dwn_track_t'
		        }, {
		        	xtype: 'displayfield',
		        	value: '&nbsp;&nbsp;至&nbsp;&nbsp;'
		        },{
		            xtype: 'textfield',
		            width:53,
		            name : 'dwn_track_d'
		        }, {
		        	xtype: 'displayfield',
		        	value: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中轨区间:'
		        },{
		            xtype: 'textfield',
		            width:53,
		            name : 'cen_track_t'
		        }, {
		        	xtype: 'displayfield',
		        	value: '&nbsp;&nbsp;至&nbsp;&nbsp;'
		        },{
		            xtype: 'textfield',
		            width:53,
		            name : 'cen_track_d'
		        }]
	    },{
		    xtype: 'compositefield',
		    fieldLabel: '上轨区间',
		    items: [{
		            xtype: 'textfield',
		            width:53,
		            name : 'top_track_t'
		        }, {
		        	xtype: 'displayfield',
		        	value: '&nbsp;&nbsp;至&nbsp;&nbsp;'
		        },{
		            xtype: 'textfield',
		            width:53,
		            name : 'top_track_d'
		        }, {
		        	xtype: 'displayfield',
		        	value: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自定区间:'
		        },{
		            xtype: 'textfield',
		            width:53,
		            name : 'slf_track_t'
		        }, {
		        	xtype: 'displayfield',
		        	value: '&nbsp;&nbsp;至&nbsp;&nbsp;'
		        },{
		            xtype: 'textfield',
		            width:53,
		            name : 'slf_track_d'
		        }]
	    },{
		    xtype: 'compositefield',
		    fieldLabel: '周线',
		    items: [{
		            xtype: 'textfield',
		            width:53,
		            name : 'weekprice'
		        }, {
		        	xtype: 'displayfield',
		        	value: '月&nbsp;&nbsp;线'
		        },{
		            xtype: 'textfield',
		            width:53,
		            name : 'monthprice'
		        }, {
		        	xtype: 'displayfield',
		        	value: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深水区:'
		        },{
		            xtype: 'textfield',
		            width:53,
		            name : 'deepprice'
		        }, {
		        	xtype: 'displayfield',
		        	value: '目标位'
		        },{
		            xtype: 'textfield',
		            width:53,
		            name : 'targetprice'
		        }]
	    },{
		    xtype: 'textfield',
		    fieldLabel: '主力形态',
		    name:'form_des',
		    anchor:'100%'
	    },{
		    xtype: 'textarea',
		    fieldLabel: '主力分析',
		    name:'stock_summary',
		    anchor:'100%'
	    }
    ]
});
/** 编辑新建窗口 */
trackcyclestatistics.addWindow = new Ext.Window({
    layout: 'fit',
    width: 518,
    height: 500,
    closeAction: 'hide',
    plain: true,
    modal: true,
    resizable: true,
    items: [trackcyclestatistics.formPanel],
    buttons: [{
        text: '保存',
        handler: function () {
            trackcyclestatistics.saveFun();
        }
    }, {
        text: '重置',
        handler: function () {
            var form = trackcyclestatistics.formPanel.getForm();
            var code = form.findField("code").getValue();
            var name = form.findField("name").getValue();
            var bus = form.findField("bus").getValue();
            form.reset();
            if (code != '') form.findField("code").setValue(code);
            if (name != '') form.findField("name").setValue(name);
            if (bus != '') form.findField("bus").setValue(bus);
        }
    }]
});

trackcyclestatistics.getChildrenItems = function(types,fields){
	var items = [];
	for(var i in types){
		items.push({boxLabel: types[i], inputValue:i});
	}
	return {
		xtype: 'checkboxgroup',
		name:fields,
		columns: 8,
        items:items
	};
}
/**初始化股票类型*/
trackcyclestatistics.typeItems = function(title,types,fields,disabled ){
	var items = { 
		xtype: 'fieldset',
        title: title,
        autoHeight: true,
        disabled:disabled ,
        items: [trackcyclestatistics.getChildrenItems(types,fields)]
    };
	return items;
}
trackcyclestatistics.formTypePanel = new Ext.form.FormPanel({
	collapsible:true,
    autoScroll:true,
    frame: false,
    header:false,
    bodyStyle: 'padding:10px;border:0px',
    anchor:'99%',
    layout:'form',
    labelWidth:1,
    labelAlign:'right',
    items: [trackcyclestatistics.typeItems("龙川44行业",stock_lcstocktype,"statestr"),
    		trackcyclestatistics.typeItems("股票新分类",stock_newtype,"statestr1"),
    		trackcyclestatistics.typeItems("个股形态",stock_formtype,"statestr2"),
    		trackcyclestatistics.typeItems("价值投资",stock_sosmetype,"statestr3"),
    		trackcyclestatistics.typeItems("行业分类",stock_bustype,"statestr4",true),{
			  xtype:'hidden',
			   name:'code'
			}]
});
/**个股类型编辑窗口*/
trackcyclestatistics.editTypeWindow = new Ext.Window({
    layout: 'fit',
    width: 1000,
    height: 600,
    closeAction: 'hide',
    plain: true,
    modal: true,
    autoscroll:true,
    resizable: true,
    items: [trackcyclestatistics.formTypePanel],
    buttons: [{
        text: '保存',
        handler: function () {
             var form = trackcyclestatistics.formTypePanel.getForm();
             var value = form.getValues();
             var statestr = ",",code;
             for(var i in value){
             	if("code"!=i)
             		statestr+=value[i]+",";
             	else
             		code = value[i];
             }
             trackcyclestatistics.saveSateFun({statestr:statestr,code:code});
        }
    }]
});
/**更新个股类型*/
trackcyclestatistics.saveSateFun = function(params){
	// 发送请求
    Share.AjaxRequest({
        url: trackcyclestatistics.savestate,
        params: params,
        callback: function (json) {
            trackcyclestatistics.editTypeWindow.hide();
            trackcyclestatistics.alwaysFun();
            trackcyclestatistics.store.reload();
        }
    });
 }
trackcyclestatistics.saveFun = function () {
    var form = trackcyclestatistics.formPanel.getForm();
    if (!form.isValid()) {
        return;
    }
    // 发送请求
    Share.AjaxRequest({
        url: trackcyclestatistics.save,
        params: form.getValues(),
        callback: function (json) {
            trackcyclestatistics.addWindow.hide();
            trackcyclestatistics.alwaysFun();
            trackcyclestatistics.store.reload();
        }
    });
};
/**主面板*/
trackcyclestatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ trackcyclestatistics.grid ]
});
Ext.override(Ext.form.CheckboxGroup,{ 
	setValue:function(val){  //多个选项值以逗号分隔的 
        this.items.each(function(item) {   
            if (val.indexOf(","+item.inputValue+",") != -1) {   
                item.setValue(true);  
            } else {  
                item.setValue(false);  
            }  
        });  
    }  
    ,clearValue:function(){  // 清空所有值  
        this.items.each(function(item) {   
            item.setValue(false);  
        });  
    }  
}); 