Ext.define('SubscriptionForm', {
  extend: 'Ext.Window',
  height: 500,
  width: 600,
  layout: 'fit',
  controller: 'subscriptionFormCtrl',
  modal: true,
  addTag: function (input, tag) {
    var component = Ext.getCmp(input);
    component.setValue(component.getValue() + '<' + tag + '>');
  },
  constructor: function (config) {
    this.items = [];
    this.items.push(new Ext.form.FormPanel({
      reference: 'subscriptionForm',
      bodyPadding: '10',
      border: false,
      layout: 'vbox',
      items: [
      {
        reference: 'category',
        xtype: 'combo',
        fieldLabel: Localization.get('subscription.form.add_subscription.field.category'),
        store: new CommonStore().createCategoryStore(),
        queryMode: 'remote',
        displayField: 'name',
        valueField: 'id',
        allowBlank: false,
        editable: false,
        width: 500
      },
      new NotifiedGrid({reference: 'notifiedGrid'})]
    }));

    this.bbar = [
    { xtype: 'tbfill' },
    { xtype: 'button', text: Localization.get('button.apply'), handler: 'onApply' },
    { xtype: 'button', text: Localization.get('button.cancel'), handler: 'onCancel' }];

    this.callParent(arguments);
  },
  listeners: {
    onApply: {
      fn: 'onApply',
      scope: 'controller'
    }
  }
});
Ext.define('SubscriptionFormController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.subscriptionFormCtrl',

  onApply: function() {
    if (this.lookupReference('subscriptionForm').isValid()) {
      var subscription = {};
      subscription.category = {};
      subscription.category.id = this.lookupReference('category').getValue();
      subscription.category.name = this.lookupReference('category').getStore().getById(subscription.category.id).get('name');
      subscription.notified = this.lookupReference('notifiedGrid').populateNotified();
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
  onCancel: function() {
    this.closeView();
  },
  init: function(config) {
    if (config.initialConfig.data) {
      var record = config.initialConfig.data;
      this.lookupReference('category').setValue(record.get('category').id);
      this.lookupReference('notifiedGrid').setNotified(record.get('notifiedList'));
    }
  }
});