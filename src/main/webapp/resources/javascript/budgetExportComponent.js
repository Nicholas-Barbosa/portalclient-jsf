/**
 * 
 */
function onFileType(event) {
	let index = event.target.options.selectedIndex;
	let text = null;
	if (index == 1)
		text = '.pdf';
	else
		text = '.xlsx';
	$('#fileExtension').text(text);
}