window.onload = function() {
	var url = document.location.toString();
	if (url.match('#signin') || url.match('#signup')) {
		$('#accountControl').modal('show');
		$(window.location.hash).tab('show');
		$(window.location.hash + 'Tab').tab('show');
	}
};

$('a[href="#signin"]').click(function() {
	$('#accountControl').modal('show');
	window.location.hash = 'signin';
	$('#signinTab').tab('show');
});

$('a[href="#signup"]').click(function() {
	$('#accountControl').modal('show');
	window.location.hash = 'signup';
	$('#signupTab').tab('show');
});

$('a[href="#forgetPassword"]').click(function() {
	$('#accountControl').modal('show');
	window.location.hash = 'forgetPassword';
	$('forgetPasswordTab').tab('show');
});

$('a[href="#payment"]').click(function() {
	$('.nav-link.active').removeClass('active');
	$('#paymentNav').addClass('active');
	$('.tab-pane.active').removeClass('show active');
	$('#payment').addClass('show active');
});

$('a[href="#review"]').click(function() {
	$('.nav-link.active').removeClass('active');
	$('#reviewNav').addClass('active');
	$('.tab-pane.active').removeClass('show active');
	$('#review').addClass('show active');
});

function revertToOriginalURL() {
	var original = window.location.href.substr(0, window.location.href
			.indexOf('#'));
	history.replaceState({}, document.title, original);
}

$('.modal').on('hidden.bs.modal', function() {
	$('.tab-pane').removeClass('active');
	$('.nav-tabs li a.active').removeClass('active');
	revertToOriginalURL();
});

function checkBillingAddress() {
	if ($("#theSameAsShippingAddress").is(":checked")) {
		$(".billingAddress").prop("disabled", true);
	} else {
		$(".billingAddress").prop("disabled", false);
	}
}

function checkPasswordMatchEdit() {
	var password = $("#newPasswordEdit").val();
	var confirmPassword = $("#confirmPasswordEdit").val();

	if (password === "" && confirmPassword === "") {
		$("#checkPasswordEdit").css("display", "none");
		$("#updateUserInfoButton").prop('disabled', false);
	} else {
		if (password !== confirmPassword) {
			$("#checkPasswordEdit").css("display", "block");
			$("#updateUserInfoButton").prop('disabled', true);
		} else {
			$("#checkPasswordEdit").css("display", "none");
			$("#updateUserInfoButton").prop('disabled', false);
		}
	}
}

function checkPasswordMatchReg() {
	var password = $("#passwordReg").val();
	var confirmPassword = $("#confirmPasswordReg").val();

	if (password === "" && confirmPassword === "") {
		$("#checkPasswordReg").css("display", "none");
	} else {
		if (password !== confirmPassword) {
			$("#checkPasswordReg").css("display", "block");
		} else {
			$("#checkPasswordReg").css("display", "none");
		}
	}
}

$(document).ready(function() {
	$("#theSameAsShippingAddress").on('click', checkBillingAddress);
	$("#confirmPasswordEdit").on('keyup', checkPasswordMatchEdit);
	$("#newPasswordEdit").on('keyup', checkPasswordMatchEdit);
	$("#confirmPasswordReg").on('keyup', checkPasswordMatchReg);
	$("#passwordReg").on('keyup', checkPasswordMatchReg);
});