
function lockScreen()
{
	alert("123");
	if(parent.document.getElementById("lockDiv")!=null&&parent.document.getElementById("lockDiv")!=undefined)
	{
		alert("123");
		var lockDiv = parent.document.getElementById("lockDiv");
		lockDiv.style.display = "block";
	}
	else if(parent.parent.document.getElementById("lockDiv")!=null&&parent.parent.document.getElementById("lockDiv")!=undefined)
	{
		var lockDiv = parent.parent.document.getElementById("lockDiv");
		lockDiv.style.display = "block";
	}
	else if(document.getElementById("lockDiv")!=null&&document.getElementById("lockDiv")!=undefined)
	{
		var lockDiv = document.getElementById("lockDiv");
		lockDiv.style.display = "block";
	}		
}


function unlockScreen()
{
	if(parent.document.getElementById("lockDiv")!=null&&parent.document.getElementById("lockDiv")!=undefined)
	{
		var lockDiv = parent.document.getElementById("lockDiv");
		lockDiv.style.display = "none";
	}
	else if(parent.parent.document.getElementById("lockDiv")!=null&&parent.parent.document.getElementById("lockDiv")!=undefined)
	{
		var lockDiv = parent.parent.document.getElementById("lockDiv");
		lockDiv.style.display = "none";
	}
	else if(document.getElementById("lockDiv")!=null&&document.getElementById("lockDiv")!=undefined)
	{
		var lockDiv = document.getElementById("lockDiv");
		lockDiv.style.display = "none";
	}	
}
