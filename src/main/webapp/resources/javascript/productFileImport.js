function backWizardToBegin() {
	PF('importWizard').back();
}

function onConfirm(xhr){
	if(xhr.getResponseHeader('products-not-found')=='true'){
		PF('productsNotFound').show();
		updateProductsNotFound();
	}
}