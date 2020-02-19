Ext.define('SubscriptionForm', {
  extend: 'Ext.Window',
  height: 500,
  width: 600,
  layout: 'fit',
  modal: true,
  addTag: function (input, tag) {
    var component = Ext.getCmp(input);
    component.setValue(component.getValue() + '<' + tag + '>');
  },
  constructor: function (config) {
    var self = this;
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
        width: 500
      },
      Ext.create('NotifiedGrid', {data: config.data})]
    }));

    this.bbar = [
    { xtype: 'tbfill' },
    {
      xtype: 'button',
      text: Localization.get('subscription.form.add_subscription.button.apply'),
      handler: function() {
        var email = Ext.getCmp('email').getValue();
        var category = self.subscriptionForm.category.getValue();
        var subject = self.subscriptionForm.subject.getValue();
        var message = self.subscriptionForm.message.getValue();
        Ext.Ajax.request({
            url: '/subscription/add',
            method: 'POST',
            params: Ext.util.JSON.encode({
               address: email,
               category: category,
               type: 'EMAIL',
               subjectTemplate: subject,
               textTemplate: message,
               filters: []
            }),
            success: function(response) {
                var text = response.responseText;
                // process server response here
            }
        });
      }
    }];

    this.callParent(arguments);

    self = this;
  },
  init: function(record) {
    Ext.getCmp('category').setValue(record.get('category'));
  }
});