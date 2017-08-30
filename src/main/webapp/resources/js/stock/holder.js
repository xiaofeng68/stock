Ext.QuickTips.init();
Ext.ns("Ext.Stock.holderstatistics"); // 自定义一个命名空间
holderstatistics = Ext.Stock.holderstatistics; // 定义命名空间的别名
holderstatistics = {
	year : ctx + '/stock/holder', // 所有用户
	holdertype:ctx+'/stock/holdertype'//股东类型
}
/** 改变页的combo*/
holderstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			holderstatistics.pageSize  = parseInt(comboBox.getValue());
			holderstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			holderstatistics.store.baseParams.limit = holderstatistics.pageSize;
			holderstatistics.store.baseParams.start = 0;
			holderstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
holderstatistics.pageSize = parseInt(holderstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
holderstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'holder',
		state:1,
		start : 0,
		limit : holderstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : holderstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'stockholder','type', 'stockcount','stock_ratio','ios','day_d','state','snum','statestr']),
	listeners : {
		'load' : function(store, records, options) {
			holderstatistics.alwaysFun();
		}
	}
});
/**十大股东类型store*/
holderstatistics.holdertypestore = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : holderstatistics.holdertype
	}),
	reader :  new Ext.data.ArrayReader({},[{name:'name',mapping:'type'}])
});
holderstatistics.alwaysFun = function() {
	Share.resetGrid(holderstatistics.grid);
};
//holderstatistics.store.load(); 
/** 基本信息-选择模式 */
holderstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
holderstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ holderstatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	} ,{
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
holderstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
holderstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                holderstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
holderstatistics.bustypeCombo = new Ext.form.ComboBox({
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
holderstatistics.formtypeCombo = new Ext.form.ComboBox({
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
holderstatistics.sosmetypeCombo = new Ext.form.ComboBox({
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
holderstatistics.newtypeCombo = new Ext.form.ComboBox({
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
holderstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
holderstatistics.stockstateCombo = new Ext.form.ComboBox({
		emptyText:'状态',
		name : 'stockstateCombo',
		width:80,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_holder_state)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
holderstatistics.stockholderCombo = new Ext.form.ComboBox({
		emptyText:'十大流通股东',
		width:140,
		listWidth:200,
		name : 'stockholderCombo',
		triggerAction : 'all',
		mode : 'local',
		store : holderstatistics.holdertypestore,
		displayField : 'name',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
holderstatistics.stockholderText = new Ext.form.TextField({
	name : 'stockholderField',
	width:80,
	emptyText:'股东名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                holderstatistics.searchFun();
            }
        }
    }
});
/** 查询 */
holderstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        holderstatistics.searchFun();
    }
});
/**清空*/
holderstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		holderstatistics.bustypeCombo.setValue(null);
		holderstatistics.formtypeCombo.setValue(null);
		holderstatistics.sosmetypeCombo.setValue(null);
		holderstatistics.newtypeCombo.setValue(null);
		holderstatistics.lcstocktypeCombo.setValue(null);
		holderstatistics.stockstateCombo.setValue(null);
		holderstatistics.stockholderCombo.setValue(null);
		holderstatistics.selectAll.checked = false;
		holderstatistics.selectAll.el.dom.checked = false;
		holderstatistics.codeText.setValue(null);
		holderstatistics.stockholderText.setValue(null);
    }
});
/** 顶部工具栏 */
holderstatistics.tbar = [holderstatistics.selectAll,holderstatistics.codeText,holderstatistics.bustypeCombo ,holderstatistics.formtypeCombo,holderstatistics.sosmetypeCombo,holderstatistics.newtypeCombo,holderstatistics.lcstocktypeCombo,holderstatistics.stockstateCombo,holderstatistics.stockholderCombo,holderstatistics.stockholderText,holderstatistics.searchAction,holderstatistics.clearAction];
/** 底部工具条 */
holderstatistics.bbar = new Ext.PagingToolbar({
	pageSize : holderstatistics.pageSize,
	store : holderstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', holderstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
holderstatistics.grid = new Ext.grid.GridPanel({
	store : holderstatistics.store,
	colModel : holderstatistics.colModel,
	selModel : holderstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:holderstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : holderstatistics.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : holderstatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
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
holderstatistics.resetStoreParameters = function(){
	var busType = holderstatistics.bustypeCombo.getValue()
	,formType = holderstatistics.formtypeCombo.getValue()
	,sosmeType = holderstatistics.sosmetypeCombo.getValue()
	,newtypeType = holderstatistics.newtypeCombo.getValue()
	,lcstockType = holderstatistics.lcstocktypeCombo.getValue()
	,stockios = holderstatistics.stockstateCombo.getValue()
	,stockholdertype = holderstatistics.stockholderCombo.getValue();
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
	holderstatistics.store.baseParams.ios = stockios?stockios:null;
	holderstatistics.store.baseParams.holdertype = stockholdertype?stockholdertype:null;
	holderstatistics.store.baseParams.stockholder = holderstatistics.stockholderText.getValue();
	holderstatistics.store.baseParams.state = holderstatistics.selectAll.checked?null:1;
	holderstatistics.store.baseParams.code = holderstatistics.codeText.getValue();
	holderstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
holderstatistics.searchFun = function () {
	holderstatistics.resetStoreParameters();
    holderstatistics.alwaysFun();
    holderstatistics.store.reload();
};
/**主面板*/
holderstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ holderstatistics.grid ]
});