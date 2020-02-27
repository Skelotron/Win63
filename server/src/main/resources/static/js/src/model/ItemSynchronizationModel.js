Ext.define('Data.Model.ItemSynchronization', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'id', type: 'int', allowNull: 'true' },
    { name: 'category', type: 'Category' },
    { name: 'syncDate', type: 'date' },
    { name: 'newItemsCount', type: 'int' },
    { name: 'manual', type: 'auto' }
  ],
  idProperty: 'extId'
});