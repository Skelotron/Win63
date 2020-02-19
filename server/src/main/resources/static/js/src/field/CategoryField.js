Ext.define('CategoryField', {
  extend: 'Ext.data.field.Field',
  alias: 'data.field.Category',
  convert: function(value, record) {
    return record.get('category');
  }
});