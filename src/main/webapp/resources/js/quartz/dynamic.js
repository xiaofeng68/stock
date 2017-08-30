Ext.ns("Ext.Quartz.dynamic"); // 自定义一个命名空间
dynamic = Ext.Quartz.dynamic; // 定义命名空间的别名
dynamic = {
    all: ctx + '/quartz/all',// 加载所有
    save: ctx + "/quartz/save",//保存
    del: ctx + "/quartz/del/",//删除
    start: ctx + "/quartz/start",//启动
    stop: ctx + "/quartz/stop",//停止
    pageSize: 20 // 每页显示的记录数
}; /** 改变页的combo */
dynamic.pageSizeCombo = new Share.pageSizeCombo({
    value: '20',
    listeners: {
        select: function (comboBox) {
            dynamic.pageSize = parseInt(comboBox.getValue());
            dynamic.bbar.pageSize = parseInt(comboBox.getValue());
            dynamic.store.baseParams.limit = dynamic.pageSize;
            dynamic.store.baseParams.start = 0;
            dynamic.store.load();
        }
    }
});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
dynamic.pageSize = parseInt(dynamic.pageSizeCombo.getValue()); /** 基本信息-数据源 */
dynamic.store = new Ext.data.Store({
    autoLoad: true,
    remoteSort: true,
    baseParams: {
        start: 0,
        limit: dynamic.pageSize
    },
    proxy: new Ext.data.HttpProxy({ // 获取数据的方式
        method: 'POST',
        url: dynamic.all
    }),
    reader: new Ext.data.JsonReader({ // 数据读取器
        totalProperty: 'results',
        // 记录总数
        root: 'rows' // Json中的列表数据根节点
    }, ['id', 'gcode', 'code', 'name', 'des', 'state', 'exp','clsdes']),
    listeners: {
        'load': function (store, records, options) {
            dynamic.alwaysFun();
        }
    }
}); /** 基本信息-选择模式 */
dynamic.selModel = new Ext.grid.CheckboxSelectionModel({
    singleSelect: true,
    listeners: {
        'rowselect': function (selectionModel, rowIndex, record) {
            dynamic.deleteAction.enable();
            dynamic.editAction.enable();
            if(record.data.state==0){//启动中，可以停止
            	dynamic.stopAction.enable();
            }else{//停止中，可以启动
            	dynamic.startAction.enable();
            }
        },
        'rowdeselect': function (selectionModel, rowIndex, record) {
            dynamic.alwaysFun();
        }
    }
}); /** 基本信息-数据列 */
dynamic.colModel = new Ext.grid.ColumnModel({
    defaults: {
        sortable: true,
        width: 140
    },
    columns: [dynamic.selModel,
    {
        hidden: true,
        header: '字段ID',
        dataIndex: 'id'
    }, {
    	hidden: true,
        header: '任务组',
        dataIndex: 'gcode'
    }, {
        header: '任务编号',
        dataIndex: 'code'
    }, {
    	hidden: true,
        header: '任务名称',
        dataIndex: 'name'
    }, {
    	width:374,
        header: '任务描述',
        dataIndex: 'des'
    }, {
    	hidden: true,
        header: '执行表达式',
        dataIndex: 'exp'
    }, {
    	hidden: true,
        header: '任务类描述',
        dataIndex: 'clsdes'
    }, {
    	width:70,
        header: '任务状态',
        dataIndex: 'state',
        renderer : function(value, metadata, record) {  
            if(value==0){
	            return "启动中";
            }else{
            	return "已停止";
            }
        }
    }]
}); 
/**停止一个任务**/
dynamic.stopFun = function(record){
	var record = dynamic.grid.getSelectionModel().getSelected();
    // 发送请求
    Share.AjaxRequest({
        url: dynamic.stop,
        params: record.data,
        callback: function (json) {
            dynamic.alwaysFun();
            dynamic.store.reload();
        }
    });
};
/**启动一个任务**/
dynamic.startFun = function(record){
	var record = dynamic.grid.getSelectionModel().getSelected();
    // 发送请求
    Share.AjaxRequest({
        url: dynamic.start,
        params: record.data,
        callback: function (json) {
            dynamic.alwaysFun();
            dynamic.store.reload();
        }
    });
};
/** 新建 */
dynamic.addAction = new Ext.Action({
    text: '新建',
    handler: function () {
        dynamic.addWindow.setIconClass('dynamic_add'); // 设置窗口的样式
        dynamic.addWindow.setTitle('新建任务'); // 设置窗口的名称
        dynamic.addWindow.show().center(); // 显示窗口
        dynamic.formPanel.getForm().reset(); // 清空表单里面的元素的值.
    }
}); 
/** 编辑 */
dynamic.editAction = new Ext.Action({
    text: '编辑',
    disabled: true,
    handler: function () {
        var record = dynamic.grid.getSelectionModel().getSelected();
        dynamic.addWindow.setIconClass('dynamic_edit'); // 设置窗口的样式
        dynamic.addWindow.setTitle('编辑任务'); // 设置窗口的名称
        dynamic.addWindow.show().center();
        dynamic.formPanel.getForm().reset();
        dynamic.formPanel.getForm().loadRecord(record);
    }
}); 
/** 删除 */
dynamic.deleteAction = new Ext.Action({
    text: '删除',
    disabled: true,
    handler: function () {
        dynamic.delFun();
    }
}); 
/** 查询 */
dynamic.searchdynamic = new Ext.ux.form.SearchField({
    store: dynamic.store,
    paramName: 'dynamicName',
    emptyText: '请输入字段名称',
    style: 'margin-left: 5px;'
});
/** 停止 */
dynamic.stopAction = new Ext.Action({
    text: '停止',
    disabled: true,
    handler: function () {
        dynamic.stopFun();
    }
}); 
/** 启动 */
dynamic.startAction = new Ext.Action({
    text: '启动',
    disabled: true,
    handler: function () {
        dynamic.startFun();
    }
}); 
/** 提示 */
dynamic.tbar = [dynamic.addAction, '-', dynamic.editAction, '-', dynamic.deleteAction, '-', dynamic.searchdynamic,'-',dynamic.startAction,'-',dynamic.stopAction]; /** 底部工具条 */
dynamic.bbar = new Ext.PagingToolbar({
    pageSize: dynamic.pageSize,
    store: dynamic.store,
    displayInfo: true,
    // plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
    items: ['-', '&nbsp;', dynamic.pageSizeCombo]
});
/** 基本信息-表格 */
dynamic.grid = new Ext.grid.GridPanel({
    store: dynamic.store,
    colModel: dynamic.colModel,
    selModel: dynamic.selModel,
    tbar: dynamic.tbar,
    bbar: dynamic.bbar,
    autoScroll: 'auto',
    region: 'center',
    loadMask: true,
    // autoExpandColumn :'dynamicDesc',
    stripeRows: true,
    listeners: {},
    viewConfig: {}
});

/** 基本信息-详细信息的form */
dynamic.formPanel = new Ext.form.FormPanel({
    frame: false,
    title: '任务详情',
    bodyStyle: 'padding:10px;border:0px',
    labelwidth: 50,
    defaultType: 'textfield',
    items: [{
        xtype: 'hidden',
        fieldLabel: 'ID',
        name: 'id',
        anchor: '99%'
    }, {
        fieldLabel: '组编号',
        allowBlank: false,
        name: 'gcode',
        anchor: '99%'
    }, {
        fieldLabel: '编号',
        allowBlank: false,
        name: 'code',
        anchor: '99%'
    }, {
    	 fieldLabel: '状态',
    	xtype: 'radiogroup',
    	name: 'state',
        anchor: '99%',
        allowBlank: false,
        items: [
        	{boxLabel: '启动', name: 'state', inputValue: '0',checked:true},
        	{boxLabel: '禁用', name: 'state', inputValue: '1'}
        ]
    }, {
        fieldLabel: '表达式',
        allowBlank: false,
        name: 'exp',
        anchor: '99%'
    }, {
        fieldLabel: '执行类',
        allowBlank: false,
        name: 'clsdes',
        anchor: '99%'
    }, {
        fieldLabel: '任务描述',
        xtype:'textarea',
        allowBlank: false,
        name: 'des',
        anchor: '99%'
    }]
}); /** 编辑新建窗口 */
dynamic.addWindow = new Ext.Window({
    layout: 'fit',
    width: 400,
    height: 338,
    closeAction: 'hide',
    plain: true,
    modal: true,
    resizable: true,
    items: [dynamic.formPanel],
    buttons: [{
        text: '保存',
        handler: function () {
            dynamic.saveFun();
        }
    }, {
        text: '重置',
        handler: function () {
            var form = dynamic.formPanel.getForm();
            var id = form.findField("id").getValue();
            form.reset();
            if (id != '') form.findField("id").setValue(id);
        }
    }]
});
dynamic.alwaysFun = function () {
    Share.resetGrid(dynamic.grid);
    dynamic.deleteAction.disable();
    dynamic.editAction.disable();
    dynamic.startAction.disable();
    dynamic.stopAction.disable();
};
dynamic.saveFun = function () {
    var form = dynamic.formPanel.getForm();
    if (!form.isValid()) {
        return;
    }
    // 发送请求
    Share.AjaxRequest({
        url: dynamic.save,
        params: form.getValues(),
        callback: function (json) {
            dynamic.addWindow.hide();
            dynamic.alwaysFun();
            dynamic.store.reload();
        }
    });
};
dynamic.delFun = function () {
    var record = dynamic.grid.getSelectionModel().getSelected();
    Ext.Msg.confirm('提示', '确定要删除这条记录吗?', function (btn, text) {
        if (btn == 'yes') {
            // 发送请求
            Share.AjaxRequest({
                url: dynamic.del + record.data.id,
                callback: function (json) {
                    dynamic.alwaysFun();
                    dynamic.store.reload();
                }
            });
        }
    });
};
dynamic.myPanel = new Ext.Panel({
    id :   stockyear+'_panel',
	renderTo : stockyear,
    layout: 'border',
    boder: false,
    height: index.tabPanel.getInnerHeight() - 1,
    items: [dynamic.grid]
});