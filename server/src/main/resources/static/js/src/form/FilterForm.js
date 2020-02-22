Ext.define('FilterForm', {
  extend: 'Ext.Window',
  height: 300,
  width: 600,
  layout: 'fit',
  reference: 'filterFormWindow',
  modal: true,
  controller: 'filterFormCtrl',
  constructor: function (config) {
    this.items = [];
    this.items.push(new Ext.form.FormPanel({
      reference: 'filterForm',
      bodyPadding: '10',
      border: false,
      layout: 'vbox',
      items: [
      {
        reference: 'field',
        xtype: 'combo',
        name: 'field',
        fieldLabel: Localization.get('filter.form.add_filter.field.field'),
        store: new CommonStore().createItemFilterFieldStore(),
        displayField: 'name',
        valueField: 'value',
        allowBlank: false,
        editable: false,
        width: 500,
        listeners: { change: 'onFieldChange' }
      },
      {
        reference: 'relation',
        xtype: 'combo',
        name: 'relation',
        fieldLabel: Localization.get('filter.form.add_filter.field.relation'),
        store: new CommonStore().createItemFilterRelationsStore(),
        displayField: 'name',
        valueField: 'value',
        allowBlank: false,
        editable: false,
        width: 500
      },
      {
        reference: 'value',
        xtype: 'textfield',
        grow: true,
        name: 'value',
        allowBlank: false,
        fieldLabel: Localization.get('filter.form.add_filter.field.value'),
        width: 500
      },
      {
        reference: 'categoryValue',
        xtype: 'tagfield',
        fieldLabel: Localization.get('filter.form.add_filter.field.value'),
        store: new CommonStore().createCategoryStore(),
        queryMode: 'remote',
        displayField: 'name',
        valueField: 'id',
        hidden: true,
        width: 500
      }]
    }));

    this.bbar = [
    { xtype: 'tbfill' },
    {
      xtype: 'button',
      text: Localization.get('button.apply'),
      handler: 'onApply'
    },
    { xtype: 'button', text: Localization.get('button.cancel'), handler: 'onCancel' }];

    this.callParent(arguments);
  },
  listeners: {
    onApply: {
      fn: 'onApply',
      scope: 'controller'
    }
  }
});

Ext.define('FilterFormController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.filterFormCtrl',

  relations: {
    'TITLE': ['EQUALS', 'CONTAINS'],
    'DESCRIPTION': ['EQUALS', 'CONTAINS'],
    'PRICE': ['EQUALS', 'GREATER', 'GREATER_OR_EQUALS', 'LESSER', 'LESSER_OR_EQUALS'],
    'CATEGORY': ['EQUALS', 'CONTAINS']
  },

  getRelationFilter: function(field) {
    var self = this;
    return new Ext.util.Filter({
      filterFn: function(item) {
        return Ext.isArray(self.relations[field]) && self.relations[field].indexOf(item.get('value')) >= 0;
      }
    });
  },

  onFieldChange: function(combo, field) {
    var relationComboBox = this.lookupReference('relation');
    relationComboBox.getStore().clearFilter();
    relationComboBox.getStore().addFilter([this.getRelationFilter(field)]);
    if (relationComboBox.getStore().findBy(function(record) { return record.get('value') == relationComboBox.getValue(); }) < 0) {
      relationComboBox.setValue(null);
    }
    var isCategoryField = field === 'CATEGORY';
    this.lookupReference('value').setHidden(isCategoryField);
    this.lookupReference('value').allowBlank = isCategoryField;
    this.lookupReference('categoryValue').setHidden(!isCategoryField);
    this.lookupReference('categoryValue').allowBlank = !isCategoryField;
  },
  onApply: function() {
    if (this.lookupReference('filterForm').isValid()) {
      var field = this.lookupReference('field').getValue();
      var relation = this.lookupReference('relation').getValue();
      var value = this.lookupReference('value').getValue();
      var categoryValue = this.lookupReference('categoryValue').getValue();

      var record = {
        field: field,
        relation: relation,
        value: field === 'CATEGORY' ? categoryValue : value
      };

      this.fireViewEvent('add-filter', this, record);
    }
  },
  onCancel: function() {
    this.closeView();
  },
  init: function(config) {
    if (config.initialConfig.data) {
      var record = config.initialConfig.data;
      this.lookupReference('field').setValue(record.get('field'));
      this.lookupReference('relation').setValue(record.get('relation'));
      if (record.get('field') !== 'CATEGORY') {
        this.lookupReference('value').setValue(record.get('value'));
      } else {
        this.lookupReference('categoryValue').setValue(record.get('value'));
      }
    }
  }
});