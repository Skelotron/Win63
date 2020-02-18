Ext.define('SubscriptionForm', {
  extend: 'Ext.Window',
  height: 300,
  width: 600,
  layout: 'fit',
  addTag: function (input, tag) {
    var component = Ext.getCmp(input);
    component.setValue(component.getValue() + '<' + tag + '>');
  },
  constructor: function (config) {

    var subjectContextMenu = new Ext.menu.Menu({
      text: 'Menu',
      id: 'SubjectContextMenu',
      items: [
        {
          text: 'Add Tag', menu: {
            items: [
              { text: 'ItemTitle', handler: function () { addTag('subject', 'ItemTitle') } },
              { text: 'ItemDescription', handler: function () { addTag('subject', 'ItemDescription') } }
            ]
          }
        }
      ]
    });

    var messageContextMenu = new Ext.menu.Menu({
      text: 'Menu',
      id: 'MessageContextMenu',
      items: [
        {
          text: 'Add Tag', menu: {
            items: [
              { text: 'ItemTitle', handler: function () { addTag('message', 'ItemTitle') } },
              { text: 'ItemDescription', handler: function () { addTag('message', 'ItemDescription') } }
            ]
          }
        }
      ]
    });

    var categoryStore = new Ext.data.JsonStore({
      // store configs
      storeId: 'categoryStore',
      proxy: {
        type: 'ajax',
        url: '/category/',
        reader: {
          type: 'json',
          rootProperty: 'categories'
        }
      },
      fields: ['externalId', 'name']
    });

    var self = this;
    this.items = [];
    this.items.push(new Ext.Panel({
      ref: 'subscriptionForm',
      bodyPadding: '10',
      border: false,
      layout: 'vbox',
      items: [
      {
        ref: 'email',
        xtype: 'textfield',
        name: 'email',
        fieldLabel: 'Email',
        allowBlank: false,
        width: 500
      },
      {
        ref: 'category',
        xtype: 'combo',
        fieldLabel: 'Category',
        store: categoryStore,
        queryMode: 'remote',
        displayField: 'name',
        valueField: 'externalId',
        width: 500
      },
      {
        ref: 'subject',
        xtype: 'textfield',
        name: 'subject',
        id: 'subject',
        fieldLabel: 'Subject',
        width: 500
      },
      {
        ref: 'message',
        xtype: 'textareafield',
        grow: true,
        name: 'message',
        id: 'message',
        fieldLabel: 'Message',
        width: 500
      }]
    }));

    this.bbar = [
    { xtype: 'tbfill' },
    {
      xtype: 'button',
      text: 'Add',
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
  }
});