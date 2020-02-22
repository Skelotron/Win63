Ext.define('Grid.column.EditAction', {
  extend: 'Ext.grid.column.Action',
  xtype: 'editactioncolumn',
  tooltip: Localization.get('column.edit'),
  icon: 'images/edit.png',
  width: 20,
  handler: 'onEditClickGrid'
});