/**
 * 
 */
function editScrollBodyMaxHeight(clientId, maxHeight) {
				PF(clientId).scrollBody[0].style.setProperty('max-height',
						maxHeight);
}