var Enums = {};
Enums.ItemField = Enums.ItemField || {
  TITLE: 'TITLE',
  DESCRIPTION: 'DESCRIPTION',
  PRICE: 'PRICE',
  CATEGORY: 'CATEGORY',
  CITY: 'CITY'
};
Enums.ItemRelation = Enums.ItemRelation || {
  EQUALS: 'EQUALS',
  CONTAINS: 'CONTAINS',
  NOT_CONTAINS: 'NOT_CONTAINS',
  GREATER: 'GREATER',
  GREATER_OR_EQUALS: 'GREATER_OR_EQUALS',
  LESSER: 'LESSER',
  LESSER_OR_EQUALS: 'LESSER_OR_EQUALS',
  IN: 'IN'
};

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
  createCityStore: function() {
    return new Ext.data.JsonStore({
      storeId: 'cityStore',
      proxy: {
        type: 'ajax',
        url: '/city/',
        reader: {
          type: 'json',
          rootProperty: 'cities'
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
    var data = [];
    Ext.each(Object.values(Enums.ItemRelation), function(relation) {
      data.push({
        value: relation,
        name: Localization.get('filter.grid.column.relation.' + relation)
      });
    });
    return new Ext.data.Store({
      storeId: 'relationStore',
      model: 'Data.Model.FieldRelation',
      data: data
    });
  },
  createItemFilterFieldStore: function() {
    var data = [];
    Ext.each(Object.values(Enums.ItemField), function(field) {
      data.push({
        value: field,
        name: Localization.get('filter.grid.column.field.' + field)
      });
    });
    return new Ext.data.Store({
      storeId: 'itemFilterFieldStore',
      model: 'Data.Model.FilterField',
      data: data
    });
  },
  createItemSynchronizationStore: function() {
    return new Ext.data.JsonStore({
      storeId: 'itemSynchronizationStore',
      proxy: {
        type: 'ajax',
        url: '/synchronization/Item',
        reader: {
          type: 'json',
          rootProperty: 'synchronizations'
        }
      },
      autoLoad: true,
      model: 'Data.Model.ItemSynchronization'
    });
  },
  createItemSynchronizationStatusStore: function() {
    return new Ext.data.JsonStore({
      storeId: 'itemSynchronizationStatusStore',
      proxy: {
        type: 'ajax',
        url: '/synchronization/Item?last=true',
        reader: {
          type: 'json',
          rootProperty: 'synchronizations'
        }
      },
      autoLoad: true,
      model: 'Data.Model.ItemSynchronization'
    });
  }
});

var Store = {};
Store.Category = new CommonStore().createCategoryStore();
Store.City = new CommonStore().createCityStore();