//$( '.dataTables_export' ).ready(function() {
//    console.log("ready!");
//    console.log($('.dataTables_export').attr('class'));
//    console.log($('a:contains("XLS")').attr('href'));
//});

var checkExist = setInterval(function() {
    if ($('.dataTables_export').length) {
        console.log("Exists!");
        console.log($('a:contains("XLS")').attr('href'));
        clearInterval(checkExist);
    } else {
    	console.log("Not yet!");
    }
}, 100);

/*
 * <a href="/owners/list.html?lastName=&amp;dtt=f&amp;dti=owners&amp;dtf=xls&amp;dtp=y&amp;dandelionAssetFilterState=false">XLS</a>
 * <a href="/owners/list.html?lastName=&amp;dtt=f&amp;dti=owners&amp;dtf=xlsx&amp;dtp=y&amp;dandelionAssetFilterState=false">XLSX</a>
 * <a href="/owners/list.html?lastName=&amp;dtt=f&amp;dti=owners&amp;dtf=pdf&amp;dtp=y&amp;dandelionAssetFilterState=false">PDF</a>
 * <a href="/owners/list.html?lastName=&amp;dtt=f&amp;dti=owners&amp;dtf=xml&amp;dtp=y&amp;dandelionAssetFilterState=false">XML</a>
 * <a href="/owners/list.html?lastName=&amp;dtt=f&amp;dti=owners&amp;dtf=csv&amp;dtp=y&amp;dandelionAssetFilterState=false">CSV</a>
 */
