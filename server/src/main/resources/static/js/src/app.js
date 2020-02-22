Ext.onReady(function () {
  Ext.create('Ext.container.Viewport', {
    layout: {
      type: 'vbox',
      align: 'middle'
    },
    items: [new SubscriptionGrid({})]
  });
});
