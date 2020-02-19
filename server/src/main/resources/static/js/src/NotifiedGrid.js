Ext.define('NotifiedGrid', {
  extend: 'Ext.Panel',
  controller: 'notifiedGridCtrl',
  title: Localization.get('notified.grid.title'),
  tbar: [
    {
      xtype: 'button',
      text: Localization.get('notified.grid.button.add'),
      handler: function() {
        var notifiedForm = Ext.create({
          xtype: 'notifiedform',
          title: Localization.get('notified.form.add_notified.title')
        });
        this.relayEvents(notifiedForm, ['add-notified']);
        notifiedForm.show();
      },
      listeners: {'add-notified': 'onAddNotified', 'scope': 'controller'}
    },
    {
      xtype: 'button',
      text: Localization.get('notified.grid.button.edit')
    }
  ],
  items: [{
    xtype: 'grid',
    reference: 'notifiedGrid',
    store: Ext.create('Ext.data.Store', {
      storeId: 'notifiedStore',
      fields: ['recipient', 'subject', 'message']
    }),
    columns: [
      {
        xtype: 'actioncolumn',
        tooltip: Localization.get('notified.grid.column.edit'),
        icon: 'images/edit.png',
        width: 20,
        handler: function(grid, rowIndex, colIndex) {
        var record = grid.getStore().getAt(rowIndex);
          var notifiedForm = Ext.create({
            xtype: 'notifiedform',
            title: Localization.get('notified.form.edit_notified.title'),
            data: record
          });
          this.relayEvents(notifiedForm, ['add-notified']);
          notifiedForm.show();
        },
        listeners: {'add-notified': 'onEditNotified', 'scope': 'controller'}
      },
      { text: Localization.get('notified.grid.column.recipient'), dataIndex: 'recipient' },
      { text: Localization.get('notified.grid.column.subject'), dataIndex: 'subject', renderer: Ext.util.Format.htmlEncode, flex: 1 },
      { text: Localization.get('notified.grid.column.message'), dataIndex: 'message', renderer: Ext.util.Format.htmlEncode },
      {
        xtype: 'actioncolumn',
        tooltip: Localization.get('notified.grid.column.delete'),
        icon: 'images/delete.png',
        width: 20,
        handler: 'onDeleteNotified'
      }
    ],
    height: 200,
    width: 500
  }],
  listeners: {
    'add-notified': 'onAddNotified',
    populateNotified: {
      fn: 'onPopulateNotified',
      scope: 'controller'
    }
  },
  populateNotified: function() {
    var notifiedList = [];
    Ext.each(this.controller.lookupReference('notifiedGrid').getStore().getRange(), function(record) {
      notifiedList.push({
        recipient: record.get('recipient'),
        subject: record.get('subject'),
        message: record.get('message')
      });
    });
    return notifiedList;
  }
});
Ext.define('NotifiedGridController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.notifiedGridCtrl',

  onAddNotified: function(form, record) {
    var grid = this.lookupReference('notifiedGrid');
    grid.getStore().add(record);
    form.closeView();
  },
  onEditNotified: function(form, record) {
    var grid = this.lookupReference('notifiedGrid');
    var store = grid.getStore();
    var recordIndex = grid.getStore().findBy(function(rec) { return rec.get('recipient') === record.recipient; });
    if (recordIndex >= 0) {
      var existingRecord = store.getAt(recordIndex);
      Ext.each(Object.keys(record), function(key) {
        existingRecord.set(key, record[key]);
      });
    }
    form.closeView();
  },
  onDeleteNotified: function(grid, rowIndex) {
    grid.getStore().removeAt(rowIndex);
  }
});