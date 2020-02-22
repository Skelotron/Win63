Ext.define('Data.Model.Filter', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'id', type: 'int' },
    { name: 'field', type: 'string' },
    { name: 'relation', type: 'string' },
    { name: 'value', type: 'string' }
  ]
});