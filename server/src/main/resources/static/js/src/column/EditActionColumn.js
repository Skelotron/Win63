Ext.define('Grid.column.EditAction', {
  extend: 'Ext.grid.column.Action',
  xtype: 'editactioncolumn',
  tooltip: Localization.get('column.edit'),
  menuText: Localization.get('column.edit'),
  icon: 'images/edit.png',
  width: 20,
  handler: 'onEditClickGrid',
  listeners: {'add-record': 'onEditRecord', 'scope': 'controller'}
});