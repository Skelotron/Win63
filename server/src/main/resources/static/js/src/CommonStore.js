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
  }
});