Ext.define('BaseCrudGrid', {
  extend: 'Ext.Panel',
  region: 'center',
  width: 500,
  tbar: [
    {
      xtype: 'button',
      text: Localization.get('grid.button.add'),
      handler: 'onAddClick',
      listeners: {'add-record': 'onAddRecord', 'scope': 'controller'}
    },
    {
      xtype: 'button',
      reference: 'editRecordButton',
      text: Localization.get('grid.button.edit'),
      handler: 'onEditClick',
      disabled: true,
      listeners: {'add-record': 'onEditRecord', 'scope': 'controller'}
    }
  ],
  listeners: {
    'add-record': { fn: 'onAddRecord', scope: 'controller' }
  }
});