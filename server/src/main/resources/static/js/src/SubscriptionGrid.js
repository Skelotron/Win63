Ext.define('SubscriptionGrid', {
  extend: 'Ext.Panel',
  region: 'center',
  title: Localization.get('subscription.grid.title'),
    width: 800,
    tbar: [
      {
        xtype: 'button',
        text: Localization.get('subscription.grid.button.add'),
        handler: function() {
          new SubscriptionForm({title: Localization.get('subscription.form.add_subscription.title')}).show();
        }
      },
      {
        xtype: 'button',
        text: Localization.get('subscription.grid.button.edit')
      }
    ],
    items: [{
    xtype: 'grid',
      store: new CommonStore().createSubscriptionStore(),
      columns: [
        {
          xtype: 'actioncolumn',
          tooltip: Localization.get('subscription.grid.column.edit'),
          icon: 'images/edit.png',
          width: 20,
          handler: function(grid, rowIndex, colIndex) {
            var record = grid.getStore().getAt(rowIndex);
            var editSubscriptionForm = Ext.create('SubscriptionForm', {title: Localization.get('subscription.form.edit_subscription.title'), data: record});
            editSubscriptionForm.show();
          }
        },
        { text: Localization.get('subscription.grid.column.category'), dataIndex: 'category', renderer: function(value) { return value.name; } },
        { text: Localization.get('subscription.grid.column.message'), dataIndex: 'notifiedList', renderer: function(value) { return value.length; } },
        {
          xtype: 'actioncolumn',
          tooltip: Localization.get('subscription.grid.column.delete'),
          icon: 'images/delete.png',
          width: 20
        }
      ],
      height: 500,
      width: 600
    }]
});