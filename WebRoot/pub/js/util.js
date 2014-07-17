function alertDialog(val)
{
	art.dialog({
	    lock: true,
	    content: val,
	    icon: 'warning',
	    ok: function () {
	        return true;
	    },
	});	
}