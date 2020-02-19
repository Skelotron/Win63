Ext.define('Subscription', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'id', type: 'int' },
    { name: 'category', type: 'Category' },
    { name: 'notifiedList', type: 'auto' }
  ]
});