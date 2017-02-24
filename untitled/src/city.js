/*选择城市*/
function cityinit(id, inputId, callback) {
    var html = "";
    html += "<ul class=\"tab-list\">";
    html += "<li id=\"select-tab\"></li>";
    html += "<li class=\"select tab\" id=\"common\" index=\"0\"><a>常用</a></li>";
    html += "<li class=\"tab\" id=\"province\" index=\"1\"><a>省份</a></li>";
    html += "<li class=\"tab\" id=\"city\" index=\"2\"><a>城市</a></li>";
    html += "</ul>";
    html += "<div class=\"tab-box\"><a class=\"place city\" code=\"310000\">上海市</a><a class=\"place city\" code=\"440300\">深圳市</a><a class=\"place city\" code=\"110000\">北京市</a><a class=\"place city\" code=\"440100\">广州市</a><a class=\"place city\" code=\"320500\">苏州市</a><a class=\"place city\" code=\"510100\">成都市</a><a class=\"place city\" code=\"441900\">东莞市</a><a class=\"place city\" code=\"330200\">宁波市</a><a class=\"place city\" code=\"120000\">天津市</a><a class=\"place city\" code=\"330100\">杭州市</a><a class=\"place city\" code=\"370200\">青岛市</a><a class=\"place city\" code=\"500000\">重庆市</a><a class=\"place city\" code=\"420100\">武汉市</a><a class=\"place city\" code=\"410100\">郑州市</a><a class=\"place a-moreplace\">更多&gt;</a></div>";
    html += "<div class=\"hidden tab-box province-box\">";
    html += "<dl>";
    html += "<dt>A-G</dt>";
    html += "<dd><a class=\"place province\" code=\"34\">安徽</a><a class=\"place province\" code=\"82\">澳门</a><a class=\"place city\" code=\"110000\">北京</a><a class=\"place city\" code=\"500000\">重庆</a><a class=\"place province\" code=\"35\">福建</a><a class=\"place province\" code=\"62\">甘肃</a><a class=\"place province\" code=\"44\">广东</a><a class=\"place province\" code=\"45\">广西</a><a class=\"place province\" code=\"52\">贵州</a></dd>";
    html += "<dt>H-K</dt>";
    html += "<dd><a class=\"place province\" code=\"46\">海南</a><a class=\"place province\" code=\"13\">河北</a><a class=\"place province\" code=\"41\">河南</a><a class=\"place province\" code=\"23\">黑龙江</a><a class=\"place province\" code=\"42\">湖北</a><a class=\"place province\" code=\"43\">湖南</a><a class=\"place province\" code=\"22\">吉林</a><a class=\"place province\" code=\"32\">江苏</a><a class=\"place province\" code=\"36\">江西</a></dd>";
    html += "<dt>L-S</dt>";
    html += "<dd><a class=\"place province\" code=\"21\">辽宁</a><a class=\"place province\" code=\"15\">内蒙古</a><a class=\"place province\" code=\"64\">宁夏</a><a class=\"place province\" code=\"63\">青海</a><a class=\"place province\" code=\"37\">山东</a><a class=\"place province\" code=\"14\">山西</a><a class=\"place province\" code=\"61\">陕西</a><a class=\"place city\" code=\"310000\">上海</a><a class=\"place province\" code=\"51\">四川</a></dd>";
    html += "<dt>T-Z</dt>";
    html += "<dd><a class=\"place city\" code=\"120000\">天津</a><a class=\"place province\" code=\"71\">台湾</a><a class=\"place province\" code=\"54\">西藏</a><a class=\"place province\" code=\"65\">新疆</a><a class=\"place province\" code=\"81\">香港</a><a class=\"place province\" code=\"53\">云南</a><a class=\"place province\" code=\"33\">浙江</a></dd>";
    html += "</dl>";
    html += "</div>";
    html += "<div class=\"hidden tab-box\"></div>";

    $("#" + id).html(html);
    if (inputId == 0) inputId = '';
    else inputId = "_" + inputId;
    citybind(id, inputId, callback);
}

/*选择城市*/
function countyinit(id, inputId, callback) {
    var html = "";
    html += "<ul class=\"tab-list\">";
    html += "<li id=\"select-tab\"></li>";
    html += "<li class=\"select tab\" id=\"common\" index=\"0\"><a>常用</a></li>";
    html += "<li class=\"tab\" id=\"province\" index=\"1\"><a>省份</a></li>";
    html += "<li class=\"tab\" id=\"city\" index=\"2\"><a>城市</a></li>";
    html += " <li class=\"tab\" id=\"county\" index=\"3\"><a>县区</a></li>";
    html += "</ul>";
    html += "<div class=\"tab-box\"><a class=\"place city\" code=\"310000\">上海市</a><a class=\"place city\" code=\"440300\">深圳市</a><a class=\"place city\" code=\"110000\">北京市</a><a class=\"place city\" code=\"440100\">广州市</a><a class=\"place city\" code=\"320500\">苏州市</a><a class=\"place city\" code=\"510100\">成都市</a><a class=\"place city\" code=\"441900\">东莞市</a><a class=\"place city\" code=\"330200\">宁波市</a><a class=\"place city\" code=\"120000\">天津市</a><a class=\"place city\" code=\"330100\">杭州市</a><a class=\"place city\" code=\"370200\">青岛市</a><a class=\"place city\" code=\"500000\">重庆市</a><a class=\"place city\" code=\"420100\">武汉市</a><a class=\"place city\" code=\"410100\">郑州市</a><a class=\"place a-moreplace\">更多&gt;</a></div>";
    html += "<div class=\"hidden tab-box province-box\">";
    html += "<dl>";
    html += "<dt>A-G</dt>";
    html += "<dd><a class=\"place province\" code=\"34\">安徽</a><a class=\"place province\" code=\"82\">澳门</a><a class=\"place city\" code=\"110000\">北京</a><a class=\"place city\" code=\"500000\">重庆</a><a class=\"place province\" code=\"35\">福建</a><a class=\"place province\" code=\"62\">甘肃</a><a class=\"place province\" code=\"44\">广东</a><a class=\"place province\" code=\"45\">广西</a><a class=\"place province\" code=\"52\">贵州</a></dd>";
    html += "<dt>H-K</dt>";
    html += "<dd><a class=\"place province\" code=\"46\">海南</a><a class=\"place province\" code=\"13\">河北</a><a class=\"place province\" code=\"41\">河南</a><a class=\"place province\" code=\"23\">黑龙江</a><a class=\"place province\" code=\"42\">湖北</a><a class=\"place province\" code=\"43\">湖南</a><a class=\"place province\" code=\"22\">吉林</a><a class=\"place province\" code=\"32\">江苏</a><a class=\"place province\" code=\"36\">江西</a></dd>";
    html += "<dt>L-S</dt>";
    html += "<dd><a class=\"place province\" code=\"21\">辽宁</a><a class=\"place province\" code=\"15\">内蒙古</a><a class=\"place province\" code=\"64\">宁夏</a><a class=\"place province\" code=\"63\">青海</a><a class=\"place province\" code=\"37\">山东</a><a class=\"place province\" code=\"14\">山西</a><a class=\"place province\" code=\"61\">陕西</a><a class=\"place city\" code=\"310000\">上海</a><a class=\"place province\" code=\"51\">四川</a></dd>";
    html += "<dt>T-Z</dt>";
    html += "<dd><a class=\"place city\" code=\"120000\">天津</a><a class=\"place province\" code=\"71\">台湾</a><a class=\"place province\" code=\"54\">西藏</a><a class=\"place province\" code=\"65\">新疆</a><a class=\"place province\" code=\"81\">香港</a><a class=\"place province\" code=\"53\">云南</a><a class=\"place province\" code=\"33\">浙江</a></dd>";
    html += "</dl>";
    html += "</div>";
    html += "<div class=\"hidden tab-box\"></div>";
    html += "<div class=\"hidden tab-box\"></div>";

    $("#" + id).html(html);
    if (inputId == 0) inputId = '';
    else inputId = "_" + inputId;
    countybind(id, inputId, callback);
}

function citybind(id, inputId, callback) {
    commonbind(id, inputId, callback);

    $("#" + id).delegate("a.city", "click", function () {
        $("#" + id).find("a.select").removeClass("select");
        $(this).addClass("select");
        var cityname = $("#cityName_input" + inputId);
        var cityid = $("#cityId_input" + inputId);
        var provinceJson = area.province;
        for (var i = 0; i < provinceJson.length; i++) {
            if (provinceJson[i].code.substring(0, 2) == $(this).attr("code").substring(0, 2)) {
                cityname.val(provinceJson[i].name + "-" + $(this).html());
                try {
                    cityname.html(provinceJson[i].name + "-" + $(this).html());
                } catch (e) {
                }
            }
        }
        cityid.val($(this).attr('code'));
        cityname.css("color", "#333333");
        $("#" + id).hide();

        if (callback != null) {
            callback();
        }
        return false;
    }).click(function (e) {
        e.stopPropagation();
    });
}

function countybind(id, inputId, callback) {
    commonbind(id, inputId, callback);

    $("#" + id).delegate("a.city", "click", function () {
        $("#" + id).find("a.select").removeClass("select");
        $(this).addClass("select");
        var cityname = $("#cityName_input" + inputId);
        var cityid = $("#cityId_input" + inputId);
        var provinceJson = area.province;
        for (var i = 0; i < provinceJson.length; i++) {
            if (provinceJson[i].code.substring(0, 2) == $(this).attr("code").substring(0, 2)) {
                cityname.val(provinceJson[i].name + "-" + $(this).html());
                try {
                    cityname.html(provinceJson[i].name + "-" + $(this).html());
                } catch (e) {
                }
            }
        }
        cityid.val($(this).attr('code'));

        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            url: "/network/www/searchapi.do?method=getcounty",
            data: "city=" + $(this).attr("code"),
            dataType: "json",
            success: function (res) {
                $("#" + id).find(".tab-box:eq(3)").html(''); //先清空县区
                if (res.length > 0) {
                    for (var i = 0; i < res.length; i++) {
                        $("#" + id).find(".tab-box:eq(3)").append("<a class='place county' code='" + res[i].number + "'>" + res[i].name + "</a>"); //从后台取得县区的数据，放入div中
                    }
                    $("#" + id).find("#county").click(); //直接到 县区
                } else {
                    $("#" + id).hide();
                }
            }
        });
        return false;
    }).delegate("a.county", "click", function () { //点击县区效果
        $("#" + id).find("a.select").removeClass("select");
        $(this).addClass("select");
        var cityname = $("#cityName_input" + inputId);
        var cityid = $("#cityId_input" + inputId);
        var provinceJson = area.province;
        var tempCityName = "";
        for (var i = 0; i < provinceJson.length; i++) {
            if (provinceJson[i].code.substring(0, 2) == $(this).attr("code").substring(0, 2)) {
                tempCityName = provinceJson[i].name;
                break;
            }
        }
        var cityJson = area.city;
        for (var i = 0; i < cityJson.length; i++) {
            var provinceId = $(this).attr("code").substring(0, 2); //取得当前 县区 的code值，且截取前两位
            if (provinceId == "11" || provinceId == "12" || provinceId == "31" || provinceId == "50") { //如果是四个直辖市
                if (cityJson[i].code.substring(0, 2) == $(this).attr("code").substring(0, 2)) {
                    cityname.val(tempCityName + "-" + $(this).html() + "-" + $(this).html());
                    try {
                        cityname.html(tempCityName + "-" + $(this).html() + "-" + $(this).html());
                    } catch (e) {
                    }
                    break;
                }
            } else {
                if (cityJson[i].code.substring(0, 4) == $(this).attr("code").substring(0, 4)) {
                    cityname.val(tempCityName + "-" + cityJson[i].name + "-" + $(this).html());
                    try {
                        cityname.html(tempCityName + "-" + cityJson[i].name + "-" + $(this).html());
                    } catch (e) {
                    }
                    break;
                }
            }
        }

        cityid.val($(this).attr('code'));
        $("#" + id).hide();

        if (callback != null) {
            callback();
        }
        return false;
    }).click(function (e) {
        e.stopPropagation();
    });
}

function commonbind(id, inputId, callback) {
    $(document).click(function () {
        if (!$("#" + id).is(":hidden")) {
            $("#" + id).hide();
        }
    });
    $("body").delegate(".selectCity", "click", function (e) {
        e.stopPropagation();
    });

    $("#" + id).delegate("li.tab", "click", function () {
        var n = $(this).attr("index");
        $("#" + id).find(".tab-box").hide();
        $("#" + id).find(".tab-box:eq(" + n + ")").show();
        $("#" + id).find(".tab").removeClass("select");
        $("#" + id).find("#select-tab").css({left: n * 70 - 1 + "px"});
        $("#" + id).find(".tab:eq(" + n + ")").addClass("select");
        return false;
    }).delegate("a.province", "click", function () {
        $("#" + id).find("a.select").removeClass("select");
        $(this).addClass("select");
        $("#" + id).find("#city").click();
        var provinceId = $(this).attr("code");
        var cityJson = area.city;
        $("#" + id).find(".tab-box:eq(2)").html('');
        for (var i = 0; i < cityJson.length; i++) {
            if (cityJson[i].code.substring(0, 2) == provinceId) {
                $("#" + id).find(".tab-box:eq(2)").append("<a class='place city' code='" + cityJson[i].code + "'>" + cityJson[i].name + "</a>");
            }
        }
        return false;
    }).delegate("a.a-moreplace", "click", function () {
        $("#" + id).find("#province").click();
    });
}
