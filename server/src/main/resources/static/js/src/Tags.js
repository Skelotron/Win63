Ext.define('Tags', {
  types: {
    SUBJECT: 'SUBJECT',
    MESSAGE: 'MESSAGE'
  },
  getTags: function(type, callback) {
    Ext.Ajax.request({
      url: '/tag/available/' + type,
      method: 'GET',
      success: function(response) {
        var json = JSON.parse(response.responseText);
        if (callback) {
          callback(json.tags);
        }
      }
    });
  }
});