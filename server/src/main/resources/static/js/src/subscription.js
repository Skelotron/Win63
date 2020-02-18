function submitSubscription() {
  var email = $('#email').val();
  var category = $('#category').val();
  var subject = $('#email_subject').val();
  var text = $('#email_text').val();

  console.log(email + ', ' + category + ', ' + subject + ', ' + text);

  jQuery.ajax({
    type: 'POST',
    url: "/subscription/add",
    contentType: "application/json",
    data: JSON.stringify({
      categoryUrl: category,
      type: 'EMAIL',
      address: email,
      subjectTemplate: subject,
      textTemplate: text,
      filters: []
    }),
    success: function () {
      $('#addSubscription').modal('hide');
    }
  });
}

function initSubscription(subscription) {
  $('#email').val(subscription.address);
  $('#category').val(subscription.categoryUrl);
  $('#email_subject').val(subscription.subjectTemplate);
  $('#email_text').val(subscription.textTemplate);
}

function editSubscription(subscriptionId) {
  jQuery.ajax({
    type: 'POST',
    url: "/subscription/" + subscriptionId,
    contentType: "application/json",
    data: JSON.stringify({}),
    success: function (subscription) {
      initSubscription(subscription);
      $('#addSubscription').modal('show');
    }
  });
}

$(function () {
  $('.edit-subscription').on('click', function (e) {
    var subscriptionId = parseInt($(e.target.parentElement).attr('value'));
    editSubscription(subscriptionId);
  });

  $('.add-subscription').on('click', addSubscription);
});