Ext.define('SubscriptionForm', {
  extend: 'BaseEditFormWindow',
  height: 500,
  width: 600,
  controller: 'subscriptionFormCtrl',
  createEditFormPanel: function() {
    return new Ext.form.FormPanel({
      reference: 'subscriptionForm',
      bodyPadding: '10',
      border: false,
      layout: 'vbox',
      items: [
        {
          reference: 'category',
          xtype: 'combo',
          fieldLabel: Localization.get('subscription.form.subscription.field.category'),
          store: new CommonStore().createCategoryStore(),
          queryMode: 'remote',
          displayField: 'name',
          valueField: 'id',
          allowBlank: false,
          editable: false,
          width: 500
        },
        new NotifiedGrid({reference: 'notifiedGrid'})
      ]
    });
  }
});
Ext.define('SubscriptionFormController', {
  extend: 'BaseEditWindowController',
  alias: 'controller.subscriptionFormCtrl',

  onApply: function() {
    if (this.lookupReference('subscriptionForm').isValid()) {
      var subscription = {};
      subscription.category = {};
      subscription.category.id = this.lookupReference('category').getValue();
      subscription.category.name = this.lookupReference('category').getStore().getById(subscription.category.id).get('name');
      subscription.notified = this.lookupReference('notifiedGrid').getNotifiedRecords();
      console.log(subscription);
      this.closeView();
      return; // TODO:
      Ext.Ajax.request({
        url: '/subscription/add',
        method: 'POST',
        params: Ext.util.JSON.encode(subscription),
        success: function(response) {
          var text = response.responseText;
        }
      });
    }
  },
  init: function(config) {
    if (config.initialConfig.data) {
      var record = config.initialConfig.data;
      this.lookupReference('category').setValue(record.get('category').id);
      this.lookupReference('notifiedGrid').setNotified(record.get('notifiedList'));
    } else {
      this.lookupReference('notifiedGrid').setNotified([]);
    }
  }
});