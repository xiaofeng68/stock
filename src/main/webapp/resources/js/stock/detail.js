Ext.QuickTips.init();
Ext.ns("Ext.Stock.detailstatistics"); // 自定义一个命名空间
detailstatistics = Ext.Stock.detailstatistics; // 定义命名空间的别名
detailstatistics = {
	year : ctx + '/stock/detail' // 所有用户
}
/** 改变页的combo*/
detailstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			detailstatistics.pageSize  = parseInt(comboBox.getValue());
			detailstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			detailstatistics.store.baseParams.limit = detailstatistics.pageSize;
			detailstatistics.store.baseParams.start = 0;
			detailstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
detailstatistics.pageSize = parseInt(detailstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
detailstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'detail',
		state:1,
		start : 0,
		limit : detailstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : detailstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'statestr','snum','oldname' ]),
	listeners : {
		'load' : function(store, records, options) {
			detailstatistics.alwaysFun();
		}
	}
});
detailstatistics.alwaysFun = function() {
	Share.resetGrid(detailstatistics.grid);
};
//detailstatistics.store.load(); 
/** 基本信息-选择模式 */
detailstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
detailstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ detailstatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	} ,{
		header : '股名历史',
		dataIndex : 'oldname',
		width:430
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
detailstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
detailstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                detailstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
detailstatistics.bustypeCombo = new Ext.form.ComboBox({
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
detailstatistics.formtypeCombo = new Ext.form.ComboBox({
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
detailstatistics.sosmetypeCombo = new Ext.form.ComboBox({
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
detailstatistics.newtypeCombo = new Ext.form.ComboBox({
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
detailstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
detailstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        detailstatistics.searchFun();
    }
});
/**清空*/
detailstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		detailstatistics.bustypeCombo.setValue(null);
		detailstatistics.formtypeCombo.setValue(null);
		detailstatistics.sosmetypeCombo.setValue(null);
		detailstatistics.newtypeCombo.setValue(null);
		detailstatistics.lcstocktypeCombo.setValue(null);
		detailstatistics.selectAll.checked = false;
		detailstatistics.selectAll.el.dom.checked = false;
		detailstatistics.codeText.setValue(null);
    }
});
/** 顶部工具栏 */
detailstatistics.tbar = [detailstatistics.selectAll,detailstatistics.codeText,detailstatistics.bustypeCombo ,detailstatistics.formtypeCombo,detailstatistics.sosmetypeCombo,detailstatistics.newtypeCombo,detailstatistics.lcstocktypeCombo,detailstatistics.searchAction,detailstatistics.clearAction];
/** 底部工具条 */
detailstatistics.bbar = new Ext.PagingToolbar({
	pageSize : detailstatistics.pageSize,
	store : detailstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', detailstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
detailstatistics.grid = new Ext.grid.GridPanel({
	store : detailstatistics.store,
	colModel : detailstatistics.colModel,
	selModel : detailstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:detailstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : detailstatistics.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : detailstatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
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
detailstatistics.resetStoreParameters = function(){
	var busType = detailstatistics.bustypeCombo.getValue()
	,formType = detailstatistics.formtypeCombo.getValue()
	,sosmeType = detailstatistics.sosmetypeCombo.getValue()
	,newtypeType = detailstatistics.newtypeCombo.getValue()
	,lcstockType = detailstatistics.lcstocktypeCombo.getValue();
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
	detailstatistics.store.baseParams.state = detailstatistics.selectAll.checked?null:1;
	detailstatistics.store.baseParams.code = detailstatistics.codeText.getValue();
	detailstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
detailstatistics.searchFun = function () {
	detailstatistics.resetStoreParameters();
    detailstatistics.alwaysFun();
    detailstatistics.store.reload();
};
/**主面板*/
detailstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ detailstatistics.grid ]
});