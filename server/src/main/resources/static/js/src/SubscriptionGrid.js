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
        { xtype: 'editactioncolumn' },
        { text: Localization.get('subscription.grid.column.category'), dataIndex: 'category', renderer: function(value) { return value.name; } },
        { text: Localization.get('subscription.grid.column.message'), dataIndex: 'notifiedList', renderer: function(value) { return value.length; } },
        { xtype: 'deleteactioncolumn' }
      ],
      height: 500,
      width: 600
    }]
});

Ext.define('SubscriptionGridController', {
  extend: 'BaseCrudController',
  alias: 'controller.subscriptionGridCtrl',

  openAddScreen: function() {
    var addSubscriptionForm = new SubscriptionForm({title: Localization.get('subscription.form.add_subscription.title')});
    addSubscriptionForm.show();
  },
  openEditScreen: function(record) {
    var editSubscriptionForm = new SubscriptionForm({title: Localization.get('subscription.form.edit_subscription.title'), data: record});
    editSubscriptionForm.show();
  },
  getGrid: function() {
    return this.lookupReference('subscriptionGrid');
  }
});