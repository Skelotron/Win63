Ext.define('CommonStore', {
  createCategoryStore: function() {
    return new Ext.data.JsonStore({
      storeId: 'categoryStore',
      proxy: {
        type: 'ajax',
        url: '/category/',
        reader: {
          type: 'json',
          rootProperty: 'categories'
        }
      },
      autoLoad: true,
      fields: ['id', 'name']
    });
  },
  createSubscriptionStore: function() {
    return new Ext.data.JsonStore({
      storeId: 'subscriptionStore',
      proxy: {
        type: 'ajax',
        url: '/subscription/',
        reader: {
          type: 'json',
          rootProperty: 'subscriptions'
        }
      },
      autoLoad: true,
      model: 'Data.Model.Subscription'
    });
  },
  createNotifiedStore: function() {
    return new Ext.data.Store({
      storeId: 'notifiedStore',
      model: 'Data.Model.Notified'
    });
  },
  createFilterStore: function() {
    return new Ext.data.Store({
      storeId: 'filterStore',
      model: 'Data.Model.Filter'
    });
  },
  createItemFilterRelationsStore: function() {
    return new Ext.data.Store({
      storeId: 'relationStore',
      fields: ['value', 'name'],
      data: [
        {value: 'EQUALS', name: Localization.get('filter.grid.column.relation.EQUALS')},
        {value: 'CONTAINS', name: Localization.get('filter.grid.column.relation.CONTAINS')},
        {value: 'GREATER', name: Localization.get('filter.grid.column.relation.GREATER')},
        {value: 'GREATER_OR_EQUALS', name: Localization.get('filter.grid.column.relation.GREATER_OR_EQUALS')},
        {value: 'LESSER', name: Localization.get('filter.grid.column.relation.LESSER')},
        {value: 'LESSER_OR_EQUALS', name: Localization.get('filter.grid.column.relation.LESSER_OR_EQUALS')},
        {value: 'IN', name: Localization.get('filter.grid.column.relation.CONTAINS')}
      ]
    });
  },
  createItemFilterFieldStore: function() {
    return new Ext.data.Store({
      storeId: 'itemFilterFieldStore',
      fields: ['value', 'name'],
      data: [
        {value: 'TITLE', name: Localization.get('filter.grid.column.field.TITLE')},
        {value: 'DESCRIPTION', name: Localization.get('filter.grid.column.field.DESCRIPTION')},
        {value: 'PRICE', name: Localization.get('filter.grid.column.field.PRICE')},
        {value: 'CATEGORY', name: Localization.get('filter.grid.column.field.CATEGORY')}
      ]
    });
  }
});

var Store = {};
Store.Category = new CommonStore().createCategoryStore();