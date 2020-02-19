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
      model: 'Subscription'
      //fields: ['id', 'category', 'notifiedList']
    });
  }
});