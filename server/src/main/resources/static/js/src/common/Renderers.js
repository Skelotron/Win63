var Renderers = Renderers || {};
Renderers.column = Renderers.column || {};
Renderers.column.FilterValueRenderer = Renderers.FilterValueRenderer ||
  function(value) {
    if (Ext.isArray(value)) {
      var names = [];
      Ext.each(value, function(id) {
        names.push(Store.Category.getById(id).get('name'));
      });
      return names.join('<br>');
    } else {
      return value;
    }
  };
Renderers.column.FilterRelationRenderer = Renderers.column.FilterRelationRenderer ||
  function(value) {
    return Localization.get('filter.grid.column.relation.' + value);
  };
Renderers.column.CountRenderer = Renderers.column.CountRenderer || function(value) { return value.length; };
Renderers.column.HtmlMessageRenderer = Renderers.column.HtmlMessageRenderer || function(value) { return Ext.util.Format.htmlEncode(value).replace(/\n/g, '<br>'); };
Renderers.column.FilterFieldRenderer = Renderers.column.FilterFieldRenderer ||
  function(value) {
    return Localization.get('filter.grid.column.field.' + value);
  };
Renderers.column.BooleanRenderer = Renderers.column.BooleanRenderer ||
  function(value) {
    if (Ext.isBoolean(value)) {
      return value === true ? Localization.get('boolean.true') : Localization.get('boolean.false');
    }
    return '';
  };