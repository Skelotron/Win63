Ext.define('Data.Model.Subscription', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'id', type: 'int', allowNull: 'true' },
    { name: 'category', type: 'Category' },
    { name: 'notifiedList', type: 'auto' }
  ],
  idProperty: 'extId'
});