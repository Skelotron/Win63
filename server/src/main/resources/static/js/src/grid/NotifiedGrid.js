Ext.define('NotifiedGrid', {
  extend: 'Ext.Panel',
  controller: 'notifiedGridCtrl',
  title: Localization.get('notified.grid.title'),
  width: 500,
  tbar: [
    {
      xtype: 'button',
      text: Localization.get('notified.grid.button.add'),
      handler: 'onAddClick',
      listeners: {'add-notified': 'onAddNotified', 'scope': 'controller'}
    },
    {
      xtype: 'button',
      text: Localization.get('notified.grid.button.edit'),
      handler: 'onEditClick',
      listeners: {'add-notified': 'onEditNotified', 'scope': 'controller'}
    }
  ],
  items: [{
    xtype: 'grid',
    reference: 'notifiedGrid',
    store: new CommonStore().createNotifiedStore(),
    columns: [
      {
        xtype: 'editactioncolumn',
        listeners: {'add-notified': 'onEditNotified', 'scope': 'controller'}
      },
      { text: Localization.get('notified.grid.column.recipient'), dataIndex: 'recipient' },
      { text: Localization.get('notified.grid.column.subject'), dataIndex: 'subject', renderer: Ext.util.Format.htmlEncode },
      { text: Localization.get('notified.grid.column.message'), dataIndex: 'message', renderer: function(value) { return Ext.util.Format.htmlEncode(value).replace(/\n/g, '<br>'); }, flex: 1 },
      { xtype: 'deleteactioncolumn' }
    ],
    listeners: {
      rowdblclick: 'onEditDoubleClick',
      'add-notified': { fn: 'onAddNotified', scope: 'controller' }
    },
    height: 200
  }],
  listeners: {
    'add-notified': { fn: 'onAddNotified', scope: 'controller' }
  },
  populateNotified: function() {
    var notifiedList = [];
    Ext.each(this.controller.lookupReference('notifiedGrid').getStore().getRange(), function(record) {
      notifiedList.push({
        recipient: record.get('recipient'),
        subject: record.get('subject'),
        message: record.get('message'),
        filters: record.get('filters')
      });
    });
    return notifiedList;
  },
  setNotified: function(records) {
    var store = this.controller.lookupReference('notifiedGrid').getStore();
    store.loadData(records, false);
  }
});
Ext.define('NotifiedGridController', {
  extend: 'BaseCrudController',
  alias: 'controller.notifiedGridCtrl',

  openAddScreen: function(component) {
    var notifiedForm = new NotifiedForm({title: Localization.get('notified.form.add_notified.title')});
    component.relayEvents(notifiedForm, ['add-notified']);
    notifiedForm.show();
  },
  openEditScreen: function(record, component) {
    var notifiedForm = new NotifiedForm({title: Localization.get('notified.form.edit_notified.title'), data: record});
    component.relayEvents(notifiedForm, ['add-notified']);
    notifiedForm.show();
  },
  getGrid: function() {
    return this.lookupReference('notifiedGrid');
  },
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
  }
});