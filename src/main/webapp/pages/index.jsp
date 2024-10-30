<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reports App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
          crossorigin="anonymous">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 30px;
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
        }
        .form-control, .btn {
            margin-bottom: 10px;
        }
        table th, table td {
            vertical-align: middle;
        }
        .no-records {
            text-align: center;
            font-weight: bold;
            color: #dc3545;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Report Application</h1>

    <div class="card p-4 mb-4">
        <form:form action="search" modelAttribute="search" method="POST" class="row g-3">
            <div class="col-md-4">
                <label class="form-label">Plan Name</label>
                <form:select path="planName" class="form-select">
                    <form:option value="">- Select -</form:option>
                    <form:options items="${names}" />
                </form:select>
            </div>
            <div class="col-md-4">
                <label class="form-label">Plan Status</label>
                <form:select path="planStatus" class="form-select">
                    <form:option value="">- Select -</form:option>
                    <form:options items="${status}" />
                </form:select>
            </div>
            <div class="col-md-4">
                <label class="form-label">Gender</label>
                <form:select path="gender" class="form-select">
                    <form:option value="">- Select -</form:option>
                    <form:option value="Male">Male</form:option>
                    <form:option value="Female">Female</form:option>
                </form:select>
            </div>
            <div class="col-md-6">
                <label class="form-label">Start Date</label>
                <form:input type="date" path="planStartDate" class="form-control" />
            </div>
            <div class="col-md-6">
                <label class="form-label">End Date</label>
                <form:input type="date" path="planEndDate" class="form-control" />
            </div>
            <div class="col-md-12 text-center">
                <button type="submit" class="btn btn-primary">Search</button>
                <a href="/" class="btn btn-secondary">Reset</a>
            </div>
        </form:form>
    </div>

    <div class="table-responsive">
        <table class="table table-striped table-hover table-dark">
            <thead class="table-dark">
                <tr>
                    <th>Id</th>
                    <th>Holder Name</th>
                    <th>Gender</th>
                    <th>Plan Name</th>
                    <th>Plan Status</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Benefit Amount</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${plans}" var="plan">
                    <tr>
                        <td>${plan.citizenId}</td>
                        <td>${plan.citizenName}</td>
                        <td>${plan.gender}</td>
                        <td>${plan.planName}</td>
                        <td>${plan.planStatus}</td>
                        <td>${plan.planStartDate}</td>
                        <td>${plan.planEndDate}</td>
                        <td>${plan.benefitAmount}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty plans}">
                    <tr>
                        <td colspan="8" class="no-records">No Records Found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

 <div class="d-flex justify-content-center gap-3 mt-3">
    <form:form action="/excel" method="GET" modelAttribute="excel" class="d-inline">
        <input type="hidden" name="planName" value="${search.planName}" />
        <input type="hidden" name="planStatus" value="${search.planStatus}" />
        <input type="hidden" name="gender" value="${search.gender}" />
        <input type="hidden" name="planStartDate" value="${search.planStartDate}" />
        <input type="hidden" name="planEndDate" value="${search.planEndDate}" />
        <button type="submit" class="btn btn-success">Export to Excel</button>
    </form:form>

    <form:form action="/pdf" method="GET" modelAttribute="pdf" class="d-inline">
        <input type="hidden" name="planName" value="${search.planName}" />
        <input type="hidden" name="planStatus" value="${search.planStatus}" />
        <input type="hidden" name="gender" value="${search.gender}" />
        <input type="hidden" name="planStartDate" value="${search.planStartDate}" />
        <input type="hidden" name="planEndDate" value="${search.planEndDate}" />
        <button type="submit" class="btn btn-danger">Export to PDF</button>
    </form:form>
</div>

    
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
        crossorigin="anonymous"></script>

</body>
</html>
