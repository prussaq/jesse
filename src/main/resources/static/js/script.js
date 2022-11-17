var profile, prices, marketIntervalId;

$(document).ready(function () {
    document.getElementById("defaultTab").click();
    document.getElementById("buyForm").addEventListener("submit", buyFormSubmitListener);
    document.getElementById("sellForm").addEventListener("submit", sellFormSubmitListener);
});

function openTab(element, tabId) {
    // Get all elements with class="tabcontent" and hide them
    let tabcontent = document.getElementsByClassName("tabcontent");
    for (let i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    // Get all elements with class="tablinks" and remove the class "active"
    let tablinks = document.getElementsByClassName("tablinks");
    for (let i = 0; i < tablinks.length; i++) {
        tablinks[i].classList.remove("active");
    }
    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tabId).style.display = "block";
    element.classList.add("active");

    if (tabId === "activePositionsTab") {
        refreshActiveTab();
    }
    if (tabId === "completePositionsTab") {
        refreshCompleteTab();
    }
}

// ACTIVE TAB

function refreshActiveTab() {
    $.getJSON("/jesse/active/profile")
        .done(function (data) {
            profile = data;
            fillActiveSummary();
            loadActiveShares();
            // $("#marketButton").click();
        });
}

function fillActiveSummary() {
    $("#activeSummaryCountTd").text(profile.count);
    $("#activeSummaryBalanceTd").text(profile.balance.toLocaleString('en-US', {maximumFractionDigits: 2}));
    $("#activeSummaryMoneyTd").text(profile.money.toLocaleString('en-US', {maximumFractionDigits: 2}));
}

function loadActiveShares() {
    let tcontent = "";
    let shares = Object.entries(profile.shares);

    shares.forEach(function ([ticket, share]) {
        tcontent +=
            "<tr onclick='loadActiveTrades(this)' id=" + ticket + " >" +
            "<td><button onclick='onBuyHandler(this)'>Buy</button></td>" +
            "<td>" + share.ticket + "</td>" +
            "<td>" + share.name + "</td>" +
            "<td>" + share.amount + "</td>" +
            "<td>" + share.total.toLocaleString('en-US', {maximumFractionDigits: 2}) + "</td>" +
            "<td class='bestTd'></td>" +
            "</tr>";
    });
    document.getElementById("activeSharesTbody").innerHTML = tcontent;
    $("#activeSharesTbody tr:first").click();
}

function loadActiveTrades(element) {
    $("#activeSharesTbody > tr").css({"background-color": "#FFF"});
    $(element).css("background-color", "#ccc");

    let tcontent = "";
    let ticket = element.id;
    for (const trade of profile.shares[ticket].trades) {
        let total = trade.buy.price * trade.buy.remains;
        let id = ticket + ":" + trade.buy.id;
        tcontent +=
            "<tr onmouseout=onMouseOutTrade() onmouseover=onMouseOverTrade(this) onclick='toggleSells(this)' id=" + id + " >" +
            "<td class='buyIdTd'>" + trade.buy.id + "</td>" +
            "<td>" + trade.buy.date + "</td>" +
            "<td class='priceTd'>" + trade.buy.price + "</td>" +
            "<td>" + trade.buy.amount + "</td>" +
            "<td class='remainsTd'>" + trade.buy.remains + "</td>" +
            "<td class='totalTd'>" + total.toLocaleString('en-US', {maximumFractionDigits: 2}) + "</td>" +
            "<td class='profitTd'></td>" +
            "<td class='percentTd'></td>" +
            "<td><button onclick='onSellHandler(this)'>Sell</button> <button>Edit</button></td>" +
            "</tr>";
    }
    document.getElementById("activeTradesTbody").innerHTML = tcontent;
    fillActiveDetails(element.id);
    refreshProfits();
}

function fillActiveDetails(ticket) {
    $("#activeDetailsTicketTd").text(ticket);
    $("#activeDetailsNameTd").text(profile.shares[ticket].name);
    if (marketIntervalId) {
        setActiveDetailsMarket(ticket);
    }
}

function setActiveDetailsMarket(ticket) {
    let marketTd = $("#activeDetailsMarketTd");
    ticket = ticket || $("#activeTradesTbody tr:first").attr('id').split(":")[0];
    let price = prices.get(ticket);

    if (price) {
        marketTd.text(price.toLocaleString('en-US', {maximumFractionDigits: 6}));
    } else {
        marketTd.text("ND");
    }
}

function toggleSells(element) {
    let [ticket, buyId] = element.id.split(":");
    let sellClass = "sell-" + buyId;
    let sellRows = Array.from(document.getElementsByClassName(sellClass));

    if (sellRows.length > 0) {
        sellRows.forEach(row => row.remove());
        $(element).css("background-color", "#FFF");
    } else {
        let tcontent = "";
        let trade = profile.shares[ticket].trades.find(function (val) {
            return val.buy.id === parseInt(buyId);
        });

        for (const sell of trade.sells) {
            let total = sell.price * sell.amount;
            let profit = total - trade.buy.price * sell.amount;
            let percent = Math.trunc((profit / (trade.buy.price * sell.amount)) * 100);
            tcontent +=
                "<tr onmouseout=onMouseOutTrade() onmouseover=onMouseOverTrade(this) id=" + sell.id + " class=" + sellClass + " style='background-color:#FDEDEC;color:DarkRed'>" +
                "<td>" + sell.id + "</td>" +
                "<td>" + sell.date + "</td>" +
                "<td>" + sell.price + "</td>" +
                "<td>" + sell.amount + "</td>" +
                "<td>-</td>" +
                "<td>" + total.toLocaleString('en-US', {maximumFractionDigits: 2}) + "</td>" +
                "<td>" + profit.toLocaleString('en-US', {maximumFractionDigits: 2}) + "</td>" +
                "<td>" + percent + "%</td>" +
                "<td><button>Edit</button></td>" +
                "</tr>";
        }
        if (trade.sells.length !== 0) {
            $(element).after(tcontent);
            $(element).css({"background-color": "#ccc"});
        }
    }
}

function toggleMarket(button) {
    button.classList.toggle("pressed");

    if ($(button).hasClass("pressed")) {
        let refresh = function () {
            setActiveDetailsMarket();
            refreshBests();
            refreshProfits();
        };
        loadPrices(refresh);
        marketIntervalId = setInterval(loadPrices, 10000, refresh);
    } else {
        clearInterval(marketIntervalId);
    }
}

function loadPrices(refresh) {
    $.ajax({
        type: "POST",
        url: "/jesse/market/prices",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({tickets: Object.keys(profile.shares)})
    }).done(data => {
        prices = new Map(Object.entries(data));
        refresh();
    });
}

function refreshBests() {
    let top = Number.MIN_VALUE;

    $("#activeSharesTbody > tr").each(function (idx, tr) {
        let ticket = tr.id;
        let price = prices.get(ticket);
        let bestTd = $(tr).children(".bestTd");

        if (price) {
            let min = Math.min(...profile.shares[ticket].trades.map(trade => trade.buy.price));
            let best = Math.trunc((price - min) / min * 100);
            top = Math.max(top, best);
            bestTd.text((best > 0 ? "+" + best : best) + "%").css(best > 0 ? {"color": "green"} : {"color" : "DarkRed"});
        } else {
            bestTd.text("ND");
        }
    });
    $("#activeSummaryTopTd").text((top > 0 ? "+" + top : top) + "%");
}

function refreshProfits() {
    if (marketIntervalId) {
        $("#activeTradesTbody > tr").each((idx, tr) => {
            let ticket = tr.id.split(":")[0];
            let price = prices.get(ticket);

            if (price) {
                let remains = Number.parseFloat($(tr).children(".remainsTd").text().replace(",", ""));
                let total = Number.parseFloat($(tr).children(".totalTd").text().replace(",", ""));
                let profit = (price * remains - total).toLocaleString('en-US', {maximumFractionDigits: 2});
                $(tr).children(".profitTd").text((profit > 0 ? "+" + profit : profit))
                    .css(profit > 0 ? {"color": "green"} : {"color" : "DarkRed"});
                let oldPrice = Number.parseFloat($(tr).children(".priceTd").text().replace(",", ""));
                let percent = Math.trunc((price - oldPrice) / oldPrice * 100);
                $(tr).children(".percentTd").text((percent > 0 ? "+" + percent : percent) + "%")
                    .css(percent > 0 ? {"color": "green"} : {"color" : "DarkRed"});
            } else {
                $(tr).children(".profitTd").text("ND");
                $(tr).children(".percentTd").text("ND");
            }
        });
    }
}

function openBuyModal() {
    document.getElementById('buyModal').style.display='block';
    document.getElementById("buyForm").reset();
    $("#buyDateInput").val(new Date().toISOString().split("T")[0]);
}

function openSellModal() {
    document.getElementById('sellModal').style.display='block';
    document.getElementById("sellForm").reset();
    $("#sellDateInput").val(new Date().toISOString().split("T")[0]);
}

function buyFormSubmitListener(event) {
    event.preventDefault();
    let formData = new FormData(document.getElementById("buyForm"));
    console.log(JSON.stringify(Object.fromEntries(formData.entries())));
    $.ajax({
        type: "POST",
        url: "/jesse/deal/buy",
        // dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(Object.fromEntries(formData.entries()))
    }).done(response => {
        document.getElementById('buyModal').style.display='none';
        refreshActiveTab();
    });
    return false;
}

function sellFormSubmitListener(event) {
    event.preventDefault();
    let formData = new FormData(document.getElementById("sellForm"));
    console.log(JSON.stringify(Object.fromEntries(formData.entries())));
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/jesse/deal/sell",
        // dataType: "json",
        data: JSON.stringify(Object.fromEntries(formData.entries()))
    }).done(response => {
        document.getElementById('sellModal').style.display='none';
        refreshActiveTab();
    });
    return false;
}

function onBuyHandler(element) {
    openBuyModal();
    $("#buyTicketInput").val(element.parentElement.parentElement.id).attr('readonly', true);
}

function onSellHandler(element) {
    openSellModal();
    let [ticket, buyId] = element.parentElement.parentElement.id.split(":");
    $("#sellTicketInput").val(ticket);
    $("#sellBuyIdInput").val(buyId);
}

function onMouseOverTrade(element) {
    $("#activeDetailsIdTd").text(element.id.includes(":") ? element.id.split(":")[1] : element.id);
}

function onMouseOutTrade() {
    $("#activeDetailsIdTd").text("");
}

// COMPLETE TAB

function refreshCompleteTab() {
    $.getJSON("/jesse/complete/profile")
        .done(function (data) {
            profile = data;
            fillCompleteSummary();
            loadCompleteShares();
        });
}

function fillCompleteSummary() {
    $("#completeSummaryCountTd").text(profile.count);
    $("#completeSummaryTotalityTd").text(profile.balance.toLocaleString('en-US', {maximumFractionDigits: 2}));
}

function loadCompleteShares() {
    let tcontent = "";
    let shares = Object.entries(profile.shares);

    shares.forEach(function ([ticket, share]) {
        tcontent +=
            "<tr onclick='loadCompleteTrades(this)' id=" + ticket + " >" +
            "<td><button onclick='onBuyHandler(this)'>Buy</button></td>" +
            "<td>" + share.ticket + "</td>" +
            "<td>" + share.name + "</td>" +
            "<td>" + share.amount + "</td>" +
            "<td>" + share.total.toLocaleString('en-US', {maximumFractionDigits: 2}) + "</td>" +
            "<td class='bestTd'></td>" +
            "</tr>";
    });
    document.getElementById("completeSharesTbody").innerHTML = tcontent;
    $("#completeSharesTbody tr:first").click();
}

function loadCompleteTrades(element) {
    $("#completeSharesTbody > tr").css({"background-color": "#FFF"});
    $(element).css("background-color", "#ccc");

    let tcontent = "";
    let ticket = element.id;
    for (const trade of profile.shares[ticket].trades) {
        let total = trade.buy.price * trade.buy.amount;
        let id = ticket + ":" + trade.buy.id;
        tcontent +=
            "<tr onmouseout=onMouseOutCompleteTrade() onmouseover=onMouseOverCompleteTrade(this) onclick='toggleCompleteSells(this)' id=" + id + " >" +
            "<td class='buyIdTd'>" + trade.buy.id + "</td>" +
            "<td>" + trade.buy.date + "</td>" +
            "<td class='priceTd'>" + trade.buy.price + "</td>" +
            "<td>" + trade.buy.amount + "</td>" +
            "<td class='totalTd'>" + total.toLocaleString('en-US', {maximumFractionDigits: 2}) + "</td>" +
            "<td class='profitTd'></td>" +
            "<td class='percentTd'></td>" +
            "<td><button>Edit</button></td>" +
            "</tr>";
    }
    document.getElementById("completeTradesTbody").innerHTML = tcontent;
    fillCompleteDetails(element.id);
    fillCompleteProfits();
}

function fillCompleteDetails(ticket) {
    $("#completeDetailsTicketTd").text(ticket);
    $("#completeDetailsNameTd").text(profile.shares[ticket].name);
}

function onMouseOutCompleteTrade() {
    $("#completeDetailsIdTd").text("");
}

function onMouseOverCompleteTrade(element) {
    $("#completeDetailsIdTd").text(element.id.includes(":") ? element.id.split(":")[1] : element.id);
}

function toggleCompleteSells(element) {
    let [ticket, buyId] = element.id.split(":");
    let sellClass = "sell-" + buyId;
    let sellRows = Array.from(document.getElementsByClassName(sellClass));

    if (sellRows.length > 0) {
        sellRows.forEach(row => row.remove());
        $(element).css("background-color", "#FFF");
    } else {
        let tcontent = "";
        let trade = profile.shares[ticket].trades.find(function (val) {
            return val.buy.id === parseInt(buyId);
        });

        for (const sell of trade.sells) {
            let total = sell.price * sell.amount;
            let profit = total - trade.buy.price * sell.amount;
            let percent = Math.trunc((profit / (trade.buy.price * sell.amount)) * 100);
            tcontent +=
                "<tr onmouseout=onMouseOutCompleteTrade() onmouseover=onMouseOverCompleteTrade(this) id=" + sell.id + " class=" + sellClass + " style='background-color:#FDEDEC;color:DarkRed'>" +
                "<td>" + sell.id + "</td>" +
                "<td>" + sell.date + "</td>" +
                "<td>" + sell.price + "</td>" +
                "<td>" + sell.amount + "</td>" +
                "<td>" + total.toLocaleString('en-US', {maximumFractionDigits: 2}) + "</td>" +
                "<td>" + profit.toLocaleString('en-US', {maximumFractionDigits: 2}) + "</td>" +
                "<td>" + percent + "%</td>" +
                "<td><button>Edit</button></td>" +
                "</tr>";
        }
        if (trade.sells.length !== 0) {
            $(element).after(tcontent);
            $(element).css({"background-color": "#ccc"});
        }
    }

}

function fillCompleteProfits() {

}
