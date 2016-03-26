var checkExist = setInterval(function() {
	if ($('.dataTables_export').length) {
		$('#exportAsCsv').attr('href', $('.dataTables_export a:contains("CSV")').attr('href'));
		$('#exportAsPdf').attr('href', $('.dataTables_export a:contains("PDF")').attr('href'));
		$('#exportAsXml').attr('href', $('.dataTables_export a:contains("XML")').attr('href'));
		$('#exportAsXls').attr('href', $('.dataTables_export a:contains("XLS")').attr('href'));
		$('#exportAsXlsx').attr('href', $('.dataTables_export a:contains("XLSX")').attr('href'));

		clearInterval(checkExist);
	}
}, 100);
