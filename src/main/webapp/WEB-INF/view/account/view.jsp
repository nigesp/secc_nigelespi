<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1>Account - ${account.name} &pound;${account.balance}</h1>

<a class="btn btn-lg btn-success" href="/account/${account.id}/pay" role="button">Pay</a>

<h2>Transactions</h2>
<div class="table-responsive">
    <table class="table table-striped">
        <thead>
            <tr>
                <td>Date</td>
                <td>Type</td>
                <td>Amount</td>
                <td>Other account</td>
            </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${account.transactions.size() > 0}">
                <c:forEach items="${account.transactions}" var="transaction">
                    <tr>
                        <td><fmt:formatDate value="${transaction.created}" pattern="yyyy-MM-dd hh:mm"/></td>
                        <td>${transaction.type}</td>
                        <td>${transaction.amount}</td>
                        <td>${transaction.otherAccount.name}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                No transactions have taken place on this account.
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>