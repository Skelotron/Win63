Ext.define('FilterForm', {
  extend: 'BaseEditFormWindow',
  height: 300,
  width: 600,
  controller: 'filterFormCtrl',
  createEditFormPanel: function () {
    return new Ext.form.FormPanel({
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
        queryMode: 'local',
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
        queryMode: 'local',
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
    });
  }
});

Ext.define('FilterFormController', {
  extend: 'BaseEditWindowController',
  alias: 'controller.filterFormCtrl',

  constructor: function(config) {
    this.relations = {};
    this.relations[Enums.ItemField.TITLE] = [Enums.ItemRelation.EQUALS, Enums.ItemRelation.CONTAINS];
    this.relations[Enums.ItemField.DESCRIPTION] = [Enums.ItemRelation.EQUALS, Enums.ItemRelation.CONTAINS];
    this.relations[Enums.ItemField.PRICE] = [Enums.ItemRelation.EQUALS, Enums.ItemRelation.GREATER, Enums.ItemRelation.GREATER_OR_EQUALS, Enums.ItemRelation.LESSER, Enums.ItemRelation.LESSER_OR_EQUALS];
    this.relations[Enums.ItemField.CATEGORY] = [Enums.ItemRelation.EQUALS, Enums.ItemRelation.CONTAINS];

    this.callParent(arguments);
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
    var isCategoryField = field === Enums.ItemField.CATEGORY;
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
        id: this.recordId,
        field: field,
        relation: relation,
        value: field === Enums.ItemField.CATEGORY ? categoryValue : value
      };

      this.fireViewEvent('add-record', this, record);
    }
  },
  init: function(config) {
    if (config.initialConfig.data) {
      var record = config.initialConfig.data;
      this.recordId = record.get('id');
      this.lookupReference('field').setValue(record.get('field'));
      this.lookupReference('relation').setValue(record.get('relation'));
      if (record.get('field') !== Enums.ItemField.CATEGORY) {
        this.lookupReference('value').setValue(record.get('value'));
      } else {
        this.lookupReference('categoryValue').setValue(record.get('value'));
      }
    }
  }
});