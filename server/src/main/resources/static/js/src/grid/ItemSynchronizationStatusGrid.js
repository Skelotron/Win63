Ext.define('ItemSynchronizationStatusGrid', {
  extend: 'Ext.Panel',
  region: 'center',
  controller: 'ItemSyncStatusCtrl',
  height: 700,
  title: Localization.get('itemSynchronizationStatus.grid.title'),
  items: [{
    xtype: 'grid',
    scrollable: true,
    height: 700,
    store: new CommonStore().createItemSynchronizationStatusStore(),
    columns: [
      { text: Localization.get('itemSynchronization.grid.column.category'), dataIndex: 'category', renderer: function(value) { return value.name; }, flex: 1 },
      { text: Localization.get('itemSynchronization.grid.column.manual'), dataIndex: 'manual', renderer: Renderers.column.BooleanRenderer },
      { text: Localization.get('itemSynchronization.grid.column.last_sync_date'), dataIndex: 'syncDate', renderer: Ext.util.Format.dateRenderer('d/m/Y H:i:s'), width: 150 },
      { xtype: 'syncactioncolumn' }
    ]
  }]
});

Ext.define('ItemSynchronizationStatusController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.ItemSyncStatusCtrl',

  onSyncClickGrid: function(grid, rowIndex, columnIndex, column, event, record) {
    var categoryId = record.get('category')['id'];
    Ext.Ajax.request({
      url: '/synchronization/item/category/' + categoryId,
      method: 'POST',
      loadMask: true,
      params: Ext.util.JSON.encode({}),
      success: function(response) {
        grid.getStore().reload();
      }
    });
  }
});