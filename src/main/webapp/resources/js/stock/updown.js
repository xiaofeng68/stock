Ext.QuickTips.init();
Ext.ns("Ext.Stock.updownstatistics"); // 自定义一个命名空间
updownstatistics = Ext.Stock.updownstatistics; // 定义命名空间的别名
updownstatistics = {
	year : ctx + '/stock/updown' // 所有用户
}
/** 改变页的combo*/
updownstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			updownstatistics.pageSize  = parseInt(comboBox.getValue());
			updownstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			updownstatistics.store.baseParams.limit = updownstatistics.pageSize;
			updownstatistics.store.baseParams.start = 0;
			updownstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
updownstatistics.pageSize = parseInt(updownstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
updownstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'updown',
		state:1,
		start : 0,
		limit : updownstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : updownstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'num','statestr','snum','sumn' ]),
	listeners : {
		'load' : function(store, records, options) {
			updownstatistics.alwaysFun();
		}
	}
});
updownstatistics.alwaysFun = function() {
	Share.resetGrid(updownstatistics.grid);
};
//updownstatistics.store.load(); 
/** 基本信息-选择模式 */
updownstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
updownstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ updownstatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	} , {
		header : '活跃天数',
		dataIndex : 'num'
	}, {
		header : '狙击总数',
		dataIndex : 'sumn'
	}, {
		header : '板块数量',
		dataIndex : 'snum'
	}, {
		header : '自定义描述',
		dataIndex : 'statestr',
		width:600,
		renderer: function (value) {
            return Share.getStockDescription(stocks_type,value);
        }
	} ]
});
/**994**/
updownstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
updownstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                updownstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
updownstatistics.bustypeCombo = new Ext.form.ComboBox({
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
updownstatistics.formtypeCombo = new Ext.form.ComboBox({
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
updownstatistics.sosmetypeCombo = new Ext.form.ComboBox({
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
updownstatistics.newtypeCombo = new Ext.form.ComboBox({
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
updownstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
updownstatistics.stockupdownCombo = new Ext.form.ComboBox({
		emptyText:'涨幅',
		name : 'stockupdownCombo',
		width:80,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_updown)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
updownstatistics.timeUtil = new DateTimeUtil();
updownstatistics.startDateLabel = new Ext.form.Label({
	text:'开始',
	anchor : '99%'
});
updownstatistics.startDate = new Ext.form.DateField({  
    fieldLabel : '开始日期',  
    emptyText : '请选择',  
    labelWidth : 100,  
    allowBlank : false,  
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:updownstatistics.timeUtil.getCurrentMonth()[0],
    anchor : '99%'
})  
updownstatistics.endDateLabel = new Ext.form.Label({
	text:'结束',
	anchor : '99%'
});
updownstatistics.endDate = new Ext.form.DateField({  
    fieldLabel : '结束日期',  
    emptyText : '请选择',  
    allowBlank : false,  
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:updownstatistics.timeUtil.getCurrentMonth()[1],
    anchor : '99%'
})  

/** 查询 */
updownstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        updownstatistics.searchFun();
    }
});
/**清空*/
updownstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		updownstatistics.bustypeCombo.setValue(null);
		updownstatistics.formtypeCombo.setValue(null);
		updownstatistics.sosmetypeCombo.setValue(null);
		updownstatistics.newtypeCombo.setValue(null);
		updownstatistics.lcstocktypeCombo.setValue(null);
		updownstatistics.stockupdownCombo.setValue(null);
		updownstatistics.selectAll.checked = false;
		updownstatistics.selectAll.el.dom.checked = false;
		updownstatistics.codeText.setValue(null);
		updownstatistics.startDate.setValue(updownstatistics.timeUtil.getCurrentMonth()[0]);
		updownstatistics.endDate.setValue(updownstatistics.timeUtil.getCurrentMonth()[1]);
    }
});
/** 顶部工具栏 */
updownstatistics.tbar = [updownstatistics.selectAll,updownstatistics.codeText,updownstatistics.bustypeCombo ,updownstatistics.formtypeCombo,updownstatistics.sosmetypeCombo,updownstatistics.newtypeCombo,updownstatistics.lcstocktypeCombo,updownstatistics.stockupdownCombo,updownstatistics.startDateLabel,updownstatistics.startDate,updownstatistics.endDateLabel,updownstatistics.endDate,updownstatistics.searchAction,updownstatistics.clearAction];
/** 底部工具条 */
updownstatistics.bbar = new Ext.PagingToolbar({
	pageSize : updownstatistics.pageSize,
	store : updownstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', updownstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
updownstatistics.grid = new Ext.grid.GridPanel({
	store : updownstatistics.store,
	colModel : updownstatistics.colModel,
	selModel : updownstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:updownstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : updownstatistics.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : updownstatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
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
updownstatistics.resetStoreParameters = function(){
	var busType = updownstatistics.bustypeCombo.getValue()
	,formType = updownstatistics.formtypeCombo.getValue()
	,sosmeType = updownstatistics.sosmetypeCombo.getValue()
	,newtypeType = updownstatistics.newtypeCombo.getValue()
	,lcstockType = updownstatistics.lcstocktypeCombo.getValue()
	,ups = updownstatistics.stockupdownCombo.getValue();
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
	updownstatistics.store.baseParams.ups = ups?ups:null;
	updownstatistics.store.baseParams.startDate = Ext.util.Format.date(updownstatistics.startDate.getValue(), 'Y-m-d');
	updownstatistics.store.baseParams.endDate = Ext.util.Format.date(updownstatistics.endDate.getValue(), 'Y-m-d');
	updownstatistics.store.baseParams.state = updownstatistics.selectAll.checked?null:1;
	updownstatistics.store.baseParams.code = updownstatistics.codeText.getValue();
	updownstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
updownstatistics.searchFun = function () {
	updownstatistics.resetStoreParameters();
    updownstatistics.alwaysFun();
    updownstatistics.store.reload();
};
/**主面板*/
updownstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ updownstatistics.grid ]
});