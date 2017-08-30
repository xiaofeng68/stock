Ext.QuickTips.init();
Ext.ns("Ext.Stock.yearstatistics"); // 自定义一个命名空间
yearstatistics = Ext.Stock.yearstatistics; // 定义命名空间的别名
yearstatistics = {
	year : ctx + '/stock/year' // 所有用户
}
/** 改变页的combo*/
yearstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			yearstatistics.pageSize  = parseInt(comboBox.getValue());
			yearstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			yearstatistics.store.baseParams.limit = yearstatistics.pageSize;
			yearstatistics.store.baseParams.start = 0;
			yearstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
yearstatistics.pageSize = parseInt(yearstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
yearstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'year',
		state:1,
		start : 0,
		limit : yearstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : yearstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'market_date', 'hhv', 'hhv_date', 'llv', 'llv_date', 'days','profit_loss', 'num','statestr','snum' ]),
	listeners : {
		'load' : function(store, records, options) {
			yearstatistics.alwaysFun();
		}
	}
});
yearstatistics.alwaysFun = function() {
	Share.resetGrid(yearstatistics.grid);
};
//yearstatistics.store.load(); 
/** 基本信息-选择模式 */
yearstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
yearstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ yearstatistics.selModel, {
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
yearstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
yearstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                yearstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
yearstatistics.bustypeCombo = new Ext.form.ComboBox({
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
yearstatistics.formtypeCombo = new Ext.form.ComboBox({
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
yearstatistics.sosmetypeCombo = new Ext.form.ComboBox({
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
yearstatistics.newtypeCombo = new Ext.form.ComboBox({
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
yearstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
yearstatistics.createPublicMarketArray = function(year){
	var date = new Date();
	var arr = [];
	for(var nyear=date.getFullYear();nyear>=year;nyear--){
		arr.push([nyear,nyear+'年']);
	}
	return arr;
};
yearstatistics.publicMarketCombo = new Ext.form.ComboBox({
		emptyText:'上市年份',
		name : 'publicMarket',
		width:90,
		triggerAction : 'all',
		mode : 'local',
		store : yearstatistics.createPublicMarketArray(1990),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
yearstatistics.statisticsMarketCombo = new Ext.form.ComboBox({
		emptyText:'统计年份',
		name : 'statisticsMarket',
		width:90,
		triggerAction : 'all',
		mode : 'local',
		store : yearstatistics.createPublicMarketArray(2009),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
/** 查询 */
yearstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        yearstatistics.searchFun();
    }
});
/**清空*/
yearstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		yearstatistics.bustypeCombo.setValue(null);
		yearstatistics.formtypeCombo.setValue(null);
		yearstatistics.sosmetypeCombo.setValue(null);
		yearstatistics.newtypeCombo.setValue(null);
		yearstatistics.lcstocktypeCombo.setValue(null);
		yearstatistics.publicMarketCombo.setValue(null);
		yearstatistics.statisticsMarketCombo.setValue(null);
		yearstatistics.selectAll.checked = false;
		yearstatistics.selectAll.el.dom.checked = false;
		yearstatistics.codeText.setValue(null);
    }
});
/** 顶部工具栏 */
yearstatistics.tbar = [yearstatistics.selectAll,yearstatistics.codeText,yearstatistics.bustypeCombo ,yearstatistics.formtypeCombo,yearstatistics.sosmetypeCombo,yearstatistics.newtypeCombo,yearstatistics.lcstocktypeCombo,yearstatistics.publicMarketCombo,yearstatistics.statisticsMarketCombo,yearstatistics.searchAction,yearstatistics.clearAction];
/** 底部工具条 */
yearstatistics.bbar = new Ext.PagingToolbar({
	pageSize : yearstatistics.pageSize,
	store : yearstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', yearstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
yearstatistics.grid = new Ext.grid.GridPanel({
	store : yearstatistics.store,
	colModel : yearstatistics.colModel,
	selModel : yearstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:yearstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : yearstatistics.bbar,
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
yearstatistics.resetStoreParameters = function(){
	var busType = yearstatistics.bustypeCombo.getValue()
	,formType = yearstatistics.formtypeCombo.getValue()
	,sosmeType = yearstatistics.sosmetypeCombo.getValue()
	,newtypeType = yearstatistics.newtypeCombo.getValue()
	,lcstockType = yearstatistics.lcstocktypeCombo.getValue()
	,publicMarket = yearstatistics.publicMarketCombo.getValue()
	,statisticsMarket = yearstatistics.statisticsMarketCombo.getValue();
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
//	if(publicMarket){
		yearstatistics.store.baseParams.market_date=publicMarket;
//	}
//	if(statisticsMarket){
		yearstatistics.store.baseParams.year = statisticsMarket;
//	}
	yearstatistics.store.baseParams.state = yearstatistics.selectAll.checked?null:1;
	yearstatistics.store.baseParams.code = yearstatistics.codeText.getValue();
	yearstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
yearstatistics.searchFun = function () {
	yearstatistics.resetStoreParameters();
    yearstatistics.alwaysFun();
    yearstatistics.store.reload();
};
/**主面板*/
yearstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	boder : false,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ yearstatistics.grid ]
});