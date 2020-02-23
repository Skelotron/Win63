Ext.define('SubscriptionGrid', {
  extend: 'BaseCrudGrid',
  controller: 'subscriptionGridCtrl',
  title: Localization.get('subscription.grid.title'),
  width: 800,
  items: [{
    xtype: 'grid',
    reference: 'subscriptionGrid',
    store: new CommonStore().createSubscriptionStore(),
    columns: [
      { xtype: 'editactioncolumn' },
      { text: Localization.get('subscription.grid.column.category'), dataIndex: 'category', renderer: function(value) { return value.name; }, flex: 1 },
      { text: Localization.get('subscription.grid.column.notified_count'), dataIndex: 'notifiedList', renderer: Renderers.column.CountRenderer },
      { xtype: 'deleteactioncolumn' }
    ],
    listeners: {
      rowdblclick: 'onEditDoubleClick',
      select: 'onRowSelected'
    },
    height: 500,
    width: 600
  }]
});

Ext.define('SubscriptionGridController', {
  extend: 'BaseCrudController',
  alias: 'controller.subscriptionGridCtrl',

  openAddScreen: function() {
    var subscriptionForm = new SubscriptionForm({title: Localization.get('subscription.form.add_subscription.title')});
    subscriptionForm.show();
    return subscriptionForm;
  },
  openEditScreen: function(record) {
    var subscriptionForm = new SubscriptionForm({title: Localization.get('subscription.form.edit_subscription.title'), data: record});
    subscriptionForm.show();
    return subscriptionForm;
  },
  getGrid: function() {
    return this.lookupReference('subscriptionGrid');
  }
});