const signOutButton = document.querySelector("#sign-out-button");

if(signOutButton != null) {
	signOutButton.onclick = function(event) {
		event.preventDefault();
		signOutButton.parentElement.submit();
	};
}