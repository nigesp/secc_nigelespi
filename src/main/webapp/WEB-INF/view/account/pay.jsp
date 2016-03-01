<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Transfer</h1>

<h2>Current balance: &pound;${account.balance}</h2>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
</c:if>

<form:form action="/account/${account.id}/pay" method="post" commandName="accountPayFormObject">
    <fieldset class="form-group">
        <form:label path="amount">Amount</form:label>
        <form:errors cssClass="has-error" path="amount" cssStyle="display: block; color: red;"/>
        <div class="input-group">
            <div class="input-group-addon">&pound;</div>
            <form:input cssClass="form-control" path="amount"/>
        </div>
    </fieldset>
    <fieldset>
        <form:label path="toAccount">To Account</form:label>
        <form:errors cssClass="has-error" path="toAccount" cssStyle="display: block; color: red;"/>
        <form:select cssClass="form-control" path="toAccount">
            <form:option value="-1" label="-- Select account --"/>
            <form:options items="${accounts}" itemValue="id" itemLabel="name"/>
        </form:select>
    </fieldset>
    <fieldset>
        <button type="submit" class="btn btn-primary">Transfer</button>
    </fieldset>
    <form:hidden path="fromAccount" value="${accountPayFormObject.fromAccount.id}"/>
</form:form>