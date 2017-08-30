Ext.QuickTips.init();
Ext.ns("Ext.Stock.monthstatistics"); // 自定义一个命名空间
monthstatistics = Ext.Stock.monthstatistics; // 定义命名空间的别名
monthstatistics = {
	year : ctx + '/stock/month' // 所有用户
}
/** 改变页的combo*/
monthstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			monthstatistics.pageSize  = parseInt(comboBox.getValue());
			monthstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			monthstatistics.store.baseParams.limit = monthstatistics.pageSize;
			monthstatistics.store.baseParams.start = 0;
			monthstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
monthstatistics.pageSize = parseInt(monthstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
monthstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'month',
		state:1,
		start : 0,
		limit : monthstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : monthstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'market_date', 'hhv', 'hhv_date', 'llv', 'llv_date', 'days','profit_loss', 'num','statestr','snum' ]),
	listeners : {
		'load' : function(store, records, options) {
			monthstatistics.alwaysFun();
		}
	}
});
monthstatistics.alwaysFun = function() {
	Share.resetGrid(monthstatistics.grid);
};
//monthstatistics.store.load(); 
/** 基本信息-选择模式 */
monthstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
monthstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ monthstatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	}, {
		header : '上市日期',
		dataIndex : 'market_date',
		width:110
	}, {
		header : '最高点位',
		dataIndex : 'hhv'
	}, {
		header : '日期',
		dataIndex : 'hhv_date',
		width:110
	}, {
		header : '最低点位',
		dataIndex : 'llv'
	}, {
		header : '日期',
		dataIndex : 'llv_date',
		width:110
	}, {
		header : '交易日',
		dataIndex : 'days'
	}, {
		header : '盈亏比例',
		dataIndex:'profit_loss',
        width:110
	}, {
		header : '量能预警',
		dataIndex : 'num'
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
monthstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
monthstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                monthstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
monthstatistics.bustypeCombo = new Ext.form.ComboBox({
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
monthstatistics.formtypeCombo = new Ext.form.ComboBox({
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
monthstatistics.sosmetypeCombo = new Ext.form.ComboBox({
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
monthstatistics.newtypeCombo = new Ext.form.ComboBox({
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
monthstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
/**
 * 创建上市年份
 * @param {} year
 * @return {}
 */
monthstatistics.createPublicMarketArray = function(year){
	var date = new Date();
	var arr = [];
	for(var nyear=date.getFullYear();nyear>=year;nyear--){
		arr.push([nyear,nyear+'年']);
	}
	return arr;
};
/**
 * 一年的月份
 * @return {}
 */
monthstatistics.createPublicMonthArray = function(){
	var date = new Date();
	var arr = [];
	for(var month=1;month<=12;month++){
		arr.push([month,month+'月']);
	}
	return arr;
};
monthstatistics.publicMarketCombo = new Ext.form.ComboBox({
		emptyText:'上市年份',
		name : 'publicMarket',
		width:90,
		triggerAction : 'all',
		mode : 'local',
		store : monthstatistics.createPublicMarketArray(1990),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
monthstatistics.statisticsMarketCombo = new Ext.form.ComboBox({
		emptyText:'统计月份',
		name : 'statisticsMarket',
		width:90,
		triggerAction : 'all',
		mode : 'local',
		store : monthstatistics.createPublicMonthArray(),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
/** 查询 */
monthstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        monthstatistics.searchFun();
    }
});
/**清空*/
monthstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		monthstatistics.bustypeCombo.setValue(null);
		monthstatistics.formtypeCombo.setValue(null);
		monthstatistics.sosmetypeCombo.setValue(null);
		monthstatistics.newtypeCombo.setValue(null);
		monthstatistics.lcstocktypeCombo.setValue(null);
		monthstatistics.publicMarketCombo.setValue(null);
		monthstatistics.statisticsMarketCombo.setValue(null);
		monthstatistics.selectAll.checked = false;
		monthstatistics.selectAll.el.dom.checked = false;
		monthstatistics.codeText.setValue(null);
    }
});
/** 顶部工具栏 */
monthstatistics.tbar = [monthstatistics.selectAll,monthstatistics.codeText,monthstatistics.bustypeCombo ,monthstatistics.formtypeCombo,monthstatistics.sosmetypeCombo,monthstatistics.newtypeCombo,monthstatistics.lcstocktypeCombo,monthstatistics.publicMarketCombo,monthstatistics.statisticsMarketCombo,monthstatistics.searchAction,monthstatistics.clearAction];
/** 底部工具条 */
monthstatistics.bbar = new Ext.PagingToolbar({
	pageSize : monthstatistics.pageSize,
	store : monthstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', monthstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
monthstatistics.grid = new Ext.grid.GridPanel({
	store : monthstatistics.store,
	colModel : monthstatistics.colModel,
	selModel : monthstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:monthstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : monthstatistics.bbar,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	autoScroll : 'auto',
	region : 'center',
	loadMask : true,
	viewConfig:{
		forceFit : false, 
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
monthstatistics.resetStoreParameters = function(){
	var busType = monthstatistics.bustypeCombo.getValue()
	,formType = monthstatistics.formtypeCombo.getValue()
	,sosmeType = monthstatistics.sosmetypeCombo.getValue()
	,newtypeType = monthstatistics.newtypeCombo.getValue()
	,lcstockType = monthstatistics.lcstocktypeCombo.getValue()
	,publicMarket = monthstatistics.publicMarketCombo.getValue()
	,statisticsMarket = monthstatistics.statisticsMarketCombo.getValue();
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
	monthstatistics.store.baseParams.market_date=publicMarket;
	monthstatistics.store.baseParams.month = statisticsMarket;
	monthstatistics.store.baseParams.state = monthstatistics.selectAll.checked?null:1;
	monthstatistics.store.baseParams.code = monthstatistics.codeText.getValue();
	monthstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
monthstatistics.searchFun = function () {
	monthstatistics.resetStoreParameters();
    monthstatistics.alwaysFun();
    monthstatistics.store.reload();
};
/**主面板*/
monthstatistics.myPanel = new Ext.Panel({
	id :   stockmonth+'_panel',
	renderTo : stockmonth,
	layout : 'border',
	boder : false,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ monthstatistics.grid ]
});