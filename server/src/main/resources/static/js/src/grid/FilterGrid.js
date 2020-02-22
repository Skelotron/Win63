Ext.define('FilterGrid', {
  extend: 'Ext.Panel',
  region: 'center',
  controller: 'filterGridCtrl',
  title: Localization.get('filter.grid.title'),
    width: 500,
    tbar: [
      {
        xtype: 'button',
        text: Localization.get('filter.grid.button.add'),
        handler: 'onAddClick',
        listeners: {'add-filter': 'onAddFilter', 'scope': 'controller'}
      },
      {
        xtype: 'button',
        text: Localization.get('filter.grid.button.edit'),
        handler: 'onEditClick',
        listeners: {'add-filter': 'onEditFilter', 'scope': 'controller'}
      }
    ],
    items: [{
      xtype: 'grid',
      reference: 'filterGrid',
      store: new CommonStore().createFilterStore(),
      columns: [
        { xtype: 'editactioncolumn', listeners: {'add-filter': 'onEditFilter', 'scope': 'controller'} },
        { text: Localization.get('filter.grid.column.field'), dataIndex: 'field' },
        { text: Localization.get('filter.grid.column.relation'), dataIndex: 'relation', renderer: function(value) { return Localization.get('filter.grid.column.relation.' + value); } },
        { text: Localization.get('filter.grid.column.value'), dataIndex: 'value',
          renderer: function(value) {
            if (Ext.isArray(value)) {
              var names = [];
              Ext.each(value, function(id) {
                names.push(Store.Category.getById(id).get('name'));
              });
              return names.join('<br>');
            } else {
              return value;
            }
          }, flex: 1
        },
        { xtype: 'deleteactioncolumn' }
      ],
      listeners: {
        rowdblclick: 'onEditDoubleClick',
        'add-filter': { fn: 'onAddFilter', scope: 'controller' }
      },
      height: 200
    }],
    listeners: {
      'add-filter': { fn: 'onAddFilter', scope: 'controller' }
    },
    populateFilters: function() {
      var filterList = [];
      Ext.each(this.controller.lookupReference('filterGrid').getStore().getRange(), function(record) {
        filterList.push({
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
    component.relayEvents(filterForm, ['add-filter']);
    filterForm.show();
  },
  openEditScreen: function(record, component) {
    var editFilterForm = new FilterForm({title: Localization.get('filter.form.edit_filter.title'), data: record});
    component.relayEvents(editFilterForm, ['add-filter']);
    editFilterForm.show();
  },
  getGrid: function() {
    return this.lookupReference('filterGrid');
  },
  onAddFilter: function(form, record) {
    var grid = this.lookupReference('filterGrid');
    grid.getStore().add(record);
    form.closeView();
  },
  onEditFilter: function(form, record) {
    var grid = this.lookupReference('filterGrid');
    var store = grid.getStore();
    //var recordIndex = grid.getStore().findBy(function(rec) { return rec.get('recipient') === record.recipient; });
    //  if (recordIndex >= 0) {
    //    var existingRecord = store.getAt(recordIndex);
    //    Ext.each(Object.keys(record), function(key) {
    //      existingRecord.set(key, record[key]);
    //    });
    //  }
    form.closeView();
  }
});