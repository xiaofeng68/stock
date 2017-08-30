Ext.QuickTips.init();
Ext.ns("Ext.Logsys.logEvent"); // 自定义一个命名空间
logEvent = Ext.Logsys.logEvent; // 定义命名空间的别名
logEvent = {
	year : ctx + '/logsys' //日志系统
}
/** 改变页的combo*/
logEvent.pageSizeCombo = new Share.pageSizeCombo({
	value : '20',
	listeners : {
		select : function(comboBox) {
			logEvent.pageSize  = parseInt(comboBox.getValue());
			logEvent.bbar.pageSize  = parseInt(comboBox.getValue());
			logEvent.store.baseParams.limit = logEvent.pageSize;
			logEvent.store.baseParams.start = 0;
			logEvent.store.load();
		}
	}
});
//覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
logEvent.pageSize = parseInt(logEvent.pageSizeCombo.getValue());
/** 基本信息-数据源 */
logEvent.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:false,
	baseParams : {
		type:'log',
		state:1,
		start : 0,
		limit : logEvent.pageSize
	},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : logEvent.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'timestmp', 'formatted_message', 'logger_name', 'level_string','arg0', 'arg1','arg2','arg3','caller_filename','caller_class','caller_method','caller_line','event_id','mapped_key','mapped_value']),
	listeners : {
		'load' : function(store, records, options) {
			logEvent.alwaysFun();
		}
	}
});
logEvent.alwaysFun = function() {
	Share.resetGrid(logEvent.grid);
};
//logEvent.store.load(); 
/** 基本信息-选择模式 */
logEvent.selModel = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true,
	listeners : {
		'rowselect' : function(selectionModel, rowIndex, record) {
		},
		'rowdeselect' : function(selectionModel, rowIndex, record) {
		}
	}
});
/** 基本信息-数据列 */
logEvent.colModel = new Ext.grid.ColumnModel({
	defaults : {
		sortable : true,
		width : 80
	},
	columns : [ logEvent.selModel, {
		header : '业务主键',
		dataIndex : 'mapped_key'
	}, {
		header : '业务数值',
		dataIndex : 'mapped_value'
	}, {
		header : '时间',
		dataIndex : 'timestmp',
		renderer:function(value){
			return Ext.util.Format.date(new Date(value), 'Y-m-d H:i:s');
		}
	} ,{
		header : '日志级别',
		dataIndex : 'level_string'
	}, {
		header : '日志内容',
		width:500,
		dataIndex : 'formatted_message'
	} , {
		header : '请求名称',
		width:300,
		dataIndex : 'logger_name'
	}, {
		header : '参数A',
		dataIndex : 'arg0'
	}, {
		header : '参数B',
		dataIndex : 'arg1'
	}, {
		header : '参数C',
		dataIndex : 'arg2'
	}, {
		header : '参数D',
		dataIndex : 'arg3'
	} , {
		header : '参数D',
		dataIndex : 'arg3'
	} , {
		header : '发起的类名',
		width:300,
		dataIndex : 'caller_class'
	} , {
		header : '发起方法名',
		dataIndex : 'caller_method'
	}, {
		header : '日志触发行数',
		dataIndex : 'caller_line'
	} ,{
		header : '日志ID',
		dataIndex : 'event_id'
	}]
});
logEvent.userTraceText = new Ext.form.TextField({
	name : 'userTraceTextField',
	width:80,
	emptyText:'用户编号',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                logEvent.searchFun();
            }
        }
    }
});
/** 行业板块类型*/
logEvent.logLevelCombo = new Ext.form.ComboBox({
		emptyText:'日志级别',
		name : 'logLevel',
		width:80,
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['v', 't'],
					data : Share.map2Ary(log_levels)
				}),
		valueField : 'v',
		displayField : 't',
		allowBlank : true,//可以为空
		//editable : false,
		anchor : '99%'
});
logEvent.timeUtil = new DateTimeUtil();
logEvent.startDateLabel = new Ext.form.Label({
	text:'开始时间',
	anchor : '99%'
});
logEvent.startDate = new Ext.ux.form.DateTimeField({  
    emptyText : '请选择',  
    labelWidth : 100,  
    allowBlank : false,  
    format : 'Y-m-d H:i:s',//日期格式  
    vtype : 'daterange',
    value:logEvent.timeUtil.getBeforeHour(new Date(),1),
    anchor : '99%'
})  
logEvent.endDateLabel = new Ext.form.Label({
	text:'结束时间',
	anchor : '99%'
});
logEvent.endDate = new Ext.ux.form.DateTimeField({  
    emptyText : '请选择',  
    allowBlank : false,  
    format : 'Y-m-d H:i:s',//日期格式  
    vtype : 'daterange',
    value:new Date(),
    anchor : '99%'
})  

/** 查询 */
logEvent.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        logEvent.searchFun();
    }
});
/**清空*/
logEvent.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
    	logEvent.userTraceText.setValue(null);
		logEvent.logLevelCombo.setValue(null);
		logEvent.startDate.setValue(logEvent.timeUtil.getBeforeHour(new Date(),1));
		logEvent.endDate.setValue(new Date());
    }
});
/** 顶部工具栏 */
logEvent.tbar = [logEvent.userTraceText,'-',logEvent.logLevelCombo ,'-',logEvent.startDateLabel,'-',logEvent.startDate,'-',logEvent.endDateLabel,'-',logEvent.endDate,'-',logEvent.searchAction,'-',logEvent.clearAction];
/** 底部工具条 */
logEvent.bbar = new Ext.PagingToolbar({
	pageSize : logEvent.pageSize,
	store : logEvent.store,
	displayInfo : true,
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	items : [ '-', '&nbsp;', logEvent.pageSizeCombo ]
});
/** 基本信息-表格 */
logEvent.grid = new Ext.grid.GridPanel({
	store : logEvent.store,
	colModel : logEvent.colModel,
	selModel : logEvent.selModel,
	tbar : logEvent.tbar,
	bbar : logEvent.bbar,
	stripeRows:true,
	cellTip:true,//出现...时鼠标指向时出现全部内容
	region : 'center',
	autoScroll:true,
	width : logEvent.colModel.getTotalWidth(true), //boolean参数指定是否包括隐藏列的宽度
	bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
	loadMask : true,
	layout : 'fit',//自适应布局
	viewConfig:{
	},
	stripeRows : true
});
/***
 * 返回查询的参数对象
 */
logEvent.resetStoreParameters = function(){
	logEvent.store.baseParams.level_string = logEvent.logLevelCombo.getValue();
	logEvent.store.baseParams.startDate = Ext.util.Format.date(logEvent.startDate.getValue(), 'Y-m-d H:i:s');
	logEvent.store.baseParams.endDate = Ext.util.Format.date(logEvent.endDate.getValue(), 'Y-m-d H:i:s');
	logEvent.store.baseParams.mapped_key = "userId";
	logEvent.store.baseParams.mapped_value = logEvent.userTraceText.getValue();
};
/**重新加载系统日志**/
logEvent.searchFun = function () {
	logEvent.resetStoreParameters();
    logEvent.alwaysFun();
    logEvent.store.reload();
};
/**主面板*/
logEvent.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ logEvent.grid ]
});