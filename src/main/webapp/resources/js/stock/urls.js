Ext.QuickTips.init();
Ext.ns("Ext.Stock.urls"); // 自定义一个命名空间
stockurls = Ext.Stock.urls; // 定义命名空间的别名
stockurls = {
	year : ctx + '/stock/urls' // 所有用户
}

/** 基本信息-数据源 */
stockurls.store = new Ext.data.Store({
	remoteSort : true,
	autoLoad:true,
	baseParams : {},  
	proxy : new Ext.data.HttpProxy({// 获取数据的方式
		method : 'POST',
		url : stockurls.year
	}),
	reader : new Ext.data.JsonReader({// 数据读取器
		totalProperty : 'results', // 记录总数
		root : 'rows' // Json中的列表数据根节点
	}, [ 'id','name','url','pic','userid'])
});
/** 顶部工具栏 */
stockurls.tbar = [];
stockurls.thumbTemplate = new Ext.XTemplate(
    '<tpl for=".">',
        '<div class="thumb-wrap" id="{name}">',
        '<div class="thumb"><img click="" src="resources/urls/stock/{pic}" title="{name}"></div>',
        '<span class="x-editable">{name}</span></div>',
    '</tpl>',
    '<div class="x-clear"></div>'
);
/** 基本信息-表格 */
stockurls.grid =  this.view = new Ext.DataView({
	tpl: stockurls.thumbTemplate,
	autoHeight:true,
	singleSelect: true,
	overClass:'x-view-over',
	itemSelector: 'div.thumb-wrap',
	emptyText : '<div style="padding:10px;">No images match the specified filter</div>',
	store: stockurls.store,
	listeners: {
		'click' : function(me,index,node,e ){
			var obj = stockurls.store.data.items[index].json;
			window.open(obj.url+'?type=individual',
			  '_blank' 
			);
		}
	}
});
/**主面板*/
stockurls.myPanel = new Ext.Panel({
	id:'images-view',
	renderTo : stockyear,
	layout:'fit',
	frame:true,
    autoHeight:true,
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ stockurls.grid ]
});
