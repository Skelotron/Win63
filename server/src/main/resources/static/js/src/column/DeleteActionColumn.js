Ext.define('Grid.column.DeleteAction', {
  extend: 'Ext.grid.column.Action',
  xtype: 'deleteactioncolumn',
  tooltip: Localization.get('column.delete'),
  menuText: Localization.get('column.delete'),
  icon: 'images/delete.png',
  width: 20,
  handler: 'onDeleteClick'
});