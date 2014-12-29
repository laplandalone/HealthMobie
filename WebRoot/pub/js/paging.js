//Global Script
var gpagingCnt = document.getElementById("pagingNumCnt")
var gcurTag = 1;
var gMaxLimit = 5;
var gpagingCount = gpagingCnt.value;

$(document).ready(function() {
	$("#ctrltab td").each(function(i, data) {
		if (i < 8) {
			$(data).css("visibility", "hidden");
		}
	});
});

function hiddenCtrl() {
	$("#ctrltab td").each(function(i, data) {
		if (i < 8) {
			$(data).css("visibility", "hidden");
		}
	});
}

function qryPaingStartFunc(val) {
	var condition = document.getElementById("condition");
	if (condition.value.length > 2) {
		var obj = condition.value.substring(0, condition.value.length - 1)
				+ ",\"curId\":" + val + ",\"pageNum\":" + gpagingCount + "}";
	} else {
		var obj = condition.value.substring(0, condition.value.length - 1)
				+ "\"curId\":" + val + ",\"pageNum\":" + gpagingCount + "}";
	}
	return obj;
}

function qryMiddleFunc(data) {
	var treeNum = document.getElementById("treeNum");
	var pagingCount = document.getElementById("pagingNumCnt");
	treeNum.value = data;
	var ftag = [];
	var cnt = treeNum.value / pagingCount.value;
	for ( var i = 0; i < cnt; i++) {
		if (i < 5) {
			ftag.push("<span class='tagNode' id='cnt_" + (i + 1)
					+ "' onclick='showList(" + (i + 1) + ")'>" + (i + 1)
					+ "</span> ");
		} else {
			ftag.push("<span class='tagNode' id='cnt_" + (i + 1)
					+ "' style='display:none' onclick='showList(" + (i + 1)
					+ ")'>" + (i + 1) + "</span> ");
		}
	}
	$("#tagId").html(ftag.join(""));
	if (cnt == 0)
		hiddenCtrl();
	return cnt;
}

function qryStartFunc(obj) {
	gcurTag = 1;
	var condition = document.getElementById("condition");
	condition.value = obj;
	obj = obj.substring(0, obj.length - 1);
	if (obj.length > 2) {
		obj += ",\"curId\":1,\"pageNum\":" + gpagingCount + "}";
	} else {
		obj += "\"curId\":1,\"pageNum\":" + gpagingCount + "}";
	}
	return obj;
}

function qryEndFunc(cnt) {
	if (cnt != 0) {
		var treeNum = document.getElementById("treeNum");
		var pagingCount = document.getElementById("pagingNumCnt");
		$("#ctrltab td").each(function(i, data) {
			if (i < 8) {
				$(data).css("visibility", "visible");
			}
		});
		var btn = document.getElementById("prevPage");
		btn.disabled = true;
		hiddenNode(1);
		if (parseInt(treeNum.value) <= parseInt(pagingCount.value)) {
			var cnt = document.getElementById("nextPage");
			cnt.disabled = true;
		}
		//
		else {
			var cnt = document.getElementById("nextPage");
			cnt.disabled = false;
		}
		//
		var totalPage = document.getElementById("totalPage");
		if (parseInt(treeNum.value / gpagingCount) != Number(treeNum.value
				/ gpagingCount))
			totalPage.innerHTML = parseInt(treeNum.value / gpagingCount) + 1;
		else
			totalPage.innerHTML = parseInt(treeNum.value / gpagingCount);
	}
}

function rotPaging(val) {
	if (val == 1) {
		showList(parseInt(gcurTag) - 1);
	} else {
		showList(parseInt(gcurTag) + 1);
	}
}

function showNode(val) {
	var cnt = document.getElementById("cnt_" + val);
	cnt.style.cursor = "pointer";
	cnt.style.color = "#000000";
	cnt.style.backgroundImage = "";
}

function hiddenNode(val) {
	var cnt = document.getElementById("cnt_" + val);
	cnt.style.backgroundImage = "url('/pub/images/node.png')";
	cnt.style.cursor = "default";
	cnt.style.color = "#ffffff";
}

function showList(val) {
	var globalPage = parseInt(val / gMaxLimit) + 1;
	var btnpre = document.getElementById("prevPage");
	var btnnex = document.getElementById("nextPage");
	var treeNum = document.getElementById("treeNum");
	var pageNum;
	if (parseInt(treeNum.value / gpagingCount) != Number(treeNum.value
			/ gpagingCount)) {
		pageNum = parseInt(treeNum.value / gpagingCount) + 1;
	} else {
		pageNum = parseInt(treeNum.value / gpagingCount);
	}
	if (pageNum >= 5) {
		for ( var i = 1; i <= pageNum; i++) {
			var cnt = document.getElementById("cnt_" + i);
			if (i >= (globalPage - 1) * gMaxLimit && i < globalPage * gMaxLimit) {
				cnt.style.display = "inline";
			} else {
				cnt.style.display = "none";
			}
		}
		if (val < 5) {
			var cnt = document.getElementById("cnt_5");
			cnt.style.display = "inline";
		}
	}
	btnnex.disabled = false;
	btnpre.disabled = false;
	var treeNum = document.getElementById("treeNum");
	//
	qryPaging(val);
	//
	showNode(gcurTag);
	gcurTag = val;
	hiddenNode(gcurTag);
	if (gcurTag == 1) {
		gcurTag = 1;
		btnpre.disabled = true;
	}
	if (gcurTag >= pageNum) {
		gcurTag = pageNum;
		btnnex.disabled = true;
	}
}

function backOver(obj) {
	obj.style.backgroundImage = "url('/pub/images/btn2.png')";
}

function backOut(obj) {
	obj.style.backgroundImage = "url('/pub/images/btn1.png')";
}

function gotoKeydown(e) {
	var gotoPage = document.getElementById("gotoPage");
	var e = window.event || e;
	if (!(e.keyCode >= 48 && e.keyCode <= 57 || e.keyCode >= 96
			&& e.keyCode <= 105 || e.keyCode >= 0 && e.keyCode < 8 || e.keyCode > 8
			&& e.keyCode < 32)) {
		gotoPage.value = "";
	}
	if (gotoPage.value.length == 0 || gotoPage.value.length == 1) {
		if (gotoPage.value.length == 1) {
			if ("0".indexOf(gotoPage.value) == -1) {
				return;
			}
		}
		if (e.keyCode == 48 || e.keyCode == 96) {
			gotoPage.value = "";
		}
	}
}

function gotoFunc() {
	var treeNum = document.getElementById("treeNum");
	var gotoPage = document.getElementById("gotoPage");
	var data;
	if (parseInt(treeNum.value / gpagingCount) != Number(treeNum.value
			/ gpagingCount))
		data = parseInt(treeNum.value / gpagingCount) + 1;
	else
		data = parseInt(treeNum.value / gpagingCount);
	gotoPage.value = gotoPage.value.replace(/ /gi, "");
	if (gotoPage.value == "") {
		$.dialog.alert('请输入要跳转的页数!');
	} else if (isNaN(gotoPage.value) || parseInt(gotoPage.value) < 0
			|| parseInt(gotoPage.value) > data) {
		$.dialog.alert('您输入的页数有误!');
		gotoPage.value = "";
	} else {
		var i, n;
		for (i = 0, n = gotoPage.value.length; i < n; i++) {
			if (gotoPage.value.charAt(i) == '0')
				continue;
			else
				break;
		}
		if (i < n) {
			gotoPage.value = gotoPage.value.substring(i);
		}
		showList(gotoPage.value);
		gotoPage.value = "";
	}
}