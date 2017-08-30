Ext.QuickTips.init();
Ext.ns("Ext.Stock.codestatistics"); // 自定义一个命名空间
codestatistics = Ext.Stock.codestatistics; // 定义命名空间的别名
codestatistics = {
	year : ctx + '/stock/warn',
	track : ctx + '/stock/track', 
	msgs : ctx + '/stock/msgs',
	incoming:ctx + '/stock/incoming',
	czb : ctx + '/stock/czb',
	holder : ctx + '/stock/holder',
	updown : ctx + '/stock/updown',
	code:ctx+'/stockstatistics/code/',
	yearstatistics:ctx+'/stockstatistics/year/',
	monthstatistics:ctx+'/stockstatistics/month/',
	closestatistics:ctx+'/stockstatistics/close/'
}
/** 改变页的combo*/
codestatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '10',
	listeners : {
		select : function(comboBox) {
			codestatistics.pageSize  = parseInt(comboBox.getValue());
			codestatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			codestatistics.store.baseParams.limit = codestatistics.pageSize;
			codestatistics.store.baseParams.start = 0;
			codestatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
codestatistics.pageSize = parseInt(codestatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
codestatistics.store = new Ext.data.Store({
	remoteSort : true,
	baseParams : {
		type:'warn',
		state:1,
		start : 0,
		limit : codestatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : codestatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, ['day_d','times', 'minprice','yclose','zhang','tov','sumprice','uddays']),
	listeners : {
		'load' : function(store, records, options) {
			Share.resetGrid(codestatistics.grid);
		}
	}
});
/** 基本信息-选择模式 */
codestatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
codestatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ codestatistics.selModel, {
		header : '预警时间',
		dataIndex : 'day_d',
		width:130,
		renderer: function (value, metadata, record) {
            var obj = record.data;
            return obj.day_d+' '+obj.times;
        }
	}, {
		header : '当日最低价',
		dataIndex : 'minprice',
		width:110
	}, {
		header : '昨日收盘价',
		dataIndex : 'yclose',
		width:110
	}, {
		header : '当日涨幅',
		dataIndex : 'zhang'
	}, {
		header : '量能预警值',
		dataIndex : 'tov',
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
		header : '当日成交额',
		dataIndex : 'sumprice',
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
		header : '连涨/跌',
		dataIndex : 'uddays'
	} ]
});
/***
 * 编号信息下拉列表数据源
 */
codestatistics.msgStore = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : codestatistics.msgs
	}),
	reader : new Ext.data.JsonReader({}, ['code','name', { 
	       name: 'display', 
	       convert: function(v, rec) { 
	    	   return rec['code'] +' - '+ rec['name']; 
	       }
    }])
});
/***
 * 编号信息下拉列表
 */
codestatistics.codeSearchText = new Ext.form.ComboBox({  
    store : codestatistics.msgStore,
    emptyText:'请输入查询的编号',
    typeAhead : true,  
    mode : 'local',  
    editable : true,  
    displayField :'display',
    valueField :'code',  
    triggerAction : 'all',  
    selectOnFocus : true,  
    listeners : {  
        'beforequery':function(e){  
            var combo = e.combo;    
            if(!e.forceAll){    
                var input = e.query;    
                // 检索的正则  
                var regExp = new RegExp(".*" + input + ".*");  
                // 执行检索  
                combo.store.filterBy(function(record,id){    
                    // 得到每个record的项目名称值  
                    var text = record.get(combo.displayField);    
                    return regExp.test(text);   
                });  
                combo.expand();    
                return false;  
            }  
        }
    }  
});  
/** 查询 */
codestatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
    	codestatistics.searchFun();
    }
});
/** 顶部工具栏 */
codestatistics.tbar = ['编号/名称','-',codestatistics.codeSearchText,'-',codestatistics.searchAction];
/** 底部工具条 */
codestatistics.bbar = new Ext.PagingToolbar({
	pageSize : codestatistics.pageSize,
	store : codestatistics.store,
	displayInfo : true,
	items : [ '-', '&nbsp;', codestatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
codestatistics.grid = new Ext.grid.GridPanel({
	title:'量能预警',
	store : codestatistics.store,
	colModel : codestatistics.colModel,
	selModel : codestatistics.selModel,
	bbar : codestatistics.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	columnWidth : 0.5,
	height:300,
	autoScroll:true,
	width : codestatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
	bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
	loadMask : true,
	//layout : 'fit',//自适应布局
	viewConfig:{
		forceFit:false
	},
	stripeRows : true
});
/**=======================================*/
/** 改变页的combo*/
codestatistics.pageSizeTrackCombo = new Share.pageSizeCombo({
	value : '10',
	listeners : {
		select : function(comboBox) {
			codestatistics.pageTrackSize  = parseInt(comboBox.getValue());
			codestatistics.trackBbar.pageSize  = parseInt(comboBox.getValue());
			codestatistics.trackStore.baseParams.limit = codestatistics.pageTrackSize;
			codestatistics.trackStore.baseParams.start = 0;
			codestatistics.trackStore.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
codestatistics.pageTrackSize = parseInt(codestatistics.pageSizeTrackCombo.getValue());
/** 基本信息-选择模式 */
codestatistics.selTrackModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
codestatistics.colTrackModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ codestatistics.selTrackModel, {
		header : '预警日期',
		dataIndex : 'days',
		width:110
	}, {
		header : '轨道描述',
		dataIndex : 'track_ud',
		renderer:function(value){
			if (value && value!='null') {
				return value;
			} else {
				return"";
			}
		},
		width:150
	}, {
		header : '最新价',
		dataIndex : 'nprice'
	}, {
		header : '涨幅',
		dataIndex : 'zhang'
	}, {
		header : '量能预警次数',
		dataIndex : 'num',
		width:130
	}, {
		header : '连涨/跌',
		dataIndex : 'uddays'
	} ]
});
/** 基本信息-数据源 */
codestatistics.trackStore = new Ext.data.Store({
	remoteSort : true,
	baseParams : {
		type:'track',
		start : 0,
		limit : codestatistics.pageTrackSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : codestatistics.track
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'days', 'track_ud','nprice','zhang','uddays','num']),
	listeners : {
		'load' : function(store, records, options) {
			Share.resetGrid(codestatistics.trackGrid);
		}
	}
});
/** 底部工具条 */
codestatistics.trackBbar = new Ext.PagingToolbar({
	pageSize : codestatistics.pageTrackSize,
	store : codestatistics.trackStore,
	displayInfo : true,
	items : [ '-', '&nbsp;', codestatistics.pageSizeTrackCombo ]
});
/** 基本信息-表格 */
codestatistics.trackGrid = new Ext.grid.GridPanel({
	title:'轨道预警',
	store : codestatistics.trackStore,
	colModel : codestatistics.colTrackModel,
	selModel : codestatistics.selTrackModel,
	bbar : codestatistics.trackBbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	columnWidth : 0.5,
	height:300,
	autoScroll:true,
	width : codestatistics.colTrackModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
	bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
	loadMask : true,
	layout : 'fit',//自适应布局
	viewConfig:{},
	stripeRows : true
});
/***===================================================*/
/** 改变页的combo*/
codestatistics.pageIncomingSizeCombo = new Share.pageSizeCombo({
	value : '10',
	listeners : {
		select : function(comboBox) {
			codestatistics.pageIncomingSize  = parseInt(comboBox.getValue());
			codestatistics.incomingBbar.pageSize  = parseInt(comboBox.getValue());
			codestatistics.incomingStore.baseParams.limit = codestatistics.pageIncomingSize;
			codestatistics.incomingStore.baseParams.start = 0;
			codestatistics.incomingStore.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
codestatistics.pageIncomingSize = parseInt(codestatistics.pageIncomingSizeCombo.getValue());
/** 底部工具条 */
codestatistics.incomingBbar = new Ext.PagingToolbar({
	pageSize : codestatistics.pageIncomingSize,
	store : codestatistics.incomingStore,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', codestatistics.pageIncomingSizeCombo ]
});
/** 基本信息-数据源 */
codestatistics.incomingStore = new Ext.data.Store({
	remoteSort : true,
	baseParams : {
		type:'incoming',
		start : 0,
		limit : codestatistics.pageIncomingSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : codestatistics.incoming
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'year','capital','markerprice','incoming','allincoming','sumincoming' ])
});
/** 基本信息-选择模式 */
codestatistics.selIncomingModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
codestatistics.colIncomingModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ codestatistics.selIncomingModel, {
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
		width:130
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
		width:130
	}, {
		header : '每股收益(元)',
		dataIndex : 'incoming',
		width:130
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
		width:130
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
		width:130
	} ]
});
/** 基本信息-表格 */
codestatistics.incomingGrid = new Ext.grid.GridPanel({
	title:'个股收益',
	store : codestatistics.incomingStore,
	colModel : codestatistics.colIncomingModel,
	selModel : codestatistics.selIncomingModel,
	bbar : codestatistics.incomingBbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	columnWidth : 0.5,
	height:300,
	autoScroll:true,
	width : codestatistics.colIncomingModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
	bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
	loadMask : true,
	layout : 'fit',//自适应布局
	viewConfig:{},
	stripeRows : true
});
/**==================财政部========================**/
/** 改变页的combo*/
codestatistics.pageSizeCzbCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			codestatistics.pageCzbSize  = parseInt(comboBox.getValue());
			codestatistics.czbBbar.pageSize  = parseInt(comboBox.getValue());
			codestatistics.czbStore.baseParams.limit = codestatistics.pageCzbSize;
			codestatistics.czbStore.baseParams.start = 0;
			codestatistics.czbStore.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
codestatistics.pageCzbSize = parseInt(codestatistics.pageSizeCzbCombo.getValue());
/** 基本信息-数据源 */
codestatistics.czbStore = new Ext.data.Store({
	remoteSort : true,
	baseParams : {
		type:'czb',
		start : 0,
		limit : codestatistics.pageCzbSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : codestatistics.czb
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, ['day_d','buy_p','sell_p','type_code' ])
});
/** 基本信息-选择模式 */
codestatistics.selCzbModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
codestatistics.colCzbModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ codestatistics.selCzbModel, {
		header : '交易日期',
		dataIndex : 'day_d'
	}, {
		header : '营业部买入(万)',
		dataIndex : 'buy_p',
		width:130
	}, {
		header : '营业部卖出(万)',
		dataIndex : 'sell_p',
		width:130
	}, {
		header : '营业部',
		dataIndex : 'type_code',
		width:160,
		renderer:function(value){
			return stock_czb[value];
		}
	}]
});
/** 底部工具条 */
codestatistics.czbBbar = new Ext.PagingToolbar({
	pageSize : codestatistics.pageCzbSize,
	store : codestatistics.czbStore,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', codestatistics.pageSizeCzbCombo ]
});
/** 基本信息-表格 */
codestatistics.czbGrid = new Ext.grid.GridPanel({
	title:'财政部',
	store : codestatistics.czbStore,
	colModel : codestatistics.colCzbModel,
	selModel : codestatistics.selCzbModel,
	bbar : codestatistics.czbBbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	columnWidth : 0.5,
	height:300,
	autoScroll:true,
	width : codestatistics.colCzbModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
	bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
	loadMask : true,
	layout : 'fit',//自适应布局
	viewConfig:{},
	stripeRows : true
});
/**===========================十大流通股东================================**/
/** 改变页的combo*/
codestatistics.pageSizeHolderCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			codestatistics.pageHolderSize  = parseInt(comboBox.getValue());
			codestatistics.holderBbar.pageSize  = parseInt(comboBox.getValue());
			codestatistics.holderStore.baseParams.limit = codestatistics.pageHolderSize;
			codestatistics.holderStore.baseParams.start = 0;
			codestatistics.holderStore.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
codestatistics.pageHolderSize = parseInt(codestatistics.pageSizeHolderCombo.getValue());
/** 基本信息-数据源 */
codestatistics.holderStore = new Ext.data.Store({
	remoteSort : true,
	baseParams : {
		type:'holder',
		start : 0,
		limit : codestatistics.pageHolderSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : codestatistics.holder
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, ['stockholder','type', 'stockcount','stock_ratio','ios','day_d'])
});
/** 基本信息-选择模式 */
codestatistics.selHolderModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
codestatistics.colHolderModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ codestatistics.selHolderModel, {
		header : '股东名称',
		dataIndex : 'stockholder',
		width:160
	}, {
		header : '股份类型',
		dataIndex : 'type',
		width:160
	}, {
		header : '持股数',
		dataIndex : 'stockcount',
		renderer:function(value){
			if (value / 1E8 > 1) {
				return (value / 1E8).toFixed(2) + "亿股";
			} else if (value / 1E4 > 1) {
				return (value / 1E4).toFixed(2) + "万股";
			} else {
				return value.toFixed(2) + "";
			}
		},
		width:110
	}, {
		header : '持股比例',
		dataIndex : 'stock_ratio'
	}, {
		header : '股东状态',
		dataIndex : 'ios'
	}, {
		header : '增减(股)',
		dataIndex : 'state'
	}, {
		header : '日期',
		dataIndex : 'day_d',
		width:110
	}]
});
/** 底部工具条 */
codestatistics.holderBbar = new Ext.PagingToolbar({
	pageSize : codestatistics.pageHolderSize,
	store : codestatistics.holderStore,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', codestatistics.pageSizeHolderCombo ]
});
/** 基本信息-表格 */
codestatistics.holderGrid = new Ext.grid.GridPanel({
	title:'十大流通股东',
	store : codestatistics.holderStore,
	colModel : codestatistics.colHolderModel,
	selModel : codestatistics.selHolderModel,
	bbar : codestatistics.holderBbar,
//	stripeRows:true,
//	cellTip:true,//出现...时鼠标指向时出现全部内容
	columnWidth : 0.5,
	height:300,
	autoScroll:true,
	width : codestatistics.colHolderModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
	bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
	loadMask : true,
	layout : 'fit',//自适应布局
	viewConfig:{},
	stripeRows : true
});
/***=============================个股涨跌幅===============================****/
/** 改变页的combo*/
codestatistics.pageSizeUpdownCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			codestatistics.pageUpdownSize  = parseInt(comboBox.getValue());
			codestatistics.upDownBbar.pageSize  = parseInt(comboBox.getValue());
			codestatistics.upDownStore.baseParams.limit = codestatistics.pageUpdownSize;
			codestatistics.upDownStore.baseParams.start = 0;
			codestatistics.upDownStore.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
codestatistics.pageUpdownSize = parseInt(codestatistics.pageSizeUpdownCombo.getValue());
/** 基本信息-数据源 */
codestatistics.upDownStore = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'updown',
		state:1,
		start : 0,
		limit : codestatistics.pageUpdownSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : codestatistics.updown
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'sumn','num' ])
});
/** 基本信息-选择模式 */
codestatistics.selUpdownModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
codestatistics.colUpdownModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ codestatistics.selUpdownModel,  {
		header : '活跃天数',
		dataIndex : 'num',
		width : 110
	}, {
		header : '狙击总数',
		dataIndex : 'sumn',
		width : 110
	} ]
});
/** 底部工具条 */
codestatistics.upDownBbar = new Ext.PagingToolbar({
	pageSize : codestatistics.pageUpdownSize,
	store : codestatistics.upDownStore,
	displayInfo : true,
	items : [ '-', '&nbsp;', codestatistics.pageSizeUpdownCombo ]
});
/** 基本信息-表格 */
codestatistics.upDownGrid = new Ext.grid.GridPanel({
	title:'涨跌幅天数统计',
	store : codestatistics.upDownStore,
	colModel : codestatistics.colUpdownModel,
	selModel : codestatistics.selUpdownModel,
	bbar : codestatistics.upDownBbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	columnWidth : 0.5,
	height:300,
	autoScroll:true,
	width : codestatistics.colUpdownModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
	bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
	loadMask : true,
	layout : 'fit',//自适应布局
	viewConfig:{},
	stripeRows : true
});

codestatistics.timeUtil = new DateTimeUtil();
/***============================核心题材===========================*/
/**核心题材内容展示*/
codestatistics.contextText = new Ext.form.HtmlEditor({
	name : 'contextTextField',
	readOnly:true,
	columnWidth : 1,
	emptyText:'编号/名称',
	anchor : '99%'
});
/***======================基本信息=========================**/
codestatistics.stockLabel = new Ext.form.TextArea({
	region:'center',
	boder : false,
	readOnly:true
}); 
codestatistics.msgPanel = new Ext.Panel({
	title:'基本信息',
	autoScroll:"true",
	height:80,
	layout:'border',
	columnWidth:1,
	boder : false,
	anchor : '99%',
	items : [codestatistics.stockLabel ]
});
/**************************个股股票报表*******************************/
codestatistics.stockPanel = new Ext.Panel({
	title:'个股趋势',
	autoScroll:"true",
	height:500,
	columnWidth:1,
	boder : false,
	anchor : '99%',
	items : []
});
codestatistics.yearPanel = new Ext.Panel({
	title:'年统计',
	id:'stockYearStatistics',
	autoScroll:"true",
	height:300,
	columnWidth:0.5,
	boder : false,
	anchor : '99%',
	items : []
});
codestatistics.monthPanel = new Ext.Panel({
	title:'月统计',
	id:'stockMonthStatistics',
	autoScroll:"true",
	height:300,
	columnWidth:0.5,
	boder : false,
	anchor : '99%',
	items : []
});
/***
 * 返回查询的参数对象
 */
codestatistics.resetStoreParameters = function(){
	var dateArr = codestatistics.timeUtil.getCurrentYear();
	var code = codestatistics.codeSearchText.getValue(),
	startDate = dateArr[0].Format("yyyy-MM-dd hh:mm:ss"),
	endDate = dateArr[1].Format("yyyy-MM-dd hh:mm:ss");
	codestatistics.store.baseParams.code = code;
	codestatistics.store.baseParams.startDate = startDate;
	codestatistics.store.baseParams.endDate = endDate;
	
	codestatistics.trackStore.baseParams.code = code;
	codestatistics.trackStore.baseParams.startDate = startDate;
	codestatistics.trackStore.baseParams.endDate = endDate;
	
	codestatistics.incomingStore.baseParams.code = code;
	
	codestatistics.czbStore.baseParams.startDate = startDate;
	codestatistics.czbStore.baseParams.endDate = endDate;
	codestatistics.czbStore.baseParams.code = code;
	
	codestatistics.holderStore.baseParams.code = code;
	
	codestatistics.upDownStore.baseParams.startDate = startDate;
	codestatistics.upDownStore.baseParams.endDate = endDate;
	codestatistics.upDownStore.baseParams.code = code;
	
	// 请求个股基本信息
    Share.AjaxRequest({
        url: codestatistics.code + code,
        callback: function (json) {
        	var context = json.content;
        	context = context.replace(new RegExp('\r', 'g'), '<br/>');
//        	context = context.replace(new RegExp('要点', 'g'), '<span style="color:#FF0000;font-size:25;">题材</span>');
        	
        	codestatistics.contextText.setValue(context);
        	codestatistics.stockLabel.setValue("编号："+json.code+"\t名称："+json.name+"\t所属行业："+json.bus+"\t板块描述："+Share.getStockDescription(stocks_type,json.statestr));//+"\n曾用名："+json.oldname
        }
    });
   //请求个股统计报表
   Share.AjaxRequest({
        url: codestatistics.closestatistics + code,
        callback: function (json) {
        	codestatistics.dayChart(codestatistics.stockPanel.body.dom,json.data,json.msg);
        }
    });
    //请求个股统计报表
    Share.AjaxRequest({
        url: codestatistics.yearstatistics + code,
        callback: function (json) {
        	codestatistics.newChart(codestatistics.yearPanel.body.dom,json,{
        		categoryField:'year',
        		dataDateFormat:"YYYY",
        		minPeriod:'YYYY',
        		valueField:'uds',
        		dateFormats:[{period:"YYYY", format:"YYYY"}],
        		balloonText:"[[category]]年盈亏比例<span style='font-size:14px;'>[[uds]]</span>",
        		categoryBalloonDateFormat:'YYYY'
        	});
        }
    });
    //请求个股统计报表
    Share.AjaxRequest({
        url: codestatistics.monthstatistics + code,
        callback: function (json) {
        	codestatistics.newChart(codestatistics.monthPanel.body.dom,json,{
        		categoryField:'year',
        		dataDateFormat:"YYYY-MM",
        		minPeriod:'MM',
        		dateFormats:[{period:"YYYY", format:"YYYY"}, {period:"MM", format:"YYYY-MM"}],
        		valueField:'uds',
        		balloonText:"[[category]]月盈亏比例<span style='font-size:14px;'>[[uds]]</span>",
        		categoryBalloonDateFormat:'YYYY-MM'
        	});
        }
    });
};
codestatistics.dayChart = function(dom,dataProvider,msg){
	var chart = new AmCharts.AmStockChart();
	chart.pathToImages = "resources/amcharts/images/";
	var dataSet = new AmCharts.DataSet();
	dataSet.color = "#b0de09";
	dataSet.fieldMappings = [{
		fromField: "close",
		toField: "close"
	}, {
		fromField: "vol",
		toField: "vol"
	}];
	dataSet.dataProvider = dataProvider;
	dataSet.categoryField = "date";
	chart.dataSets = [dataSet];
	var stockPanel1 = new AmCharts.StockPanel();
	stockPanel1.showCategoryAxis = false;
	stockPanel1.title = "收盘价";
	stockPanel1.percentHeight = 70;
	stockPanel1.drawingIconsEnabled = true;//添加画笔
	var graph1 = new AmCharts.StockGraph();
	graph1.type = "smoothedLine";
	graph1.valueField = "close";
	graph1.connect = true;
	graph1.bullet = "round";
	graph1.lineThickness = 1;
	graph1.bulletSize = 2;
	graph1.bulletBorderColor = "#FFFFFF";
	graph1.bulletBorderThickness = 1;
	graph1.hideBulletsCount = 50;
	graph1.balloonFunction =　function(a,b){
		var price = a.values.value;//成交量
		if(price){
			var obj = dataProvider[a.index];
			var vol = obj.vol,volStr;
			if(vol/1E8>1){
				volStr = (vol / 1E8).toFixed(2) + "亿";
			}else if (vol / 1E4 > 1) {
				volStr = (vol / 1E4).toFixed(2) + "万";
			}else{
				volStr = vol+"";
			}
			return "开盘价："+obj.open+"<br/>最高价："+obj.hhv+"<br/>最低价："+obj.llv+"<br/>收盘价："+obj.close+"<br/>昨日收盘价："+obj.yclose+"<br/>成交量："+volStr;
		}else{
			return '';
		}
	};
	stockPanel1.addStockGraph(graph1);
	
	// value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.axisColor = "#DADADA";
    valueAxis.dashLength = 1;
    valueAxis.logarithmic = true; // this line makes axis logarithmic
    stockPanel1.addValueAxis(valueAxis);
    var vals =[{key:'dwn_track_t',label:'下轨-底',color:'#CC0000',dash:4},{key:'dwn_track_d',label:'中轨-底',color:'#CC0000',dash:4},
               {key:'cen_track_d',label:'上轨-底',color:'#CC0000',dash:4},{key:'top_track_d',label:'上轨-顶',color:'#CC0000',dash:4},
               {key:'slf_track_d',label:'自定区间-顶',color:'#CC0000',dash:4},
               {key:'weekprice',label:'周线',color:'#00FF00',dash:1},{key:'monthprice',label:'月线',color:'#D522E0',dash:1},
               {key:'deepprice',label:'深水区',color:'#0000FF',dash:1},{key:'targetprice',label:'目标点位',color:'#FF0000',dash:1}];
    // GUIDE for average
    for(var i=0,j=vals.length;i<j;i++){//添加分曲线
    	if(!msg) break;
    	var obj = msg[vals[i].key]; 
    	if(obj){
		    var guide = new AmCharts.Guide();
		    guide.value = obj;
		    guide.lineColor = vals[i].color;
		    guide.dashLength = 4;
		    guide.label = "　　　　"+vals[i].label;
		    guide.inside = true;
		    guide.lineAlpha = 1;
		    valueAxis.addGuide(guide);
    	}
    }

	// create stock legend
	var stockLegend1 = new AmCharts.StockLegend();
	stockLegend1.valueTextRegular = " ";
	stockLegend1.markerType = "none";
	stockPanel1.stockLegend = stockLegend1;


	// second stock panel
	var stockPanel2 = new AmCharts.StockPanel();
	stockPanel2.title = "成交量";
	stockPanel2.percentHeight = 30;
	var graph2 = new AmCharts.StockGraph();
	graph2.valueField = "vol";
	graph2.type = "column";
	graph2.colorField = "color";
	graph2.fillAlphas = 1;
	graph2.balloonFunction =　function(a,b){
		var volume = a.values.value;//成交量
		if(volume){
			if(volume/1E8>1){
				return (volume / 1E8).toFixed(2) + "亿";
			}else if (volume / 1E4 > 1) {
				return (volume / 1E4).toFixed(2) + "万";
			}
		}else{
			return '';
		}
	};
	stockPanel2.addStockGraph(graph2);
	var valueAxis2 = new AmCharts.ValueAxis();
	valueAxis2.axisColor = "#DADADA";
	valueAxis2.dashLength = 1; 
	valueAxis2.labelFunction = function(valueText, date, valueAxis){
		var colume = parseInt(valueText);
		if(colume){
			if(colume/1E8>1){
				return (colume / 1E8).toFixed(2) + "亿";
			}else if (colume / 1E4 > 1) {
				return (colume / 1E4).toFixed(2) + "万";
			}else{
				return valueText;
			}
		}else{
			return '';
		}
	};
	stockPanel2.addValueAxis(valueAxis2);
	// create stock legend
	var stockLegend2 = new AmCharts.StockLegend();
	stockLegend2.valueTextRegular = " ";
	stockLegend2.markerType = "none";
	stockPanel2.stockLegend = stockLegend2;

	// set panels to the chart
	chart.panels = [stockPanel1, stockPanel2];


	// 下方的滚动查询
	var scrollbarSettings = new AmCharts.ChartScrollbarSettings();
	scrollbarSettings.graph = graph1;
	scrollbarSettings.enable = false;
	scrollbarSettings.height = "0";
	chart.chartScrollbarSettings = scrollbarSettings;
	
	//标针样式设置
	var cursorSettings = new AmCharts.ChartCursorSettings();
	cursorSettings.valueBalloonsEnabled = true;
	cursorSettings.graphBulletSize = 1;
	cursorSettings.cursorPosition = "middle";
	cursorSettings.categoryBalloonDateFormats = [{period:"YYYY", format:"YYYY"}, {period:"MM", format:"YYYY-MM"}, {period:"WW", format:"YYYY-MM-DD"}, {period:"DD", format:"YYYY-MM-DD"}];
	chart.chartCursorSettings = cursorSettings;
	//设置横轴坐标轴样式
	var categoryAxesSettings = new AmCharts.CategoryAxesSettings();
	categoryAxesSettings.dateFormats = [{period:"YYYY", format:"YYYY"}, {period:"MM", format:"YYYY-MM"}, {period:"WW", format:"YYYY-MM-DD"}, {period:"DD", format:"YYYY-MM-DD"}];
	categoryAxesSettings.groupToPeriods = ["DD"];
	chart.categoryAxesSettings = categoryAxesSettings;
	

	//设置下面时间快捷查询样式
	var periodSelector = new AmCharts.PeriodSelector();
	periodSelector.dateFormat = "YYYY-MM-DD";
	periodSelector.fromText = "选择日期：起";
	periodSelector.toText = "至";
	periodSelector.periodsText = "";
	periodSelector.inputFieldWidth = 80;
	periodSelector.periods = [{
		period: "WW",
		count: 1,
		label: "近1周"
	}, {
		period: "MM",
		count: 1,
		label: "近1月",
		selected: true
	}, {
		period: "MM",
		count: 3,
		label: "近1季"
	}, {
		period: "YYYY",
		count: 1,
		label: "近1年"
	}, {
		period: "MAX",
		label: "全部"
	}];
	chart.periodSelector = periodSelector;
	var panelsSettings = new AmCharts.PanelsSettings();
	panelsSettings.usePrefixes = true;
	chart.panelsSettings = panelsSettings;
    chart.write(dom);
}
codestatistics.newChart = function(dom,dataProvider,params){
	var chart = new AmCharts.AmSerialChart();
    chart.pathToImages = "resources/amcharts/images/";
    chart.dataProvider = dataProvider;
    chart.marginLeft = 10;
    chart.categoryField = params['categoryField'];
    chart.dataDateFormat = params['dataDateFormat'];
    // AXES
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.dateFormats = params['dateFormats'];
    categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
    categoryAxis.minPeriod = params['minPeriod']; // our data is yearly, so we set minPeriod to YYYY
    categoryAxis.dashLength = 3;
    categoryAxis.minorGridEnabled = true;
    categoryAxis.minorGridAlpha = 0.1;

    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.axisAlpha = 0;
    valueAxis.inside = true;
    valueAxis.unit = "%";
    valueAxis.dashLength = 3;
    chart.addValueAxis(valueAxis);

    // GRAPH
    graph = new AmCharts.AmGraph();
    graph.type = "smoothedLine"; // this line makes the graph smoothed line.
    graph.lineColor = "#b0de09";
    graph.negativeLineColor = "#b0de09"; // this line makes the graph to change color when it drops below 0
    graph.bullet = "round";
    graph.bulletSize = 8;
    graph.bulletBorderColor = "#FFFFFF";
    graph.bulletBorderAlpha = 1;
    graph.bulletBorderThickness = 2;
    graph.lineThickness = 1;
    graph.valueField = params['valueField'];
    graph.balloonText = params['balloonText'];
    chart.addGraph(graph);

    // CURSOR
    var chartCursor = new AmCharts.ChartCursor();
    chartCursor.cursorAlpha = 0;
    chartCursor.cursorPosition = "mouse";
    chartCursor.categoryBalloonDateFormat = params['categoryBalloonDateFormat'];
    chart.addChartCursor(chartCursor);

    // SCROLLBAR
    //var chartScrollbar = new AmCharts.ChartScrollbar();
    //chart.addChartScrollbar(chartScrollbar);
	chart.write(dom);	
}
/**重新加载年统计**/
codestatistics.searchFun = function () {
	codestatistics.resetStoreParameters();
	
    Share.resetGrid(codestatistics.grid);
    Share.resetGrid(codestatistics.trackGrid);
    Share.resetGrid(codestatistics.incomingGrid);
    Share.resetGrid(codestatistics.czbGrid);
    Share.resetGrid(codestatistics.holderGrid);
    Share.resetGrid(codestatistics.upDownGrid);
    
    codestatistics.store.reload();
    codestatistics.trackStore.reload();
    codestatistics.incomingStore.reload();
    codestatistics.czbStore.reload();
    codestatistics.holderStore.reload();
    codestatistics.upDownStore.reload();
    
};
/**主面板*/
codestatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	tbar : codestatistics.tbar,
	layout : 'column',
	autoScroll:"true",
	boder : false,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [codestatistics.msgPanel,codestatistics.stockPanel,codestatistics.yearPanel,codestatistics.monthPanel,codestatistics.grid,codestatistics.trackGrid,codestatistics.incomingGrid,codestatistics.czbGrid,codestatistics.holderGrid,codestatistics.upDownGrid,codestatistics.contextText]
});
