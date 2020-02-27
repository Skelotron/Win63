Ext.define('Grid.column.SyncAction', {
  extend: 'Ext.grid.column.Action',
  xtype: 'syncactioncolumn',
  tooltip: Localization.get('column.sync'),
  menuText: Localization.get('column.sync'),
  icon: 'images/sync.png',
  width: 20,
  handler: 'onSyncClickGrid'
});