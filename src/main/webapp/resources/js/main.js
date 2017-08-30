Ext.ns("Ext.Authority.index"); // 自定义一个命名空间
index = Ext.Authority.index; // 定义命名空间的别名
index = {
    welcome: ctx + "/welcome",//个股统计
    header: ctx + '/header',
    treeMenu: ctx + "/treeMenu"
};
// 设置主题
Share.swapStyle();
// 头部
index.headerPanel = new Ext.Panel({
    region: 'north',
    height: 65,
    border: false,
    margins: '0 0 0 0',
    collapseMode: 'mini',
    collapsible: false,
    split: false,
    bodyStyle: 'background-color:transparent;',
    autoLoad: {
        url: index.header,
        scripts: true,
        nocache: true
    }
});
index.menuTree = new Ext.tree.TreePanel({
    useArrows: true,
    // 设置为true将在树中使用Vista-style的箭头
    autoScroll: true,
    animate: true,
    // 设置为true以启用展开/折叠时的动画效果
    containerScroll: true,
    // 设置为true向ScrollManager注册此容器
    border: false,
    rootVisible: false,
    // 设置为false将隐藏root节点
    margins: '2 2 0 0',
    loader: new Ext.tree.TreeLoader({
        dataUrl: index.treeMenu,
        clearOnLoad: true
    }),
    root: {
        expanded: true,
        id: '0'
    },
    listeners: {
        'click': function (node, e) { // 点击事件
            if (node.attributes.url) { // 如果是链接 node.isLeaf()
                Share.openTab(node, ctx + node.attributes.url);
            } else {
                e.stopEvent();
            }
        }
    }
});
// 菜单面板
index.menuPanel = new Ext.Panel({
    region: 'west',
    title: '主菜单',
    iconCls: 'computer',
    margins: '0 2 0 0',
    layout: 'fit',
    width: 200,
    minSize: 100,
    maxSize: 300,
    collapsible: true,
    collapseMode: 'mini',
    split: true,
    tools: [{
        id: 'refresh',
        handler: function () {
            index.menuTree.root.reload();
        }
    }],
    items: [index.menuTree]
});

// tab主面板
index.tabPanel = new Ext.TabPanel({
    id: 'mainTabPanel',
    region: 'center',
    activeTab: 0,
    deferredRender: true,
    enableTabScroll: true,
    defaults: {
    	layout : 'fit'//自适应布局
    },
    plugins: new Ext.ux.TabCloseMenu({
        closeTabText: '关闭标签页',
        closeOtherTabsText: '关闭其他标签页',
        closeAllTabsText: '关闭所有标签页'
    }),
    viewConfig : {forceFit : false},
    items: [{
        id: 'home',
        title: '我的主页',
        iconCls: 'home',
        closable: false,
        autoScroll: true,
        autoLoad: {
            url: index.welcome,
            scripts: true,
            nocache: true
        }
    }],
    listeners: {
        'bodyresize': function (panel, neww, newh) {
            // 自动调整tab下面的panel的大小
            var tab = panel.getActiveTab();
            var centerpanel = Ext.getCmp(tab.id + "_div_panel");
            if (centerpanel) {
                centerpanel.setHeight(newh-2);
                centerpanel.setWidth(neww-2);
                index.tabPanel.doLayout();
            }
        }
    }
});

index.msgArea = new Ext.form.TextArea({
    autoScroll: true,
    readOnly: true,
    region: 'center'
});

// 初期化页面Layout
index.viewport = new Ext.Viewport({
    layout: 'border',
    items: [index.headerPanel, index.menuPanel, index.tabPanel,{
    	region : 'south',
	    height : 30,
	    html : '<div class="dibu" id="buttomDiv"><div class="version">版本： Ver. 1.02.342</div>Ver1.0 Preview</div>'//'<div class="login_copyright"></div><br />'
    }]
});