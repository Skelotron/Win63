var Localization = {
  keys: {
    'subscription.grid.title': 'Subscriptions',
    'subscription.grid.button.add': 'Add Subscription',
    'subscription.grid.button.edit': 'Edit Subscription',
    'subscription.grid.column.edit': 'Edit',
    'subscription.grid.column.category': 'Category',
    'subscription.grid.column.email': 'Email',
    'subscription.grid.column.message': 'Text',
    'subscription.grid.column.delete': 'Delete',
    'subscription.form.add_subscription.title': 'Add Subscription',
    'subscription.form.add_subscription.field.email': 'Email',
    'subscription.form.add_subscription.field.category': 'Category',
    'subscription.form.add_subscription.field.subject': 'Subject',
    'subscription.form.add_subscription.field.message': 'Message',
    'subscription.form.add_subscription.button.apply': 'Add',
    'subscription.form.edit_subscription.title': 'Edit Subscription',

    'notified.grid.button.add': 'Add Notified'
  },
  get: function(key) {
    return this.keys[key] || key;
  }
};