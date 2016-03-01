<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Transaction</h1>

<h2>Accounts</h2>
<div class="table-responsive">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email Address</th>
            <th>Balance</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${accounts}" var="account">
            <tr>
                <td>${account.id}</td>
                <td>${account.name}</td>
                <td>${account.emailAddress}</td>
                <td>&pound; ${account.balance}</td>
                <td>
                    <a href="/account/${account.id}">view</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>