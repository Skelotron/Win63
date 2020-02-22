Ext.define('BaseEditFormWindow', {
  extend: 'Ext.Window',
  layout: 'fit',
  modal: true,
  constructor: function (config) {
    if (!config.data) {
      delete this.data;
    }
    this.items = [];
    this.items.push(this.createEditFormPanel());

    this.bbar = [
      { xtype: 'tbfill' },
      { xtype: 'button', text: Localization.get('button.apply'), handler: 'onApply' },
      { xtype: 'button', text: Localization.get('button.cancel'), handler: 'onCancel' }
    ];

    this.callParent(arguments);
  },
  listeners: {
    onApply: {
      fn: 'onApply',
      scope: 'controller'
    }
  }
});