Ext.define('BaseCrudController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.BaseCrudCtrl',

  onAddClick: function(component) {
    var editScreen = this.openAddScreen(component);
    editScreen.on('add-record', function(controller, record) {
      this.onAddRecord(controller, record);
    }, this);
  },
  onEditClick: function(component) {
    var grid = this.getGrid();
    var selections = grid.getSelection();
    if (Ext.isArray(selections) && selections.length > 0) {
      var editScreen = this.openEditScreen(selections[0], component);
      editScreen.on('add-record', function(controller, record) {
        this.onEditRecord(controller, record);
      }, this);
    }
  },
  onEditClickGrid: function(grid, rowIndex, columnIndex, column) {
    var record = grid.getStore().getAt(rowIndex);
    var editScreen = this.openEditScreen(record, column);
    editScreen.on('add-record', function(controller, record) {
      this.onEditRecord(controller, record);
    }, this);
  },
  onEditDoubleClick: function(grid, record) {
    var editScreen = this.openEditScreen(record, grid);
    editScreen.on('add-record', function(controller, record) {
      this.onEditRecord(controller, record);
    }, this);
  },
  onDeleteClick: function(grid, rowIndex) {
    grid.getStore().removeAt(rowIndex);
  },
  onRowSelected: function() {
    this.lookupReference('editRecordButton').setDisabled(false);
  }
});