Ext.define('ItemSynchronizationGrid', {
  extend: 'Ext.Panel',
  region: 'center',
  title: Localization.get('itemSynchronization.grid.title'),
  items: [{
    xtype: 'grid',
    store: new CommonStore().createItemSynchronizationStore(),
    columns: [
      { text: Localization.get('itemSynchronization.grid.column.category'), dataIndex: 'category', renderer: function(value) { return value.name; }, flex: 1 },
      { text: Localization.get('itemSynchronization.grid.column.new_items_count'), dataIndex: 'newItemsCount' },
      { text: Localization.get('itemSynchronization.grid.column.manual'), dataIndex: 'manual', renderer: Renderers.column.BooleanRenderer },
      { text: Localization.get('itemSynchronization.grid.column.sync_date'), dataIndex: 'syncDate', renderer: Ext.util.Format.dateRenderer('d/m/Y H:i:s'), width: 130 }
    ]
  }]
});