Ext.onReady(function () {

    Ext.create('Ext.data.Store', {
        storeId: 'subscriptionsStore',
        fields: ['category', 'email', 'text'],
        data: [
            { category: 'Телефоны', email: 'lisa@simpsons.com', text: '555-111-1224' },
            { category: 'Bart', email: 'bart@simpsons.com', text: '555-222-1234' },
            { category: 'Homer', email: 'homer@simpsons.com', text: '555-222-1244' },
            { category: 'Marge', email: 'marge@simpsons.com', text: '555-222-1254' }
        ]
    });

    Ext.create('Ext.panel.Panel', {
        title: 'Subscriptions',
        width: 800,
        tbar: [
            { xtype: 'button', text: 'Add Subscription', handler: function() {
                createAddSubscriptionWindow();
            } },
            { xtype: 'button', text: 'Edit Subscription' },
        ],
        items: [{
            xtype: 'grid',
            store: Ext.data.StoreManager.lookup('subscriptionsStore'),
            columns: [
                {
                    //xtype: 'actioncolumn',
                    text: 'Edit',
                    dataIndex: 'email',
                    renderer: function () {
                        return '<i class="fas fa-edit">';
                    },
                    //handler: function() {

                    //}
                },
                { text: 'Category', dataIndex: 'category' },
                { text: 'Email', dataIndex: 'email', flex: 1 },
                { text: 'Text', dataIndex: 'text' },
                {
                    text: 'Remove',
                    dataIndex: 'email',
                    renderer: function () {
                        return '<i class="fas fa-trash">';
                    }
                }
            ],
            height: 500,
            width: 600,
        }],
        renderTo: 'mainContent'
    });


    var createAddSubscriptionWindow = function () {
        /*var addTag = function(input, tag) {
          var component = Ext.getCmp(input);
          component.setValue(component.getValue() + '<' + tag + '>');
        };

        var subjectContextMenu = new Ext.menu.Menu({
          text: 'Menu',
          id: 'SubjectContextMenu',
          items: [
            {text: 'Add Tag', menu: { items: [
                    { text: 'ItemTitle', handler: function() { addTag('subject', 'ItemTitle') } },
                    { text: 'ItemDescription', handler: function() { addTag('subject', 'ItemDescription') } }
               ]}
            }
          ]
        });

        var messageContextMenu = new Ext.menu.Menu({
                  text: 'Menu',
                  id: 'MessageContextMenu',
                  items: [
                    {text: 'Add Tag', menu: { items: [
                            { text: 'ItemTitle', handler: function() { addTag('message', 'ItemTitle') } },
                            { text: 'ItemDescription', handler: function() { addTag('message', 'ItemDescription') } }
                       ]}
                    }
                  ]
                });

        var categoryStore = new Ext.data.JsonStore({
            // store configs
            storeId: 'categoryStore',
            proxy: {
                type: 'ajax',
                url: '/category/',
                reader: {
                    type: 'json',
                    rootProperty: 'categories'
                }
            },
            fields: ['externalId', 'name']
        });

        Ext.create('Ext.window.Window', {
            title: 'Add Subscription',
            height: 300,
            width: 600,
            layout: 'fit',
            items: [{
                xtype: 'panel',
                border: false,
                items: [{
                    xtype: 'textfield',
                    name: 'email',
                    fieldLabel: 'Email',
                    allowBlank: false
                }, {
                    xtype: 'combo',
                    fieldLabel: 'Category',
                    store: categoryStore,
                    queryMode: 'remote',
                    displayField: 'name',
                    valueField: 'externalId'
                },
                {
                    xtype: 'textfield',
                    name: 'subject',
                    id: 'subject',
                    fieldLabel: 'Subject'
                },
                {
                    xtype: 'textareafield',
                    grow: true,
                    name: 'message',
                    id: 'message',
                    fieldLabel: 'Message'
                }]
            }]
        }).show();*/

        new SubscriptionForm({title: 'Add Subscription'}).show();

        Ext.get('subject').on('contextmenu', function(e) {
        	e.stopEvent();
        	var xy = e.getXY();
        	subjectContextMenu.showAt(xy);
        });

        Ext.get('message').on('contextmenu', function(e) {
            e.stopEvent();
            var xy = e.getXY();
            messageContextMenu.showAt(xy);
        });
    };

});
