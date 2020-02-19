Ext.define('NotifiedForm', {
  extend: 'Ext.Window',
  height: 300,
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
      reference: 'notifiedForm',
      bodyPadding: '10',
      border: false,
      layout: 'vbox',
      items: [
      {
        reference: 'email',
        xtype: 'textfield',
        name: 'email',
        fieldLabel: Localization.get('notified.form.add_subscription.field.recipient'),
        allowBlank: false,
        width: 500
      },
      {
        reference: 'subject',
        xtype: 'textfield',
        name: 'subject',
        id: 'subject',
        fieldLabel: Localization.get('notified.form.add_notified.field.subject'),
        width: 500
      },
      {
        reference: 'message',
        xtype: 'textareafield',
        grow: true,
        name: 'message',
        id: 'message',
        fieldLabel: Localization.get('notified.form.add_notified.field.message'),
        width: 500
      },
      /*Ext.create('FilterGrid', {data: config.data})*/]
    }));

    this.bbar = [
    { xtype: 'tbfill' },
    {
      xtype: 'button',
      text: Localization.get('notified.form.add_notified.button.apply'),
      handler: function() {
        /*var email = Ext.getCmp('email').getValue();
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
        });*/
      }
    }];

    this.callParent(arguments);

    self = this;
  },
  listeners: {
    'afterrender': function(form) {
      var tagsObject = new Tags();
      tagsObject.getTags(tagsObject.types.SUBJECT, function(tags) {
        var tagItems = [];
        Ext.each(tags, function(tag) {
          tagItems.push({ text: tag, handler: function () { form.addTag('subject', tag) } });
        }, form);
        var subjectContextMenu = new Ext.menu.Menu({
          text: 'Menu',
          items: [ { text: 'Add Tag', menu: { items: tagItems } } ]
        });
        Ext.get('subject').on('contextmenu', function(e) {
          e.stopEvent();
          var xy = e.getXY();
          subjectContextMenu.showAt(xy);
        });
      });
      tagsObject.getTags(tagsObject.types.MESSAGE, function(tags) {
        var tagItems = [];
        Ext.each(tags, function(tag) {
          tagItems.push({ text: tag, handler: function () { form.addTag('message', tag) } });
        }, form);
        var messageContextMenu = new Ext.menu.Menu({
          text: 'Menu',
          items: [ { text: 'Add Tag', menu: { items: tagItems } } ]
        });
        Ext.get('message').on('contextmenu', function(e) {
          e.stopEvent();
          var xy = e.getXY();
          messageContextMenu.showAt(xy);
        });
      });
    }
  },
  init: function(record) {
    Ext.getCmp('email').setValue(record.get('email'));
    Ext.getCmp('category').setValue(record.get('category'));
    Ext.getCmp('subject').setValue(record.get('subject'));
    Ext.getCmp('message').setValue(record.get('text'));
  }
});