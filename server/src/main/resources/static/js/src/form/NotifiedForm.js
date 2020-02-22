Ext.define('NotifiedForm', {
  extend: 'BaseEditFormWindow',
  height: 600,
  width: 600,
  controller: 'notifiedFormCtrl',
  addTag: function (component, tag) {
    component.setValue(component.getValue() + '<' + tag + '>');
  },
  createEditFormPanel: function () {
    return new Ext.form.FormPanel({
      reference: 'notifiedForm',
      bodyPadding: '10',
      border: false,
      layout: 'vbox',
      items: [
      {
        reference: 'recipient',
        xtype: 'textfield',
        name: 'email',
        fieldLabel: Localization.get('notified.form.notified.field.recipient'),
        allowBlank: false,
        width: 500
      },
      {
        reference: 'subject',
        xtype: 'textfield',
        name: 'subject',
        fieldLabel: Localization.get('notified.form.notified.field.subject'),
        width: 500
      },
      {
        reference: 'message',
        xtype: 'textareafield',
        grow: true,
        name: 'message',
        allowBlank: false,
        fieldLabel: Localization.get('notified.form.notified.field.message'),
        width: 500
      },
      new FilterGrid({reference: 'filterGrid'})
      ]
    });
  },
  listeners: {
    afterrender: 'onAfterRender',
    onApply: { fn: 'onApply', scope: 'controller' }
  }
});

Ext.define('NotifiedFormController', {
  extend: 'BaseEditWindowController',
  alias: 'controller.notifiedFormCtrl',

  onApply: function() {
    if (this.lookupReference('notifiedForm').isValid()) {
      var recipient = this.lookupReference('recipient').getValue();
      var subject = this.lookupReference('subject').getValue();
      var message = this.lookupReference('message').getValue();
      var filters = this.lookupReference('filterGrid').getFilterRecords();

      var record = {
        recipient: recipient,
        subject: subject,
        message: message,
        filters: filters
      };

      this.fireViewEvent('add-record', this, record);
    }
  },
  init: function(config) {
    if (config.initialConfig.data) {
      this.lookupReference('recipient').setValue(config.initialConfig.data.get('recipient'));
      this.lookupReference('recipient').setEditable(false);
      this.lookupReference('subject').setValue(config.initialConfig.data.get('subject'));
      this.lookupReference('message').setValue(config.initialConfig.data.get('message'));
      this.lookupReference('filterGrid').setFilters(config.initialConfig.data.get('filters'));
    } else {
      this.lookupReference('filterGrid').setFilters([]);
    }
  },
  onAfterRender: function(form) {
    var subject = this.lookupReference('subject');
    var message = this.lookupReference('message');
      var tagsObject = new Tags();
      tagsObject.getTags(tagsObject.types.SUBJECT, function(tags) {
        var tagItems = [];
        Ext.each(tags, function(tag) {
          tagItems.push({ text: tag, handler: function () { form.addTag(subject, tag) } });
        }, form);
        var subjectContextMenu = new Ext.menu.Menu({
          text: Localization.get('menu.title'),
          items: [ { text: Localization.get('menu.add_tag'), menu: { items: tagItems } } ]
        });
        subject.getEl().on('contextmenu', function(e) {
          e.stopEvent();
          var xy = e.getXY();
          subjectContextMenu.showAt(xy);
        });
      });
      tagsObject.getTags(tagsObject.types.MESSAGE, function(tags) {
        var tagItems = [];
        Ext.each(tags, function(tag) {
          tagItems.push({ text: tag, handler: function () { form.addTag(message, tag) } });
        }, form);
        var messageContextMenu = new Ext.menu.Menu({
          text: Localization.get('menu.title'),
          items: [ { text: Localization.get('menu.add_tag'), menu: { items: tagItems } } ]
        });
        message.getEl().on('contextmenu', function(e) {
          e.stopEvent();
          var xy = e.getXY();
          messageContextMenu.showAt(xy);
        });
      });
    }
});