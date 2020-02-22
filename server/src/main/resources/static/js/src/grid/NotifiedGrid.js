Ext.define('NotifiedGrid', {
  extend: 'BaseCrudGrid',
  controller: 'notifiedGridCtrl',
  title: Localization.get('notified.grid.title'),
  items: [{
    xtype: 'grid',
    reference: 'notifiedGrid',
    store: new CommonStore().createNotifiedStore(),
    columns: [
      { xtype: 'editactioncolumn' },
      { text: Localization.get('notified.grid.column.recipient'), dataIndex: 'recipient' },
      { text: Localization.get('notified.grid.column.subject'), dataIndex: 'subject', renderer: Ext.util.Format.htmlEncode },
      { text: Localization.get('notified.grid.column.message'), dataIndex: 'message', renderer: Renderers.column.HtmlMessageRenderer, flex: 1 },
      { xtype: 'deleteactioncolumn' }
    ],
    listeners: {
      rowdblclick: 'onEditDoubleClick',
      'add-record': { fn: 'onAddRecord', scope: 'controller' },
      select: 'onRowSelected'
    },
    height: 200
  }],
  getNotifiedRecords: function() {
    var notifiedList = [];
    Ext.each(this.controller.lookupReference('notifiedGrid').getStore().getRange(), function(record) {
      notifiedList.push({
        id: record.get('id'),
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
    component.relayEvents(notifiedForm, ['add-record']);
    notifiedForm.show();
  },
  openEditScreen: function(record, component) {
    var notifiedForm = new NotifiedForm({title: Localization.get('notified.form.edit_notified.title'), data: record});
    component.relayEvents(notifiedForm, ['add-record']);
    notifiedForm.show();
  },
  getGrid: function() {
    return this.lookupReference('notifiedGrid');
  },
  onAddRecord: function(form, record) {
    var grid = this.lookupReference('notifiedGrid');
    grid.getStore().add(record);
    form.closeView();
  },
  onEditRecord: function(form, record) {
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