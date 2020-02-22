Ext.define('Data.Model.Notified', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'id', type: 'int' },
    { name: 'recipient', type: 'string' },
    { name: 'subject', type: 'string' },
    { name: 'message', type: 'string' },
    { name: 'filters', type: 'auto' }
  ]
});