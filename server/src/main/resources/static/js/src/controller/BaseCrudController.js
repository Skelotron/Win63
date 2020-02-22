Ext.define('BaseCrudController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.BaseCrudCtrl',

  onAddClick: function() {
    this.openAddScreen();
  },
  onEditClick: function() {
    var grid = this.getGrid();
    var selections = grid.getSelection();
    if (Ext.isArray(selections) && selections.length > 0) {
      this.openEditScreen(selections[0]);
    }
  },
  onEditClickGrid: function(grid, rowIndex) {
    var record = grid.getStore().getAt(rowIndex);
    this.openEditScreen(record);
  },
  onDeleteClick: function(grid, rowIndex) {
    grid.getStore().removeAt(rowIndex);
  }
});