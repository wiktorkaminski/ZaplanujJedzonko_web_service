<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Zaplanuj Jedzonko</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext"
          rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>

<body>
<%-- app-header--%>
<%@ include file="/WEB-INF/jspf/app-header.jsp" %>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <%-- left navbar list with links to servlets--%>
        <%@ include file="/WEB-INF/jspf/app-left-nav-list.jsp" %>

        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <c:choose>
                    <c:when test="${plansByAdminId.size()==0}">
                        <div class="row border-bottom border-3 p-1 m-1">
                            <div class="col noPadding">
                                <h3 class="color-header text-uppercase">NIE MASZ ŻADNYCH PLANÓW</h3>
                            </div>
                            <div class="col d-flex justify-content-end mb-2 noPadding">
                                <a href="/app/dashboard" class="btn btn-info rounded-0 text-light m-1">OK</a>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${recipes.size()==0}">
                        <div class="row border-bottom border-3 p-1 m-1">
                            <div class="col noPadding">
                                <h3 class="color-header text-uppercase">NIE MASZ ŻADNYCH PRZEPISÓW</h3>
                            </div>
                            <div class="col d-flex justify-content-end mb-2 noPadding">
                                <a href="/app/dashboard" class="btn btn-info rounded-0 text-light m-1">OK</a>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="row border-bottom border-3 p-1 m-1">
                            <div class="col noPadding">
                                <h3 class="color-header text-uppercase">DODAJ PRZEPIS DO PLANU</h3>
                            </div>
                            <div class="col d-flex justify-content-end mb-2 noPadding">
                                <button type="submit" form="addRecipeToPlan"
                                        class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz
                                </button>
                            </div>
                        </div>

                        <div class="schedules-content">
                            <form action="/app/recipe/plan/add" method="post" id="addRecipeToPlan">
                                <div class="form-group row">
                                    <label for="choosePlan" class="col-sm-2 label-size col-form-label">
                                        Wybierz plan
                                    </label>
                                    <div class="col-sm-3">
                                        <select name="planId" class="form-control" id="choosePlan">
                                            <c:forEach items="${plansByAdminId}" var="plan">
                                                <option value="${plan.id}">${plan.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="name" class="col-sm-2 label-size col-form-label">
                                        Nazwa posiłku
                                    </label>
                                    <div class="col-sm-10">
                                        <input name="mealName" type="text" class="form-control" value="" id="name"
                                               placeholder="Nazwa posiłku" required>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="number" class="col-sm-2 label-size col-form-label">
                                        Numer posiłku
                                    </label>
                                    <div class="col-sm-2">
                                        <input name="displayOrder" type="number" min="1" max="5" class="form-control"
                                               value="1"
                                               id="number">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="recipe" class="col-sm-2 label-size col-form-label">
                                        Przepis
                                    </label>
                                    <div class="col-sm-4">
                                        <select name="recipeId" class="form-control" id="recipe">
                                            <c:forEach items="${recipes}" var="recipe">
                                                <option value="${recipe.id}">${recipe.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="day" class="col-sm-2 label-size col-form-label">
                                        Dzień
                                    </label>
                                    <div class="col-sm-2">
                                        <select name="dayNameId" class="form-control" id="day">
                                            <c:forEach items="${dayNames}" var="day">
                                                <option value="${day.id}">${day.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</section>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>