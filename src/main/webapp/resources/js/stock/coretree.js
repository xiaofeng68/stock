Ext.QuickTips.init();
Ext.ns("Ext.Stock.corekeysstatistics"); // 自定义一个命名空间
corekeysstatistics = Ext.Stock.corekeysstatistics; // 定义命名空间的别名
corekeysstatistics = {
	year : ctx + '/stock/coretree'
}
/**994**/
corekeysstatistics.selectAll = new Ext.form.Checkbox({
	boxLabel: '', 
	name : 'selectAllBox',
	anchor : '99%'
});
/**编号名称文本框*/
corekeysstatistics.codeText = new Ext.form.TextField({
	name : 'codeTextField',
	width:80,
	emptyText:'编号/名称',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                corekeysstatistics.searchFun();
            }
        }
    }
});
corekeysstatistics.codekeysText = new Ext.form.TextField({
	name : 'codekeysTextField',
	width:200,
	emptyText:'一个或多个关键字',
	anchor : '99%',
	listeners: {
        specialkey: function(field, e){
            if (e.getKey() == e.ENTER) {
                corekeysstatistics.searchFun();
            }
        }
    }
});
/**核心题材内容展示*/
corekeysstatistics.contextText = new Ext.form.HtmlEditor({
	name : 'contextTextField',
	readOnly:true,
	region:'center',
	emptyText:'编号/名称',
	anchor : '99%'
});
/** 行业板块类型*/
corekeysstatistics.bustypeCombo = new Ext.form.ComboBox({
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
/** 查询 */
corekeysstatistics.searchAction = new Ext.Action({
    text: '查询',
    handler: function () {
        corekeysstatistics.searchFun();
    }
});
/**清空*/
corekeysstatistics.clearAction = new Ext.Action({
    text: '清空',
    handler: function () {
		corekeysstatistics.bustypeCombo.setValue(null);
		corekeysstatistics.selectAll.checked = false;
		corekeysstatistics.selectAll.el.dom.checked = false;
		corekeysstatistics.codeText.setValue(null);
		corekeysstatistics.codekeysText.setValue(null);
		
    }
});
/** 提示 */
corekeysstatistics.tips = '&nbsp;<font color="red"><b>提示:多个关键字查询时使用逗号或分好</b></font>';
/** 顶部工具栏 */
corekeysstatistics.tbar = [corekeysstatistics.selectAll,'-',corekeysstatistics.codeText,'-',corekeysstatistics.bustypeCombo,'-',corekeysstatistics.codekeysText,'-',corekeysstatistics.searchAction,'-',corekeysstatistics.clearAction,corekeysstatistics.tips];
/***
 * 返回查询的参数对象
 */
corekeysstatistics.resetStoreParameters = function(){
	var busType = corekeysstatistics.bustypeCombo.getValue(),keyStr = corekeysstatistics.codekeysText.getValue();
	var types = [];
	if(busType){
		types.push(busType);
	}
	var treeLoad = corekeysstatistics.menuTree.getLoader();
	var keys = corekeysstatistics.getKeys(keyStr);
	treeLoad.baseParams.keys = $.toJSON(keys);
	treeLoad.baseParams.state = corekeysstatistics.selectAll.checked?null:1;
	treeLoad.baseParams.code = corekeysstatistics.codeText.getValue();
	treeLoad.baseParams.typeIds = $.toJSON(types);
};
/**重新加载年统计**/
corekeysstatistics.searchFun = function () {
	corekeysstatistics.resetStoreParameters();
    corekeysstatistics.menuTree.root.reload();
};
/**主面板*/
corekeysstatistics.menuTree = new Ext.tree.TreePanel({
    autoScroll: true,
    // 设置为true向ScrollManager注册此容器
    border: false,
    rootVisible: false,
    width:200,
    height:500,
    region: 'west',
    // 设置为false将隐藏root节点
    margins: '2 2 0 0',
    loader: new Ext.tree.TreeLoader({
        dataUrl: corekeysstatistics.year,
        clearOnLoad: true,
        listeners:{
        	beforeload : function() {  
		       corekeysstatistics.menuTree.el.mask('加载中');//tree为TreePanel对象  
		    },  
		    load : function() {  
		       corekeysstatistics.menuTree.el.unmask();//tree为TreePanel对象  
		    }
        }
    }),
    root: {
        expanded: true,
        id: '0'
    },
    listeners: {
        'click': function (node, e) { // 点击事件
        	var context = node.attributes.data;
        	context = context.replace(new RegExp('\r', 'g'), '<br/>');
        	var keys = corekeysstatistics.getKeys(corekeysstatistics.codekeysText.getValue());
        	if(keys && keys.length>0){
				for (var i=0,j=keys.length;i<j;i++) {
					context = context.replace(new RegExp(keys[i], 'g'), '<span style="color:#FF0000;font-size:25;">'+keys[i]+'</span>');
				}
        	}
        	//context = context.replace(new RegExp('要点', 'g'), '<span style="color:#FF0000;font-size:25;">题材</span>');
            corekeysstatistics.contextText.setValue(context);
        }
    }
});
/**格式化关键字*/
corekeysstatistics.getKeys = function(value){
	if(!value) return [];
	value = value.replace(new RegExp(',', 'g'), ';');
	value = value.replace(new RegExp('，', 'g'), ';');
	value = value.replace(new RegExp('；', 'g'), ';');
	return value.split(';');
};
/**主面板*/
corekeysstatistics.myPanel = new Ext.Panel({
	id :   stockyear+'_panel',
	renderTo : stockyear,
	tbar : corekeysstatistics.tbar,
	bbar : corekeysstatistics.bbar,
	layout : 'border',
	autoScroll:"true",
	boder : false,
	autoScroll:true,
	height : index.tabPanel.getInnerHeight() - 1,
	items : [ corekeysstatistics.menuTree,corekeysstatistics.contextText ]
});
