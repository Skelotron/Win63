var Localization = {
  keys: {
    'subscription.grid.title': 'Subscriptions',
    'grid.button.edit': 'Edit',
    'subscription.grid.column.edit': 'Edit',
    'subscription.grid.column.category': 'Category',
    'subscription.grid.column.email': 'Email',
    'subscription.grid.column.message': 'Notified Count',
    'subscription.grid.column.delete': 'Delete',
    'subscription.form.add_subscription.title': 'Add Subscription',
    'subscription.form.subscription.field.category': 'Category',
    'subscription.form.add_subscription.button.apply': 'Add',
    'subscription.form.edit_subscription.title': 'Edit Subscription',
    'grid.button.add': 'Add'
  },
  get: function(key) {
    return this.keys[key] || key;
  }
};