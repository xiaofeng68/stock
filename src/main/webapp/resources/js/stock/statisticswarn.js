Ext.ns("Ext.Stock.warnwindow"); // 自定义一个命名空间
warnwindow = Ext.Stock.warnwindow; // 定义命名空间的别名
warnwindow = {
	year : ctx + '/stock/warnwindow' // 所有用户
}
/** 改变页的combo*/
warnwindow.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			warnwindow.pageSize  = parseInt(comboBox.getValue());
			warnwindow.bbar.pageSize  = parseInt(comboBox.getValue());
			warnwindow.store.baseParams.limit = warnwindow.pageSize;
			warnwindow.store.baseParams.start = 0;
			warnwindow.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
warnwindow.pageSize = parseInt(warnwindow.pageSizeCombo.getValue());
/** 基本信息-数据源 */
warnwindow.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:false,
	baseParams : {
		type:'warnwindow',
		state:1,
		start : 0,
		limit : warnwindow.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : warnwindow.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus','num','sumn','sumn2','zhang','statestr','snum' ])
});
warnwindow.alwaysFun = function() {
	Share.resetGrid(warnwindow.grid);
};
//warnwindow.store.load(); 
/** 基本信息-选择模式 */
warnwindow.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
warnwindow.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ warnwindow.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	}, {
		header : '狙击次数',
		dataIndex : 'num'
	}, {
		header : '狙击总数',
		dataIndex : 'sumn'
	}, {
		header : '雪豹总数',
		dataIndex : 'sumn2'
	}, {
		header : '涨幅',
		dataIndex : 'zhang',
		renderer:function(value){
			return value.toFixed(2);
		}
	}]
});
/**994**/
warnwindow.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/** 行业板块类型*/
warnwindow.bustypeCombo = new Ext.form.ComboBox({
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
warnwindow.timeUtil = new DateTimeUtil();
warnwindow.startDateLabel = new Ext.form.Label({
	text:'起',
	anchor : '99%'
});
warnwindow.startDate = new Ext.form.DateField({  
    fieldLabel : '开始日期',  
    emptyText : '请选择',  
    labelWidth : 100,  
    allowBlank : false,  
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:warnwindow.timeUtil.getCurrentWeek()[0],
    anchor : '99%'
})  
warnwindow.endDateLabel = new Ext.form.Label({
	text:'止',
	anchor : '99%'
});
warnwindow.endDate = new Ext.form.DateField({  
    fieldLabel : '结束日期',  
    emptyText : '请选择',  
    allowBlank : false,  
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:warnwindow.timeUtil.getCurrentWeek()[1],
    anchor : '99%'
})  
/**统计天数*/
warnwindow.warnnumLabel = new Ext.form.Label({
	text:'统计天数',
	anchor : '99%'
});
/**统计天数*/
warnwindow.warnnum = new Ext.form.NumberField({
	minValue:1,
	width:40,
	value:3
});
/** 查询 */
warnwindow.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        warnwindow.searchFun();
    }
});
/**清空*/
warnwindow.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		warnwindow.bustypeCombo.setValue(null);
		warnwindow.selectAll.checked = false;
		warnwindow.selectAll.el.dom.checked = false;
		warnwindow.startDate.setValue(warnwindow.timeUtil.getCurrentWeek()[0]);
		warnwindow.endDate.setValue(warnwindow.timeUtil.getCurrentWeek()[1]);
    }
});
/** 顶部工具栏 */
warnwindow.tbar = [warnwindow.selectAll,warnwindow.bustypeCombo ,warnwindow.startDateLabel,warnwindow.startDate,warnwindow.endDateLabel,warnwindow.endDate,warnwindow.warnnumLabel,warnwindow.warnnum,warnwindow.searchAction,warnwindow.clearAction];
/** 底部工具条 */
warnwindow.bbar = new Ext.PagingToolbar({
	pageSize : warnwindow.pageSize,
	store : warnwindow.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', warnwindow.pageSizeCombo ]
});
/** 基本信息-表格 */
warnwindow.grid = new Ext.grid.GridPanel({
	store : warnwindow.store,
	colModel : warnwindow.colModel,
	selModel : warnwindow.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:warnwindow.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : warnwindow.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : warnwindow.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
	bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
	loadMask : true,
	layout : 'fit',//自适应布局
	viewConfig:{
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
warnwindow.resetStoreParameters = function(){
	var busType = warnwindow.bustypeCombo.getValue();
	var types = [];
	if(busType){
		types.push(busType);
	}
	warnwindow.store.baseParams.warndays = warnwindow.warnnum.getValue();
	warnwindow.store.baseParams.startDate = Ext.util.Format.date(warnwindow.startDate.getValue(), 'Y-m-d');
	warnwindow.store.baseParams.endDate = Ext.util.Format.date(warnwindow.endDate.getValue(), 'Y-m-d');
	warnwindow.store.baseParams.state = warnwindow.selectAll.checked?null:1;
	warnwindow.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
warnwindow.searchFun = function () {
	warnwindow.resetStoreParameters();
    warnwindow.alwaysFun();
    warnwindow.store.reload();
};
/**主面窗口*/
warnwindow.window = new Ext.Window({
    layout: 'fit',
    width: 700,
    height: 540,
    closeAction: 'hide',
    plain: true,
    modal: true,
    resizable: true,
    items: [warnwindow.grid],
	listeners : {
		'afterender' : function(store, records, options) {
			warnwindow.alwaysFun();
		}
	}
});