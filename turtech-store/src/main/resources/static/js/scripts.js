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