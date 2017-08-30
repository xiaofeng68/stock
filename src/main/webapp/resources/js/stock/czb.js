Ext.QuickTips.init();
Ext.ns("Ext.Stock.czbstatistics"); // 自定义一个命名空间
czbstatistics = Ext.Stock.czbstatistics; // 定义命名空间的别名
czbstatistics = {
	year : ctx + '/stock/czb' // 所有用户
}
/** 改变页的combo*/
czbstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			czbstatistics.pageSize  = parseInt(comboBox.getValue());
			czbstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			czbstatistics.store.baseParams.limit = czbstatistics.pageSize;
			czbstatistics.store.baseParams.start = 0;
			czbstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
czbstatistics.pageSize = parseInt(czbstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
czbstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'czb',
		state:1,
		start : 0,
		limit : czbstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : czbstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'statestr','snum','day_d','buy_p','sell_p','type_code' ]),
	listeners : {
		'load' : function(store, records, options) {
			czbstatistics.alwaysFun();
		}
	}
});
czbstatistics.alwaysFun = function() {
	Share.resetGrid(czbstatistics.grid);
};
//czbstatistics.store.load(); 
/** 基本信息-选择模式 */
czbstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
czbstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ czbstatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	} ,{
		header : '交易日期',
		dataIndex : 'day_d',
		width:110
	}, {
		header : '营业部买入(万)',
		dataIndex : 'buy_p',
		width:160
	}, {
		header : '营业部卖出(万)',
		dataIndex : 'sell_p',
		width:160
	}, {
		header : '营业部',
		dataIndex : 'type_code',
		width:160,
		renderer:function(value){
			return stock_czb[value];
		}
	}, {
		header : '板块数量',
		dataIndex : 'snum'
	}, {
		header : '自定义描述',
		dataIndex : 'statestr',
		width:430,
		renderer: function (value) {
            return Share.getStockDescription(stocks_type,value);
        }
	} ]
});
/**994**/
czbstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
czbstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                czbstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
czbstatistics.bustypeCombo = new Ext.form.ComboBox({
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
czbstatistics.formtypeCombo = new Ext.form.ComboBox({
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
czbstatistics.sosmetypeCombo = new Ext.form.ComboBox({
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
czbstatistics.newtypeCombo = new Ext.form.ComboBox({
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
czbstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
czbstatistics.stockczbCombo = new Ext.form.ComboBox({
		emptyText:'财政营业部',
		name : 'stockczbCombo',
		width:150,
		listWidth:200,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_czb)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
czbstatistics.timeUtil = new DateTimeUtil();
czbstatistics.startDateLabel = new Ext.form.Label({
	text:'开始',
	anchor : '99%'
});
czbstatistics.startDate = new Ext.form.DateField({  
    fieldLabel : '开始日期',  
    emptyText : '请选择',  
    labelWidth : 100, 
    width:110,
    allowBlank : false,  
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:czbstatistics.timeUtil.getCurrentSeason()[0],
    anchor : '99%'
})  
czbstatistics.endDateLabel = new Ext.form.Label({
	text:'结束',
	anchor : '99%'
});
czbstatistics.endDate = new Ext.form.DateField({  
    fieldLabel : '结束日期',  
    emptyText : '请选择',
    width:110,
    allowBlank : false,  
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:czbstatistics.timeUtil.getCurrentSeason()[1],
    anchor : '99%'
})  

/** 查询 */
czbstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        czbstatistics.searchFun();
    }
});
/**清空*/
czbstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		czbstatistics.bustypeCombo.setValue(null);
		czbstatistics.formtypeCombo.setValue(null);
		czbstatistics.sosmetypeCombo.setValue(null);
		czbstatistics.newtypeCombo.setValue(null);
		czbstatistics.lcstocktypeCombo.setValue(null);
		czbstatistics.stockczbCombo.setValue(null);
		czbstatistics.selectAll.checked = false;
		czbstatistics.selectAll.el.dom.checked = false;
		czbstatistics.codeText.setValue(null);
		czbstatistics.startDate.setValue(czbstatistics.timeUtil.getCurrentSeason()[0]);
		czbstatistics.endDate.setValue(czbstatistics.timeUtil.getCurrentSeason()[1]);
    }
});
/** 顶部工具栏 */
czbstatistics.tbar = [czbstatistics.selectAll,czbstatistics.codeText,czbstatistics.bustypeCombo ,czbstatistics.formtypeCombo,czbstatistics.sosmetypeCombo,czbstatistics.newtypeCombo,czbstatistics.lcstocktypeCombo,czbstatistics.stockczbCombo,czbstatistics.startDateLabel,czbstatistics.startDate,czbstatistics.endDateLabel,czbstatistics.endDate,czbstatistics.searchAction,czbstatistics.clearAction];
/** 底部工具条 */
czbstatistics.bbar = new Ext.PagingToolbar({
	pageSize : czbstatistics.pageSize,
	store : czbstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', czbstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
czbstatistics.grid = new Ext.grid.GridPanel({
	store : czbstatistics.store,
	colModel : czbstatistics.colModel,
	selModel : czbstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:true,  
        items:czbstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : czbstatistics.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : czbstatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
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
czbstatistics.resetStoreParameters = function(){
	var busType = czbstatistics.bustypeCombo.getValue()
	,formType = czbstatistics.formtypeCombo.getValue()
	,sosmeType = czbstatistics.sosmetypeCombo.getValue()
	,newtypeType = czbstatistics.newtypeCombo.getValue()
	,lcstockType = czbstatistics.lcstocktypeCombo.getValue()
	,stockczb = czbstatistics.stockczbCombo.getValue();
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
	czbstatistics.store.baseParams.stockczb = stockczb?stockczb:null;
	czbstatistics.store.baseParams.startDate = Ext.util.Format.date(czbstatistics.startDate.getValue(), 'Y-m-d');
	czbstatistics.store.baseParams.endDate = Ext.util.Format.date(czbstatistics.endDate.getValue(), 'Y-m-d');
	czbstatistics.store.baseParams.state = czbstatistics.selectAll.checked?null:1;
	czbstatistics.store.baseParams.code = czbstatistics.codeText.getValue();
	czbstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
czbstatistics.searchFun = function () {
	czbstatistics.resetStoreParameters();
    czbstatistics.alwaysFun();
    czbstatistics.store.reload();
};
/**主面板*/
czbstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ czbstatistics.grid ]
});