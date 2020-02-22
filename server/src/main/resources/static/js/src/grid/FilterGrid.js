Ext.define('FilterGrid', {
  extend: 'BaseCrudGrid',
  controller: 'filterGridCtrl',
  title: Localization.get('filter.grid.title'),
  items: [{
    xtype: 'grid',
    reference: 'filterGrid',
    store: new CommonStore().createFilterStore(),
    columns: [
      { xtype: 'editactioncolumn' },
      { text: Localization.get('filter.grid.column.field'), dataIndex: 'field', renderer: Renderers.column.FilterFieldRenderer },
      { text: Localization.get('filter.grid.column.relation'), dataIndex: 'relation', renderer: Renderers.column.FilterRelationRenderer },
      { text: Localization.get('filter.grid.column.value'), dataIndex: 'value', renderer: Renderers.column.FilterValueRenderer, flex: 1 },
      { xtype: 'deleteactioncolumn' }
    ],
    listeners: {
      rowdblclick: 'onEditDoubleClick',
      'add-record': { fn: 'onAddRecord', scope: 'controller' },
      select: 'onRowSelected'
    },
    height: 200
  }],
  getFilterRecords: function() {
    var filterList = [];
    Ext.each(this.controller.lookupReference('filterGrid').getStore().getRange(), function(record) {
      filterList.push({
        id: record.get('id'),
        field: record.get('field'),
        relation: record.get('relation'),
        value: record.get('value')
      });
    });
    return filterList;
  },
  setFilters: function(records) {
    var store = this.controller.lookupReference('filterGrid').getStore();
    store.loadData(records, false);
  }
});

Ext.define('FilterGridController', {
  extend: 'BaseCrudController',
  alias: 'controller.filterGridCtrl',

  openAddScreen: function(component) {
    var filterForm = new FilterForm({title: Localization.get('filter.form.add_filter.title')});
    component.relayEvents(filterForm, ['add-record']);
    filterForm.show();
  },
  openEditScreen: function(record, component) {
    var editFilterForm = new FilterForm({title: Localization.get('filter.form.edit_filter.title'), data: record});
    component.relayEvents(editFilterForm, ['add-record']);
    editFilterForm.show();
  },
  getGrid: function() {
    return this.lookupReference('filterGrid');
  },
  onAddRecord: function(form, record) {
    var grid = this.lookupReference('filterGrid');
    grid.getStore().add(record);
    form.closeView();
  },
  onEditRecord: function(form, record) {
    var grid = this.lookupReference('filterGrid');
    var store = grid.getStore();
    var recordIndex = grid.getStore().findBy(function(rec) { return rec.get('id') === record.id; });
    if (recordIndex >= 0) {
      var existingRecord = store.getAt(recordIndex);
      Ext.each(Object.keys(record), function(key) {
        existingRecord.set(key, record[key]);
      });
    }
    form.closeView();
  }
});