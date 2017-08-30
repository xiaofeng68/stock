Ext.QuickTips.init();
Ext.ns("Ext.Stock.trackstatistics"); // 自定义一个命名空间
trackstatistics = Ext.Stock.trackstatistics; // 定义命名空间的别名
trackstatistics = {
	year : ctx + '/stock/track' // 所有用户
}
/** 改变页的combo*/
trackstatistics.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			trackstatistics.pageSize  = parseInt(comboBox.getValue());
			trackstatistics.bbar.pageSize  = parseInt(comboBox.getValue());
			trackstatistics.store.baseParams.limit = trackstatistics.pageSize;
			trackstatistics.store.baseParams.start = 0;
			trackstatistics.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
trackstatistics.pageSize = parseInt(trackstatistics.pageSizeCombo.getValue());
/** 基本信息-数据源 */
trackstatistics.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {
		type:'track',
		state:1,
		start : 0,
		limit : trackstatistics.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : trackstatistics.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'code', 'name', 'bus', 'track','days', 'track_ud','nprice','zhang','uddays','num','statestr','snum','dwn_track_t','dwn_track_d','cen_track_t','cen_track_d','top_track_t','top_track_d','slf_track_t','slf_track_d','weekprice','monthprice','deepprice','argetprice' ]),
	listeners : {
		'load' : function(store, records, options) {
			trackstatistics.alwaysFun();
		}
	}
});
trackstatistics.alwaysFun = function() {
	Share.resetGrid(trackstatistics.grid);
};
//trackstatistics.store.load(); 
/** 基本信息-选择模式 */
trackstatistics.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
trackstatistics.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ trackstatistics.selModel, {
		header : '股票编号',
		dataIndex : 'code'
	}, {
		header : '股票名称',
		dataIndex : 'name'
	}, {
		header : '所属板块',
		dataIndex : 'bus'
	} ,{
		header : '所属轨道',
		dataIndex : 'track'
	}, {
		header : '标记区间',
		width:230,
		renderer:function(value, metadata, record){
			var str = '';
			var obj = record.data;
			if(obj['dwn_track_t']){
				str+=obj['dwn_track_t'];
			}
			if(obj['dwn_track_d']){
				str+="->"+obj['dwn_track_d']+";";
			}
			if(obj['cen_track_t']){
				str+=obj['cen_track_t'];
			}
			if(obj['cen_track_d']){
				str+="->"+obj['cen_track_d']+";";
			}
			if(obj['top_track_t']){
				str+=obj['top_track_t'];
			}
			if(obj['top_track_d']){
				str+="->"+obj['top_track_d']+";";
			}
			if(obj['slf_track_t']){
				str+=obj['slf_track_t'];
			}
			if(obj['slf_track_d']){
				str+="->"+obj['slf_track_d']+";";
			}
			if(obj['weekprice']){
				str+="   周线"+obj['weekprice'];
			}
			if(obj['monthprice']){
				str+="月线"+obj['monthprice'];
			}
			if(obj['deepprice']){
				str+="深水区"+obj['deepprice'];
			}
			if(obj['targetprice']){
				str+="目标点位"+obj['targetprice'];
			}
			return str;
		}
	}, {
		header : '预警日期',
		dataIndex : 'days',
		width:110
	}, {
		header : '轨道描述',
		dataIndex : 'track_ud',
		renderer:function(value, metadata, record){
			return value&&'null'!=value?value:'';
		}
	}, {
		header : '最新价',
		dataIndex : 'nprice'
	}, {
		header : '涨幅',
		dataIndex : 'zhang'
	}, {
		header : '量能预警次数',
		dataIndex : 'num'
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
trackstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
trackstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                trackstatistics.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
trackstatistics.bustypeCombo = new Ext.form.ComboBox({
		emptyText:'行业板块',
		name : 'bustype',
		width:90,
		listWidth:100,
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
trackstatistics.formtypeCombo = new Ext.form.ComboBox({
		emptyText:'个股形态',
		name : 'formtype',
		width:90,
		listWidth:100,
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
trackstatistics.sosmetypeCombo = new Ext.form.ComboBox({
		emptyText:'价值投资',
		name : 'sosmetype',
		width:90,
		listWidth:100,
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
trackstatistics.newtypeCombo = new Ext.form.ComboBox({
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
trackstatistics.lcstocktypeCombo = new Ext.form.ComboBox({
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
trackstatistics.stocktrackCombo = new Ext.form.ComboBox({
		emptyText:'轨道',
		name : 'stocktrackCombo',
		width:60,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_track)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
trackstatistics.stocktrackdesCombo = new Ext.form.ComboBox({
		emptyText:'轨道突破',
		name : 'stocktrackdesCombo',
		width:90,
		listWidth:90,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(stock_track_des)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
trackstatistics.startDateLabel = new Ext.form.Label({
	text:'开始',
	anchor : '99%'
});
trackstatistics.startDate = new Ext.form.DateField({  
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
trackstatistics.endDateLabel = new Ext.form.Label({
	text:'结束',
	anchor : '99%'
});
trackstatistics.endDate = new Ext.form.DateField({  
    fieldLabel : '结束日期',  
    emptyText : '请选择',  
    allowBlank : false,
    width:110,
    format : 'Y-m-d',//日期格式  
    vtype : 'daterange',
    value:new Date(),
    anchor : '99%'
})  

/** 查询 */
trackstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        trackstatistics.searchFun();
    }
});
/**清空*/
trackstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		trackstatistics.bustypeCombo.setValue(null);
		trackstatistics.formtypeCombo.setValue(null);
		trackstatistics.sosmetypeCombo.setValue(null);
		trackstatistics.newtypeCombo.setValue(null);
		trackstatistics.lcstocktypeCombo.setValue(null);
		trackstatistics.stocktrackCombo.setValue(null);
		trackstatistics.stocktrackdesCombo.setValue(null);
		trackstatistics.selectAll.checked = false;
		trackstatistics.selectAll.el.dom.checked = false;
		trackstatistics.codeText.setValue(null);
		trackstatistics.startDate.setValue(new Date());
		trackstatistics.endDate.setValue(new Date());
    }
});
/** 顶部工具栏 */
trackstatistics.tbar = [trackstatistics.selectAll,trackstatistics.codeText,trackstatistics.bustypeCombo ,trackstatistics.formtypeCombo,trackstatistics.sosmetypeCombo,trackstatistics.newtypeCombo,trackstatistics.lcstocktypeCombo,trackstatistics.stocktrackCombo,trackstatistics.stocktrackdesCombo,trackstatistics.startDateLabel,trackstatistics.startDate,trackstatistics.endDateLabel,trackstatistics.endDate,trackstatistics.searchAction,trackstatistics.clearAction];
/** 底部工具条 */
trackstatistics.bbar = new Ext.PagingToolbar({
	pageSize : trackstatistics.pageSize,
	store : trackstatistics.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', trackstatistics.pageSizeCombo ]
});
/** 基本信息-表格 */
trackstatistics.grid = new Ext.grid.GridPanel({
	store : trackstatistics.store,
	colModel : trackstatistics.colModel,
	selModel : trackstatistics.selModel,
	tbar : { //  
        xtype: 'buttongroup',  
        floatable:false,  
        items:trackstatistics.tbar,
        style: {border:'1px solid #000000' }
	},
	bbar : trackstatistics.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : trackstatistics.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
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
trackstatistics.resetStoreParameters = function(){
	var busType = trackstatistics.bustypeCombo.getValue()
	,formType = trackstatistics.formtypeCombo.getValue()
	,sosmeType = trackstatistics.sosmetypeCombo.getValue()
	,newtypeType = trackstatistics.newtypeCombo.getValue()
	,lcstockType = trackstatistics.lcstocktypeCombo.getValue()
	,stocktrack = trackstatistics.stocktrackCombo.getValue()
	,stocktrackdes = trackstatistics.stocktrackdesCombo.getValue();
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
	trackstatistics.store.baseParams.stocktrack = stocktrack?stocktrack:null;
	trackstatistics.store.baseParams.stocktrackdes = stocktrackdes?stocktrackdes:null;
	trackstatistics.store.baseParams.startDate = Ext.util.Format.date(trackstatistics.startDate.getValue(), 'Y-m-d');
	trackstatistics.store.baseParams.endDate = Ext.util.Format.date(trackstatistics.endDate.getValue(), 'Y-m-d');
	trackstatistics.store.baseParams.state = trackstatistics.selectAll.checked?null:1;
	trackstatistics.store.baseParams.code = trackstatistics.codeText.getValue();
	trackstatistics.store.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
trackstatistics.searchFun = function () {
	trackstatistics.resetStoreParameters();
    trackstatistics.alwaysFun();
    trackstatistics.store.reload();
};
/**主面板*/
trackstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ trackstatistics.grid ]
});