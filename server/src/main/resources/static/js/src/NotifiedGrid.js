Ext.define('NotifiedGrid', {
  extend: 'Ext.Panel',
  title: Localization.get('notified.grid.title'),
  tbar: [
    {
      xtype: 'button',
      text: Localization.get('notified.grid.button.add'),
      handler: function() {
        new NotifiedForm({title: Localization.get('notified.form.add_notified.title')}).show();
      }
    },
    {
      xtype: 'button',
      text: Localization.get('notified.grid.button.edit')
    }
  ],
  items: [{
    xtype: 'grid',
    store: Ext.create('Ext.data.Store', {
      storeId: 'notifiedStore',
      fields: ['category', 'email', 'text'],
      data: []
    }),
            columns: [
                {
                    xtype: 'actioncolumn',
                    tooltip: Localization.get('notified.grid.column.edit'),
                    icon: 'images/edit.png',
                    width: 20,
                    handler: function(grid, rowIndex, colIndex) {
                      var record = grid.getStore().getAt(rowIndex);
                      //var editSubscriptionForm = Ext.create('SubscriptionForm', {title: Localization.get('notified.form.edit_subscription.title')});
                      //editSubscriptionForm.show();
                      //editSubscriptionForm.init(record);
                    }
                },
                { text: Localization.get('notified.grid.column.recipient'), dataIndex: 'recipient' },
                { text: Localization.get('notified.grid.column.subject'), dataIndex: 'subject', flex: 1 },
                { text: Localization.get('notified.grid.column.message'), dataIndex: 'message' },
                {
                    xtype: 'actioncolumn',
                    tooltip: Localization.get('notified.grid.column.delete'),
                    icon: 'images/delete.png',
                    width: 20
                }
            ],
            height: 200,
            width: 500,
            listeners: {
                    'afterrender': function(component) {
                    // todo:
                      console.log(component.getStore());
                    }
                  }
        }]
});