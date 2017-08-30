Ext.QuickTips.init();
Ext.ns("Ext.Stock.restrictedstatistics"); // 自定义一个命名空间
restrictedstatistics = Ext.Stock.restrictedstatistics; // 定义命名空间的别名
restrictedstatistics = {
	year : ctx + '/stock/restricted', // 所有用户
	restrictedtype:ctx+'/stock/restrictedtype'
}
/** 改变页的combo*/
restrictedstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			restrictedstatistics.pageSize  = parseInt(comboBox.getValue());
			restrictedstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			restrictedstatistics.store.baseParams.limit = restrictedstatistics.pageSize;
			restrictedstatistics.store.baseParams.start = 0;
			restrictedstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
restrictedstatistics.pageSize = parseInt(restrictedstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
restrictedstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'restricted',
		state:1,
		start : 0,
		limit : restrictedstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : restrictedstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'track','days', 'day_d','allcount','type','tov','toa','statestr','snum']),
	listeners : {
		'load' : function(store, records, options) {
			restrictedstatistics.alwaysFun();
		}
	}
});
/**限售股类型*/
restrictedstatistics.restrictedtypestore = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : restrictedstatistics.restrictedtype
	}),
	reader :  new Ext.data.ArrayReader({},[{name:'name',mapping:'type'}])
});
restrictedstatistics.alwaysFun = function() {
	Share.resetGrid(restrictedstatistics.grid);
};
//restrictedstatistics.store.load(); 
/** 基本信息-选择模式 */
restrictedstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
restrictedstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ restrictedstatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	} ,{
		header : '距离解禁天数',
		width:110,
		dataIndex : 'days'
	}, {
		header : '解禁日期',
		dataIndex : 'day_d',
		width:110
	}, {
		header : '上市股份数量(股)',
		width:120,
		dataIndex : 'allcount',
		renderer:function(value){
			if (value / 1E8 > 1) {
				return (value / 1E8).toFixed(2) + "亿股";
			} else if (value / 1E4 > 1) {
				return (value / 1E4).toFixed(2) + "万股";
			} else {
				return value.toFixed(2) + "股";
			}
		}
	}, {
		header : '上市股份类型',
		dataIndex : 'type',
		width:140
	}, {
		header : '占已流通数量比例(%)',
		dataIndex : 'tov',
		width:140
	}, {
		header : '占总股本比例(%)',
		dataIndex : 'toa',
		width:130
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
restrictedstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
restrictedstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                restrictedstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
restrictedstatistics.bustypeCombo = new Ext.form.ComboBox({
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
restrictedstatistics.formtypeCombo = new Ext.form.ComboBox({
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
restrictedstatistics.sosmetypeCombo = new Ext.form.ComboBox({
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
restrictedstatistics.newtypeCombo = new Ext.form.ComboBox({
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
restrictedstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
restrictedstatistics.stocktypeCombo = new Ext.form.ComboBox({
		emptyText:'上市股份类型',
		name : 'stocktypeCombo',
		width:140,
		listWidth:350,
		triggerAction : 'all',
		mode : 'local',
		store : restrictedstatistics.restrictedtypestore,
		valueField : 'name',
		displayField : 'name',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
restrictedstatistics.timeUtil = new DateTimeUtil();
restrictedstatistics.startDateLabel = new Ext.form.Label({
	text:'开始',
	anchor : '99%'
});
restrictedstatistics.startDate = new Ext.form.DateField({  
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
restrictedstatistics.endDateLabel = new Ext.form.Label({
	text:'结束',
	anchor : '99%'
});
restrictedstatistics.endDate = new Ext.form.DateField({  
    fieldLabel : '结束日期',  
    emptyText : '请选择',
    width:110,
    allowBlank : false,  
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:restrictedstatistics.timeUtil.getNextMonthday(new Date(),3),
    anchor : '99%'
})  

/** 查询 */
restrictedstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        restrictedstatistics.searchFun();
    }
});
/**清空*/
restrictedstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		restrictedstatistics.bustypeCombo.setValue(null);
		restrictedstatistics.formtypeCombo.setValue(null);
		restrictedstatistics.sosmetypeCombo.setValue(null);
		restrictedstatistics.newtypeCombo.setValue(null);
		restrictedstatistics.lcstocktypeCombo.setValue(null);
		restrictedstatistics.stocktypeCombo.setValue(null);
		restrictedstatistics.selectAll.checked = false;
		restrictedstatistics.selectAll.el.dom.checked = false;
		restrictedstatistics.codeText.setValue(null);
		restrictedstatistics.startDate.setValue(new Date());
		restrictedstatistics.endDate.setValue(restrictedstatistics.timeUtil.getNextMonthday(new Date(),3));
    }
});
/** 顶部工具栏 */
restrictedstatistics.tbar = [restrictedstatistics.selectAll,restrictedstatistics.codeText,restrictedstatistics.bustypeCombo ,restrictedstatistics.formtypeCombo,restrictedstatistics.sosmetypeCombo,restrictedstatistics.newtypeCombo,restrictedstatistics.lcstocktypeCombo,restrictedstatistics.stocktypeCombo,restrictedstatistics.startDateLabel,restrictedstatistics.startDate,restrictedstatistics.endDateLabel,restrictedstatistics.endDate,restrictedstatistics.searchAction,restrictedstatistics.clearAction];
/** 底部工具条 */
restrictedstatistics.bbar = new Ext.PagingToolbar({
	pageSize : restrictedstatistics.pageSize,
	store : restrictedstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', restrictedstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
restrictedstatistics.grid = new Ext.grid.GridPanel({
	store : restrictedstatistics.store,
	colModel : restrictedstatistics.colModel,
	selModel : restrictedstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:restrictedstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : restrictedstatistics.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : restrictedstatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
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
restrictedstatistics.resetStoreParameters = function(){
	var busType = restrictedstatistics.bustypeCombo.getValue()
	,formType = restrictedstatistics.formtypeCombo.getValue()
	,sosmeType = restrictedstatistics.sosmetypeCombo.getValue()
	,newtypeType = restrictedstatistics.newtypeCombo.getValue()
	,lcstockType = restrictedstatistics.lcstocktypeCombo.getValue()
	,stockrestrictedtype = restrictedstatistics.stocktypeCombo.getValue();
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
	restrictedstatistics.store.baseParams.stockrestrictedtype = stockrestrictedtype?stockrestrictedtype:null;
	restrictedstatistics.store.baseParams.startDate = Ext.util.Format.date(restrictedstatistics.startDate.getValue(), 'Y-m-d');
	restrictedstatistics.store.baseParams.endDate = Ext.util.Format.date(restrictedstatistics.endDate.getValue(), 'Y-m-d');
	restrictedstatistics.store.baseParams.state = restrictedstatistics.selectAll.checked?null:1;
	restrictedstatistics.store.baseParams.code = restrictedstatistics.codeText.getValue();
	restrictedstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
restrictedstatistics.searchFun = function () {
	restrictedstatistics.resetStoreParameters();
    restrictedstatistics.alwaysFun();
    restrictedstatistics.store.reload();
};
/**主面板*/
restrictedstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ restrictedstatistics.grid ]
});