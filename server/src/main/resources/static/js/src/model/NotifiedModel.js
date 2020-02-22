Ext.define('Data.Model.Notified', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'id', type: 'int', allowNull: 'true' },
    { name: 'recipient', type: 'string' },
    { name: 'subject', type: 'string' },
    { name: 'message', type: 'string' },
    { name: 'filters', type: 'auto' }
  ],
  idProperty: 'extId'
});