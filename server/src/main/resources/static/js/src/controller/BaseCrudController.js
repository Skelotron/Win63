Ext.define('BaseCrudController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.BaseCrudCtrl',

  onAddClick: function(component) {
    this.openAddScreen(component);
  },
  onEditClick: function(component) {
    var grid = this.getGrid();
    var selections = grid.getSelection();
    if (Ext.isArray(selections) && selections.length > 0) {
      this.openEditScreen(selections[0], component);
    }
  },
  onEditClickGrid: function(grid, rowIndex, columnIndex, column) {
    var record = grid.getStore().getAt(rowIndex);
    this.openEditScreen(record, column);
  },
  onEditDoubleClick: function(grid, record) {
    this.openEditScreen(record, grid);
  },
  onDeleteClick: function(grid, rowIndex) {
    grid.getStore().removeAt(rowIndex);
  },
  onRowSelected: function() {
    this.lookupReference('editRecordButton').setDisabled(false);
  }
});