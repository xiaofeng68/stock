Ext.QuickTips.init();
Ext.ns("Ext.Stock.warnstatistics"); // 自定义一个命名空间
warnstatistics = Ext.Stock.warnstatistics; // 定义命名空间的别名
warnstatistics = {
	year : ctx + '/stock/warn' // 所有用户
}
/** 改变页的combo*/
warnstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			warnstatistics.pageSize  = parseInt(comboBox.getValue());
			warnstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			warnstatistics.store.baseParams.limit = warnstatistics.pageSize;
			warnstatistics.store.baseParams.start = 0;
			warnstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
warnstatistics.pageSize = parseInt(warnstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
warnstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'warn',
		state:1,
		start : 0,
		limit : warnstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : warnstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'day_d','times', 'minprice','yclose','zhang','capital','tov','sumprice','uddays','hhv', 'hhv_date', 'llv', 'llv_date', 'v_days','statestr','snum' ]),
	listeners : {
		'load' : function(store, records, options) {
			warnstatistics.alwaysFun();
		}
	}
});
warnstatistics.alwaysFun = function() {
	Share.resetGrid(warnstatistics.grid);
};
//warnstatistics.store.load(); 
/** 基本信息-选择模式 */
warnstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
warnstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ warnstatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	} ,{
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
		dataIndex : 'v_days'
	}, {
		header : '预警时间',
		dataIndex : 'day_d',
		width:200,
		renderer: function (value, metadata, record) {
            var obj = record.data;
            return obj.day_d+' '+obj.times;
        }
	}, {
		header : '当日最低价',
		dataIndex : 'minprice',
		width:100
	}, {
		header : '昨日收盘价',
		dataIndex : 'yclose',
		width:100
	}, {
		header : '当日涨幅',
		dataIndex : 'zhang'
	}, {
		header : '流通股',
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
		},width:110
	}, {
		header : '连涨/跌',
		dataIndex : 'uddays'
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
warnstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
warnstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                warnstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
warnstatistics.bustypeCombo = new Ext.form.ComboBox({
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
warnstatistics.formtypeCombo = new Ext.form.ComboBox({
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
warnstatistics.sosmetypeCombo = new Ext.form.ComboBox({
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
warnstatistics.newtypeCombo = new Ext.form.ComboBox({
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
warnstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
warnstatistics.startDateLabel = new Ext.form.Label({
	text:'开始',
	anchor : '99%'
});
warnstatistics.startDate = new Ext.form.DateField({  
    fieldLabel : '开始日期',  
    emptyText : '请选择',  
    labelWidth : 100,  
    width:110,
    allowBlank : false,  
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:new Date(),
    anchor : '99%'
})  
warnstatistics.endDateLabel = new Ext.form.Label({
	text:'结束',
	anchor : '99%'
});
warnstatistics.endDate = new Ext.form.DateField({  
    fieldLabel : '结束日期',  
    emptyText : '请选择',
    width:110,
    allowBlank : false,  
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:new Date(),
    anchor : '99%'
})  

/** 查询 */
warnstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        warnstatistics.searchFun();
    }
});
/**清空*/
warnstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		warnstatistics.bustypeCombo.setValue(null);
		warnstatistics.formtypeCombo.setValue(null);
		warnstatistics.sosmetypeCombo.setValue(null);
		warnstatistics.newtypeCombo.setValue(null);
		warnstatistics.lcstocktypeCombo.setValue(null);
		warnstatistics.selectAll.checked = false;
		warnstatistics.selectAll.el.dom.checked = false;
		warnstatistics.codeText.setValue(null);
		warnstatistics.startDate.setValue(new Date());
		warnstatistics.endDate.setValue(new Date());
    }
});
/** 统计 */
warnstatistics.statisticsAction = new Ext.Action({
    text: '统计',
    handler: function () {
        warnwindow.window.setIconClass('role_edit'); // 设置窗口的样式
        warnwindow.window.setTitle('量能预警统计'); // 设置窗口的名称
        warnwindow.window.show().center();
    }
});
/** 顶部工具栏 */
warnstatistics.tbar = [warnstatistics.selectAll,warnstatistics.codeText,warnstatistics.bustypeCombo ,warnstatistics.formtypeCombo,warnstatistics.sosmetypeCombo,warnstatistics.newtypeCombo,warnstatistics.lcstocktypeCombo,warnstatistics.startDateLabel,warnstatistics.startDate,warnstatistics.endDateLabel,warnstatistics.endDate,warnstatistics.searchAction,warnstatistics.clearAction,warnstatistics.statisticsAction];
/** 底部工具条 */
warnstatistics.bbar = new Ext.PagingToolbar({
	pageSize : warnstatistics.pageSize,
	store : warnstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', warnstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
warnstatistics.grid = new Ext.grid.GridPanel({
	store : warnstatistics.store,
	colModel : warnstatistics.colModel,
	selModel : warnstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:warnstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : warnstatistics.bbar,
//	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : warnstatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
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
warnstatistics.resetStoreParameters = function(){
	var busType = warnstatistics.bustypeCombo.getValue()
	,formType = warnstatistics.formtypeCombo.getValue()
	,sosmeType = warnstatistics.sosmetypeCombo.getValue()
	,newtypeType = warnstatistics.newtypeCombo.getValue()
	,lcstockType = warnstatistics.lcstocktypeCombo.getValue()
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
	warnstatistics.store.baseParams.startDate = Ext.util.Format.date(warnstatistics.startDate.getValue(), 'Y-m-d');
	warnstatistics.store.baseParams.endDate = Ext.util.Format.date(warnstatistics.endDate.getValue(), 'Y-m-d');
	warnstatistics.store.baseParams.state = warnstatistics.selectAll.checked?null:1;
	warnstatistics.store.baseParams.code = warnstatistics.codeText.getValue();
	warnstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
warnstatistics.searchFun = function () {
	warnstatistics.resetStoreParameters();
    warnstatistics.alwaysFun();
    warnstatistics.store.reload();
};
/**主面板*/
warnstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ warnstatistics.grid ]
});
