Ext.onReady(function () {
  Ext.create('Ext.container.Viewport', {
    layout: {
      type: 'vbox',
      align: 'middle'
    },
    items: [
    Ext.create('Ext.tab.Panel', {
        width: 800,
        height: 800,
        items: [new SubscriptionGrid({}), new ItemSynchronizationGrid({}), new ItemSynchronizationStatusGrid({})]
    })
    ]
  });
});
