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
        handler: 'onAddClick'
      },
      {
        xtype: 'button',
        text: Localization.get('filter.grid.button.edit'),
        handler: 'onEditClick'
      }
    ],
    items: [{
      xtype: 'grid',
      reference: 'filterGrid',
      store: new CommonStore().createFilterStore(),
      columns: [
        { xtype: 'editactioncolumn' },
        { text: Localization.get('filter.grid.column.field'), dataIndex: 'field' },
        { text: Localization.get('filter.grid.column.relation'), dataIndex: 'relation', renderer: function(value) { return Localization.get('filter.grid.column.relation.' + value); } },
        { text: Localization.get('filter.grid.column.value'), dataIndex: 'value' },
        { xtype: 'deleteactioncolumn' }
      ],
      height: 200
    }],
    setFilters: function(records) {
      var store = this.controller.lookupReference('filterGrid').getStore();
      store.loadData(records, false);
    }
});

Ext.define('FilterGridController', {
  extend: 'BaseCrudController',
  alias: 'controller.filterGridCtrl',

  openAddScreen: function() {
    //new SubscriptionForm({title: Localization.get('filter.form.add_filter.title')}).show();
  },
  openEditScreen: function(record) {
    //var editSubscriptionForm = Ext.create('SubscriptionForm', {title: Localization.get('subscription.form.edit_subscription.title'), data: record});
    //editSubscriptionForm.show();
  },
  getGrid: function() {
    this.lookupReference('filterGrid');
  }
});