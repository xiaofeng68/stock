Ext.QuickTips.init();
Ext.ns("Ext.Stock.incomingstatistics"); // 自定义一个命名空间
incomingstatistics = Ext.Stock.incomingstatistics; // 定义命名空间的别名
incomingstatistics = {
	year : ctx + '/stock/incoming' // 所有用户
}
/** 改变页的combo*/
incomingstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			incomingstatistics.pageSize  = parseInt(comboBox.getValue());
			incomingstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			incomingstatistics.store.baseParams.limit = incomingstatistics.pageSize;
			incomingstatistics.store.baseParams.start = 0;
			incomingstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
incomingstatistics.pageSize = parseInt(incomingstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
incomingstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'incoming',
		state:1,
		start : 0,
		limit : incomingstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : incomingstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'statestr','snum','year','capital','markerprice','incoming','allincoming','sumincoming' ]),
	listeners : {
		'load' : function(store, records, options) {
			incomingstatistics.alwaysFun();
		}
	}
});
incomingstatistics.alwaysFun = function() {
	Share.resetGrid(incomingstatistics.grid);
};
//incomingstatistics.store.load(); 
/** 基本信息-选择模式 */
incomingstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
incomingstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ incomingstatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	} ,{
		header : '年份',
		dataIndex : 'year'
	}, {
		header : '流通股本(股)',
		dataIndex : 'capital',
		renderer:function(value){
			if (value / 1E8 > 1) {
				return (value / 1E8).toFixed(2) + "亿股";
			} else if (value / 1E4 > 1) {
				return (value / 1E4).toFixed(2) + "万股";
			} else {
				return value.toFixed(2) + "股";
			}
		},
		width:110
	}, {
		header : '流通市值(元)',
		dataIndex : 'markerprice',
		renderer:function(value){
			if (value / 1E8 > 1) {
				return (value / 1E8).toFixed(2) + "亿";
			} else if (value / 1E4 > 1) {
				return (value / 1E4).toFixed(2) + "万";
			} else {
				return value.toFixed(2) + "";
			}
		},
		width:110
	}, {
		header : '每股收益(元)',
		dataIndex : 'incoming',
		width:110
	}, {
		header : '营业总收入(元)',
		dataIndex : 'allincoming',
		renderer:function(value){
			if (value / 1E8 > 1) {
				return (value / 1E8).toFixed(2)  + "亿";
			} else if (value / 1E4 > 1) {
				return (value / 1E4).toFixed(2)  + "万";
			} else {
				return value.toFixed(2)  + "";
			}
		},
		width:110
	}, {
		header : '利润总额(元)',
		dataIndex : 'sumincoming',
		renderer:function(value){
			if (value / 1E8 > 1) {
				return (value / 1E8).toFixed(2) + "亿";
			} else if (value / 1E4 > 1) {
				return (value / 1E4).toFixed(2) + "万";
			} else if (value / 1E4 > -1E4) {
				return (value / 1E4).toFixed(2) + "万";
			} else if (value / 1E8 > -1E8) {
				return (value / 1E8).toFixed(2) + "亿";
			} else {
				return value.toFixed(2) + "";
			}
		},
		width:110
	}, {
		header : '板块数量',
		dataIndex : 'snum'
	}, {
		header : '自定义描述',
		dataIndex : 'statestr',
		width:346,
		renderer: function (value) {
            return Share.getStockDescription(stocks_type,value);
        }
	} ]
});
/**994**/
incomingstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
incomingstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                incomingstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
incomingstatistics.bustypeCombo = new Ext.form.ComboBox({
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
incomingstatistics.formtypeCombo = new Ext.form.ComboBox({
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
incomingstatistics.sosmetypeCombo = new Ext.form.ComboBox({
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
incomingstatistics.newtypeCombo = new Ext.form.ComboBox({
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
incomingstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
/** 查询 */
incomingstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        incomingstatistics.searchFun();
    }
});
/**清空*/
incomingstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		incomingstatistics.bustypeCombo.setValue(null);
		incomingstatistics.formtypeCombo.setValue(null);
		incomingstatistics.sosmetypeCombo.setValue(null);
		incomingstatistics.newtypeCombo.setValue(null);
		incomingstatistics.lcstocktypeCombo.setValue(null);
		incomingstatistics.selectAll.checked = false;
		incomingstatistics.selectAll.el.dom.checked = false;
		incomingstatistics.codeText.setValue(null);
    }
});
/** 顶部工具栏 */
incomingstatistics.tbar = [incomingstatistics.selectAll,incomingstatistics.codeText,incomingstatistics.bustypeCombo ,incomingstatistics.formtypeCombo,incomingstatistics.sosmetypeCombo,incomingstatistics.newtypeCombo,incomingstatistics.lcstocktypeCombo,incomingstatistics.searchAction,incomingstatistics.clearAction];
/** 底部工具条 */
incomingstatistics.bbar = new Ext.PagingToolbar({
	pageSize : incomingstatistics.pageSize,
	store : incomingstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', incomingstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
incomingstatistics.grid = new Ext.grid.GridPanel({
	store : incomingstatistics.store,
	colModel : incomingstatistics.colModel,
	selModel : incomingstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:incomingstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : incomingstatistics.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : incomingstatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
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
incomingstatistics.resetStoreParameters = function(){
	var busType = incomingstatistics.bustypeCombo.getValue()
	,formType = incomingstatistics.formtypeCombo.getValue()
	,sosmeType = incomingstatistics.sosmetypeCombo.getValue()
	,newtypeType = incomingstatistics.newtypeCombo.getValue()
	,lcstockType = incomingstatistics.lcstocktypeCombo.getValue();
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
	incomingstatistics.store.baseParams.state = incomingstatistics.selectAll.checked?null:1;
	incomingstatistics.store.baseParams.code = incomingstatistics.codeText.getValue();
	incomingstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
incomingstatistics.searchFun = function () {
	incomingstatistics.resetStoreParameters();
    incomingstatistics.alwaysFun();
    incomingstatistics.store.reload();
};
/**主面板*/
incomingstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ incomingstatistics.grid ]
});