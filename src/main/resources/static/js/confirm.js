$(document).ready(function() {

	$('.nBtn, .alertMessage .eBtn').on('click', function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		var text = $(this).text();
		//for update user
		if (text == 'Edit') {
			$.get(href, function(users, status) {
				$('.myFormUpdate #id').val(users.id);
				$('.myFormUpdate #username').val(users.username);
				$('.myFormUpdate #password').val(users.password);
				$('.myFormUpdate #email').val(users.email);
			});
			$('.myFormUpdate #updateModal').modal();
		} else {
			//for creating user
			$('.myFormCreate #username').val('');
			$('.myFormCreate #password').val('');
			$('.myFormCreate #email').val('');
			$('.myFormCreate #myModalCreate').modal();
		}
	});
	//for delete user
	$('.alertMessage .delBtn').on('click', function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('#removeModalCenter #delRef').attr('href', href);
		$('#removeModalCenter').modal();
	});


});
//for table
$(document).ready(function() {
	$('#dtDynamicVerticalScrollExample').DataTable({
		"scrollY": "50vh",
		"scrollCollapse": true,
	});
	$('.dataTables_length').addClass('bs-select');
});