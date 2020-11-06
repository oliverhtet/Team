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

	$('#fileImage').change(function() {
		showImage(this);
	});

});



function showImage(fileInput) {
	file = fileInput.files[0];
	reader = new FileReader();
	reader.onload = function(e) {
		$('#previewimage').attr('src',e.target.result).width(100).height(100);
	};
	reader.readAsDataURL(file);
}