Ext.define('SubscriptionGrid', {
  extend: 'Ext.Panel',
  region: 'center',
  controller: 'subscriptionGridCtrl',
  title: Localization.get('subscription.grid.title'),
    width: 800,
    tbar: [
      {
        xtype: 'button',
        text: Localization.get('subscription.grid.button.add'),
        handler: 'onAddClick'
      },
      {
        xtype: 'button',
        text: Localization.get('subscription.grid.button.edit'),
        handler: 'onEditClick'
      }
    ],
    items: [{
      xtype: 'grid',
      reference: 'subscriptionGrid',
      store: new CommonStore().createSubscriptionStore(),
      columns: [
        {
          xtype: 'actioncolumn',
          tooltip: Localization.get('subscription.grid.column.edit'),
          icon: 'images/edit.png',
          width: 20,
          handler: 'onEditClickGrid'
        },
        { text: Localization.get('subscription.grid.column.category'), dataIndex: 'category', renderer: function(value) { return value.name; } },
        { text: Localization.get('subscription.grid.column.message'), dataIndex: 'notifiedList', renderer: function(value) { return value.length; } },
        {
          xtype: 'actioncolumn',
          tooltip: Localization.get('subscription.grid.column.delete'),
          icon: 'images/delete.png',
          width: 20,
          handler: 'onDeleteClick'
        }
      ],
      height: 500,
      width: 600
    }]
});

Ext.define('SubscriptionGridController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.subscriptionGridCtrl',

  onAddClick: function() {
    new SubscriptionForm({title: Localization.get('subscription.form.add_subscription.title')}).show();
  },
  onEditClick: function() {
    var grid = this.lookupReference('subscriptionGrid');
    var selections = grid.getSelection();
    if (Ext.isArray(selections) && selections.length > 0) {
      this.doEdit(selections[0]);
    }
  },
  onEditClickGrid: function(grid, rowIndex) {
    var record = grid.getStore().getAt(rowIndex);
    this.doEdit(record);
  },
  doEdit: function(record) {
    var editSubscriptionForm = Ext.create('SubscriptionForm', {title: Localization.get('subscription.form.edit_subscription.title'), data: record});
    editSubscriptionForm.show();
  },
  onDeleteClick: function(grid, rowIndex) {
    grid.getStore().removeAt(rowIndex);
  }
});