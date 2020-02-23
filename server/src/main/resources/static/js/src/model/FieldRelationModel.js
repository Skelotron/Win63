Ext.define('Data.Model.FieldRelation', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'name', type: 'string' },
    { name: 'value', type: 'string' }
  ],
  idProperty: 'extId'
});