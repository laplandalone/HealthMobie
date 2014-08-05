$(function(){
	var date = new Date(); //鏃ユ湡瀵硅薄
	var now = null;
	var before = null;
	now = date.getFullYear()+"-"; 
	if((date.getMonth()+1) < 10)
	{
		if((date.getDate() < 10))
		{
			now = now + "0" + (date.getMonth()+1)+"-";
			now = now + "0" + date.getDate();
			before = date.getFullYear()+"-"; 
			before = before + "0" + (date.getMonth())+"-";
			before = before + "0" + date.getDate();
		}
		else
		{
			if((date.getFullYear()%4 == 0 && date.getFullYear()%100 !=0)||(date.getFullYear()%400==0))
			{
				if((date.getMonth()+1 == 3) && (date.getDate() == 30 || date.getDate() ==31))
				{
					now = now + "0" + (date.getMonth()+1)+"-";
					now = now + date.getDate();
					before = date.getFullYear()+"-"; 
					before = before + "0" + (date.getMonth())+"-";
					before = before + "29";
				}
				else
				{
					if((date.getMonth()+1 == 5) && (date.getDate() == 31) || (date.getMonth()+1 == 7) && (date.getDate() == 31))
					{
						now = now + "0" + (date.getMonth()+1)+"-";
						now = now + date.getDate();
						before = date.getFullYear()+"-"; 
						before = before + "0" + (date.getMonth())+"-";
						before = before + "30";
					}
					else
					{
						now = now + "0" + (date.getMonth()+1)+"-";
						now = now + date.getDate();
						before = date.getFullYear()+"-"; 
						before = before + "0" + (date.getMonth())+"-";
						before = before + date.getDate();
					}
				}
			}
			else 
			{
				if((date.getMonth()+1 == 3) && (date.getDate() == 29 || date.getDate() == 30 || date.getDate() ==31))
				{
					now = now + "0" + (date.getMonth()+1)+"-";
					now = now + date.getDate();
					before = date.getFullYear()+"-"; 
					before = before + "0" + (date.getMonth())+"-";
					before = before + "28";
				}
				else
				{
					if((date.getMonth()+1 == 5) && (date.getDate() == 31) || (date.getMonth()+1 == 7) && (date.getDate() == 31))
					{
						now = now + "0" + (date.getMonth()+1)+"-";
						now = now + date.getDate();
						before = date.getFullYear()+"-"; 
						before = before + "0" + (date.getMonth())+"-";
						before = before + "30";
					}
					else
					{
						now = now + "0" + (date.getMonth()+1)+"-";
						now = now + date.getDate();
						before = date.getFullYear()+"-"; 
						before = before + "0" + (date.getMonth())+"-";
						before = before + date.getDate();
					}
				}
			}
		}
	}
	else
	{
		if((date.getDate()<10))
		{
			now = now + (date.getMonth()+1)+"-";
			now = now + "0" + date.getDate();
			if((date.getMonth()+1) == 10)
			{
				before = date.getFullYear()+"-"; 
				before = before + "0" + (date.getMonth())+"-";
				before = before + "0" + date.getDate();
			}
			else
			{
				before = date.getFullYear()+"-"; 
				before = before + (date.getMonth())+"-";
				before = before + "0" + date.getDate();
			}
		}
		else
		{
			now = now + (date.getMonth()+1)+"-";
			now = now + date.getDate();
			if((date.getMonth()+1) == 10)
			{
				if((date.getMonth()+1) == 10)
				{
					if(date.getDate() == 31)
					{
						before = date.getFullYear()+"-"; 
						before = before + "0" + (date.getMonth())+"-";
						before = before + "30";
					}
					else
					{
						before = date.getFullYear()+"-"; 
						before = before + "0" + (date.getMonth())+"-";
						before = before + date.getDate();
					}
				}
			}
			else
			{
				if((date.getMonth() == 12) && (date.getDate() == 31))
				{
					before = date.getFullYear()+"-"; 
					before = before + (date.getMonth())+"-";
					before = before + "30";
				}
				else
				{
					before = date.getFullYear()+"-"; 
					before = before + (date.getMonth())+"-";
					before = before + date.getDate();
				}
			}
		}
	}
	if((date.getMonth()+1) == 1)
	{
		if((date.getDate()<10))
		{
			before = (date.getFullYear()-1)+"-"; 
			before = before + 12+"-";
			before = before + "0" + date.getDate();
		}
		else
		{
			before = (date.getFullYear()-1)+"-"; 
			before = before + 12+"-";
			before = before + date.getDate();
		}
	}
	
	$("#endTime").val(now);
	$("#startTime").val(before);
});
var date = new Date(); //鏃ユ湡瀵硅薄
var now = null;
var before = null;
now = date.getFullYear()+"-"; 
if((date.getMonth()+1) < 10)
{
	if((date.getDate() < 10))
	{
		now = now + "0" + (date.getMonth()+1)+"-";
		now = now + "0" + date.getDate();
		before = date.getFullYear()+"-"; 
		before = before + "0" + (date.getMonth())+"-";
		before = before + "0" + date.getDate();
	}
	else
	{
		if((date.getFullYear()%4 == 0 && date.getFullYear()%100 !=0)||(date.getFullYear()%400==0))
		{
			if((date.getMonth()+1 == 3) && (date.getDate() == 30 || date.getDate() ==31))
			{
				now = now + "0" + (date.getMonth()+1)+"-";
				now = now + date.getDate();
				before = date.getFullYear()+"-"; 
				before = before + "0" + (date.getMonth())+"-";
				before = before + "29";
			}
			else
			{
				if((date.getMonth()+1 == 5) && (date.getDate() == 31) || (date.getMonth()+1 == 7) && (date.getDate() == 31))
				{
					now = now + "0" + (date.getMonth()+1)+"-";
					now = now + date.getDate();
					before = date.getFullYear()+"-"; 
					before = before + "0" + (date.getMonth())+"-";
					before = before + "30";
				}
				else
				{
					now = now + "0" + (date.getMonth()+1)+"-";
					now = now + date.getDate();
					before = date.getFullYear()+"-"; 
					before = before + "0" + (date.getMonth())+"-";
					before = before + date.getDate();
				}
			}
		}
		else 
		{
			if((date.getMonth()+1 == 3) && (date.getDate() == 29 || date.getDate() == 30 || date.getDate() ==31))
			{
				now = now + "0" + (date.getMonth()+1)+"-";
				now = now + date.getDate();
				before = date.getFullYear()+"-"; 
				before = before + "0" + (date.getMonth())+"-";
				before = before + "28";
			}
			else
			{
				if((date.getMonth()+1 == 5) && (date.getDate() == 31) || (date.getMonth()+1 == 7) && (date.getDate() == 31))
				{
					now = now + "0" + (date.getMonth()+1)+"-";
					now = now + date.getDate();
					before = date.getFullYear()+"-"; 
					before = before + "0" + (date.getMonth())+"-";
					before = before + "30";
				}
				else
				{
					now = now + "0" + (date.getMonth()+1)+"-";
					now = now + date.getDate();
					before = date.getFullYear()+"-"; 
					before = before + "0" + (date.getMonth())+"-";
					before = before + date.getDate();
				}
			}
		}
	}
}
else
{
	if((date.getDate()<10))
	{
		now = now + (date.getMonth()+1)+"-";
		now = now + "0" + date.getDate();
		if((date.getMonth()+1) == 10)
		{
			before = date.getFullYear()+"-"; 
			before = before + "0" + (date.getMonth())+"-";
			before = before + "0" + date.getDate();
		}
		else
		{
			before = date.getFullYear()+"-"; 
			before = before + (date.getMonth())+"-";
			before = before + "0" + date.getDate();
		}
	}
	else
	{
		now = now + (date.getMonth()+1)+"-";
		now = now + date.getDate();
		if((date.getMonth()+1) == 10)
		{
			if((date.getMonth()+1) == 10)
			{
				if(date.getDate() == 31)
				{
					before = date.getFullYear()+"-"; 
					before = before + "0" + (date.getMonth())+"-";
					before = before + "30";
				}
				else
				{
					before = date.getFullYear()+"-"; 
					before = before + "0" + (date.getMonth())+"-";
					before = before + date.getDate();
				}
			}
		}
		else
		{
			if((date.getMonth() == 12) && (date.getDate() == 31))
			{
				before = date.getFullYear()+"-"; 
				before = before + (date.getMonth())+"-";
				before = before + "30";
			}
			else
			{
				before = date.getFullYear()+"-"; 
				before = before + (date.getMonth())+"-";
				before = before + date.getDate();
			}
		}
	}
}
if((date.getMonth()+1) == 1)
{
	if((date.getDate()<10))
	{
		before = (date.getFullYear()-1)+"-"; 
		before = before + 12+"-";
		before = before + "0" + date.getDate();
	}
	else
	{
		before = (date.getFullYear()-1)+"-"; 
		before = before + 12+"-";
		before = before + date.getDate();
	}
}