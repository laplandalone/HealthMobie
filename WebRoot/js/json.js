$.fn.serializeObject = function()
{
	var o = {};      
	var a = this.serializeArray();      
	$.each(a, function(){
		if (o[this.name])
		{
			if (!o[this.name].push)
			{
				o[this.name] = [ o[this.name] ];      
			}
			o[this.name].push(this.value.replace(/^\s+|\s+$/g,"") || '');      
		}
		else
		{
			o[this.name] = this.value.replace(/^\s+|\s+$/g,"") || '';
		}
	});
	return o; 
}

function string2Json(s) 
{   
	var newstr = "";
	if(s.length > 0)
	{
		for (var i=0; i<s.length; i++) 
		{
			c = s.charAt(i);     
			switch (c) 
			{     
			case '\"':     
				newstr += "\\\"";     
				break;     
			case '\\':     
				newstr += "\\\\";     
				break;     
			case '/':     
				newstr += "\\/";     
				break;     
			case '\b':     
				newstr += "\\b";     
				break;     
			case '\f':     
				newstr += "\\f";     
				break;     
			case '\n':     
				newstr += "\\n";     
				break;     
			case '\r':     
				newstr += "\\r";     
				break;     
			case '\t':     
				newstr += "\\t";     
				break;   
			case '+':
				newstr += "%2B";   
				break; 
			case '&':
				newstr += "%26";   
				break;
			case '%':
				newstr += "%25"; 
				break;
			case '#':
				newstr += "%23";   
				break;
			case '$':
				newstr += "%24"; 
				break;
			case "'":
				newstr += "‘"; 
				break;	
			default:     
				newstr += c;     
			}
		}
	}
   return newstr;     
}

Array.prototype.remove = function(dx)
{
　	if(isNaN(dx) || dx > this.length)
	{
　		return false;
　	}
　	for(var i = 0, n = 0; i < this.length; i++)
　	{
　　　	if(this[i] != this[dx])
　　　	{
　　　　	　	this[n++] = this[i]
　　　	}
　	}
　	this.length-=1
}