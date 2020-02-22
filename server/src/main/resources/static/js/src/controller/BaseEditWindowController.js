Ext.define('BaseEditWindowController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.BaseEditWindowCtrl',
  onCancel: function() {
    this.closeView();
  }
});